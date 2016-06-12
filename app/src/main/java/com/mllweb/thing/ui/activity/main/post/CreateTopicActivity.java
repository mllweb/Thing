package com.mllweb.thing.ui.activity.main.post;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

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
                        hideLoading();
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("topicName", topicNmae)
                , OkHttpClientManager.Params.get("topicDesc", mTopicDesc.getText().toString())
                , OkHttpClientManager.Params.get("topicPartner", mTopicPartner.getText().toString())
                , OkHttpClientManager.Params.get("topicHeadImage", API.IMAGE + headImage));
    }

}
