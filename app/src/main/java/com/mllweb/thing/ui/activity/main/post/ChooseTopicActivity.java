package com.mllweb.thing.ui.activity.main.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mllweb.model.Thing;
import com.mllweb.model.ThingFile;
import com.mllweb.model.Topic;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.main.post.ChooseTopicAdapter;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseTopicActivity extends BaseActivity {
    @InjectView(R.id.et_topic)
    EditText mEdit;
    @InjectView(R.id.tv_topic_name)
    TextView mTopicName;
    @InjectView(R.id.create_layout)
    LinearLayout mCreateLayout;
    @InjectView(R.id.topic_list)
    RecyclerView mTopicListView;
    private ChooseTopicAdapter mTopicAdapter;
    private List<Topic> mData = new ArrayList<>();
    private Thing thing;

    @Override
    protected int initLayout() {
        return R.layout.activity_choose_topic;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        thing = (Thing) getIntent().getSerializableExtra("thing");
        mTopicListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mTopicAdapter = new ChooseTopicAdapter(mData, mActivity);
        mTopicListView.setAdapter(mTopicAdapter);
        initList("");
    }

    @Override
    protected void initEvent() {
        mTopicAdapter.setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void itemClick(View rootView, int position) {
                showLoading("创建中...");
                List<ThingFile> list = thing.getThingFiles();
                if (list != null && list.size() > 0) {
                    Counter counter = new Counter();
                    uploadFile(list, counter, mData.get(position));
                } else {
                    createThing(mData.get(position), "");
                }
            }
        });
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initList(s.toString());
            }
        });
    }

    private void createThing(Topic topic, String files) {
        mHttp.requestPostDomain(API.INSERT_THING, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        hideLoading();
                        startActivity(MainActivity.class);
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("topicId", topic.getId() + "")
                , OkHttpClientManager.Params.get("content", thing.getContent())
                , OkHttpClientManager.Params.get("files", files)
                , OkHttpClientManager.Params.get("link", thing.getLink()));
    }


    private void uploadFile(List<ThingFile> list, Counter counter, Topic topic) {
        ThingFile file = list.get(counter.i);
        String fileName = Utils.getFileName();
        mHttp.requestPostDomain(API.FILE_UPLOAD, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hideLoading();
            }

            @Override
            public void onResponse(Response response, String body) {
                file.setFilePath(API.IMAGE + fileName);
                counter.i++;
                if (counter.i >= list.size()) {
                    String files = "[";
                    for (int t = 0; t < list.size(); t++) {
                        ThingFile f = list.get(t);
                        files += String.format("{path:\"%s\",type:%d},", f.getFilePath(), f.getFileType());
                    }
                    files = files.substring(0, files.length() - 1);
                    files += "]";
                    createThing(topic, files);
                } else {
                    uploadFile(list, counter, topic);
                }
            }
        }, new File(file.getFilePath()), fileName);
    }


    public class Counter {
        int i;
    }

    private void initList(String search) {
        mHttp.requestPostDomain(API.SELECT_TOPIC, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                mData.clear();
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optInt("code") == 1000) {
                        if (!search.equals("")) {
                            mCreateLayout.setVisibility(View.VISIBLE);
                            mTopicName.setText(search);
                        } else {
                            mCreateLayout.setVisibility(View.GONE);
                        }
                    } else {
                        mCreateLayout.setVisibility(View.GONE);
                    }
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.getJSONObject(i);
                            Topic topic = mGson.fromJson(o.toString(), Topic.class);
                            mData.add(topic);
                        }
                        mTopicAdapter.resetData(mData);

                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("topicName", search));
    }

    @OnClick(R.id.create_layout)
    public void clickCreateTopic() {
        Intent intent = new Intent(mActivity, CreateTopicActivity.class);
        intent.putExtra("topicName", mEdit.getText().toString());
        startActivity(intent);
    }

}
