package com.mllweb.thing.ui.activity.main.post;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class CreateTopicActivity extends BaseActivity {

    @InjectView(R.id.tv_topic_name)
    TextView mTopicName;
    @InjectView(R.id.et_topic_desc)
    EditText mTopicDesc;
    @InjectView(R.id.et_topic_partner)
    EditText mTopicPartner;
    private String topicNmae;
    private String headImage;

    @Override
    protected int initLayout() {
        return R.layout.activity_create_topic;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topicNmae = getIntent().getStringExtra("topicName");
        mTopicName.setText(topicNmae);
    }

    @OnClick(R.id.tv_create)
    public void clickCreate() {

        mHttp.requestPostDomain(API.INSERT_TOPIC, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        Utils.log(body);
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("topicName", topicNmae)
                , OkHttpClientManager.Params.get("topicDesc", mTopicDesc.getText().toString())
                , OkHttpClientManager.Params.get("topicPartner", mTopicPartner.getText().toString())
                , OkHttpClientManager.Params.get("topicHeadImage", headImage));
    }
}
