package com.mllweb.thing.ui.activity.main.post;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.model.Thing;
import com.mllweb.model.ThingFile;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class CreateTopicActivity extends BaseActivity {
    @InjectView(R.id.head_image)
    ImageView mHeadImage;
    @InjectView(R.id.tv_topic_name)
    TextView mTopicName;
    @InjectView(R.id.et_topic_desc)
    @Order(1)
    @NotEmpty(message = "介绍一下主题内容吧")
    EditText mTopicDesc;
    @InjectView(R.id.et_topic_partner)
    @Order(2)
    @NotEmpty(message = "给小伙伴给你昵称吧")
    EditText mTopicPartner;
    private String topicNmae;
    private String filePath;
    private Thing thing;

    @Override
    protected int initLayout() {
        return R.layout.activity_create_topic;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topicNmae = getIntent().getStringExtra("topicName");
        thing = (Thing) getIntent().getSerializableExtra("thing");
        mTopicName.setText(topicNmae);
    }

    @OnClick(R.id.tv_create)
    public void clickCreate() {
        if (filePath == null) {
            Utils.toast(mActivity, "头像更能表达主题呦");
            return;
        }
        mValidator.validate();
    }

    @OnClick(R.id.head_image)
    public void clickChooseHeadImage() {
        Intent intent = new Intent(mActivity, ChooseFileActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            File[] files = (File[]) data.getSerializableExtra("files");
            filePath = files[0].getAbsolutePath();
            ImageLoader.getInstance().loadImage(filePath, mHeadImage);
        }
    }

    @Override
    public void onValidationSucceeded() {
        uploadHeadImage();
    }

    private void uploadHeadImage() {
        showLoading("正在创建话题...");
        String fileName = Utils.getFileName();
        mHttp.requestPostDomain(API.FILE_UPLOAD, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hideLoading();
            }

            @Override
            public void onResponse(Response response, String body) {
                createTopic(fileName);
            }
        }, new File(filePath), fileName);
    }

    private void createTopic(String headImage) {
        mHttp.requestPostDomain(API.INSERT_TOPIC, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        try {
                            JSONObject object = new JSONObject(body);
                            JSONObject responseObject = object.optJSONObject("response");
                            int topicId = responseObject.optInt("result");
                            List<ThingFile> list = thing.getThingFiles();
                            if (list != null && list.size() > 0) {
                                Counter counter = new Counter();
                                uploadFile(list, counter, topicId);
                            } else {
                                createThing(topicId, "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("topicName", topicNmae)
                , OkHttpClientManager.Params.get("topicDesc", mTopicDesc.getText().toString())
                , OkHttpClientManager.Params.get("topicPartner", mTopicPartner.getText().toString())
                , OkHttpClientManager.Params.get("topicHeadImage", API.IMAGE + headImage));
    }

    public class Counter {
        int i;
    }

    private void uploadFile(List<ThingFile> list, Counter counter, int topicId) {
        mLoadingDialog.setProgress(counter.i * 1f / list.size() * 100);
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
                    createThing(topicId, files);
                } else {
                    uploadFile(list, counter, topicId);
                }
            }
        }, new File(file.getFilePath()), fileName);
    }

    private void createThing(int topicId, String files) {
        mHttp.requestPostDomain(API.INSERT_THING, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        hideLoading();
                        try {
                            JSONObject object = new JSONObject(body);
                            JSONObject responseObject = object.optJSONObject("response");
                            int thingId = responseObject.optInt("result");
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            UserInfo user = UserInfoManager.get(mActivity);
                            thing.setUserId(user.getId());
                            thing.setId(thingId);
                            thing.setNickName(user.getNickName());
                            thing.setCreateDate(new Date().getTime());
                            thing.setHeadImage(user.getHeadImage());
                            thing.setTopicId(topicId);
                            thing.setTopicName(topicNmae);
                            intent.putExtra("thing", thing);
                            user.setPostCount(user.getPraiseCount() + 1);
                            UserInfoManager.put(user, mActivity);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("topicId", topicId + "")
                , OkHttpClientManager.Params.get("content", thing.getContent())
                , OkHttpClientManager.Params.get("files", files)
                , OkHttpClientManager.Params.get("link", thing.getLink()));
    }
}
