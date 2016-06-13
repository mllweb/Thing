package com.mllweb.thing.ui.activity.main.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.mllweb.model.Comment;
import com.mllweb.model.Thing;
import com.mllweb.model.ThingFile;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.home.CommentAdapter;
import com.mllweb.thing.ui.adapter.main.home.ThingFileAdapter;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

public class ThingDetailsActivity extends BaseActivity {

    @InjectView(R.id.comment_list)
    RecyclerView mCommentListView;
    TextView mContent;
    TextView mTopic;
    TextView mNickName;
    TextView mDate;
    ImageView mHeadImage;
    private RecyclerView mThingFileListView;
    private ThingFileAdapter mThingFileAdapter;
    private Thing thing;
    private RecyclerViewHeader mCommentListViewHeader;
    private CommentAdapter mCommmentAdapter;
    private List<Comment> mCommentData = new ArrayList<>();

    @Override
    protected int initLayout() {
        mCommentListViewHeader = RecyclerViewHeader.fromXml(this, R.layout.adapter_comment_header);
        mContent = (TextView) mCommentListViewHeader.findViewById(R.id.tv_content);
        mTopic = (TextView) mCommentListViewHeader.findViewById(R.id.tv_topic);
        mNickName = (TextView) mCommentListViewHeader.findViewById(R.id.tv_nick_name);
        mHeadImage = (ImageView) mCommentListViewHeader.findViewById(R.id.iv_head_image);
        mDate = (TextView) mCommentListViewHeader.findViewById(R.id.tv_date);
        mThingFileListView = (RecyclerView) mCommentListViewHeader.findViewById(R.id.attachment_view);
        return R.layout.activity_thing_details;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initThing();
        initComment();
    }

    private void initThing() {
        thing = (Thing) getIntent().getSerializableExtra("thing");
        mContent.setText(thing.getContent());
        mTopic.setText("#" + thing.getTopicName() + "#");
        mNickName.setText(thing.getNickName());
        mImageLoader.displayImage(OkHttpClientManager.DOMAIN + thing.getHeadImage(), mHeadImage);
        mDate.setText(Utils.caleDate(new Date(thing.getCreateDate())));
        List<ThingFile> files = thing.getThingFiles();
        if (files != null && files.size() > 0) {
            mThingFileAdapter = new ThingFileAdapter(files, mActivity);
            if (files.size() == 1) {
                mThingFileListView.setLayoutManager(new GridLayoutManager(mActivity, 1));
            } else if (files.size() <= 4) {
                mThingFileListView.setLayoutManager(new GridLayoutManager(mActivity, 2));
            } else {
                mThingFileListView.setLayoutManager(new GridLayoutManager(mActivity, 3));
            }
            mThingFileListView.setAdapter(mThingFileAdapter);
        }
    }

    private void initComment() {
        mCommmentAdapter = new CommentAdapter(mCommentData, mActivity);
        mCommentListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mCommentListViewHeader.attachTo(mCommentListView);
        mCommentListView.setAdapter(mCommmentAdapter);
        mHttp.requestPostDomain(API.SELECT_COMMENT, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.getJSONObject(i);
                            Comment comment = mGson.fromJson(o.toString(), Comment.class);
                            mCommentData.add(comment);
                        }
                        mCommmentAdapter.resetData(mCommentData);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("thingId", thing.getId() + ""));
    }

}
