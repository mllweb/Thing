package com.mllweb.thing.ui.activity.main.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class EditInfoActivity extends BaseActivity {
    public static final int USER_SIGN = 1;
    public static final int USER_NAME = 2;
    public static final int NICK_NAME = 3;
    @InjectView(R.id.tv_title)
    TextView mTitle;
    @InjectView(R.id.tv_save)
    TextView mSave;
    @InjectView(R.id.tv_length)
    TextView mLength;
    @InjectView(R.id.et_edit)
    EditText mEdit;
    private int contentLength;
    private Intent mIntent;
    private int type;
    private UserInfo mUser;

    @Override
    protected int initLayout() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUser = UserInfoManager.get(mActivity);
        mIntent = getIntent();
        type = mIntent.getIntExtra("type", 0);

        switch (type) {
            case USER_SIGN:
                mTitle.setText("个性签名");
                mEdit.setText(mUser.getUserSign());
                contentLength = 30;
                break;
            case USER_NAME:
                mTitle.setText("账号");
                mEdit.setText(mUser.getUserName());
                contentLength = 10;
                break;
            case NICK_NAME:
                mTitle.setText("昵称");
                mEdit.setText(mUser.getNickName());
                contentLength = 10;
                break;
        }
        mLength.setText(contentLength + "");
        mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(contentLength)});
    }

    private void updateUserSign() {
        String userSign = mEdit.getText().toString();
        mHttp.requestPostDomain(API.UPDATE_USER_INFO, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        mUser.setUserSign(userSign);
                        UserInfoManager.put(mUser, mActivity);
                        finish();
                    }
                }, OkHttpClientManager.Params.get("id", mUser.getId() + ""),
                OkHttpClientManager.Params.get("userSign", userSign));
    }

    private void updateNickName() {
        String nickname = mEdit.getText().toString();
        mHttp.requestPostDomain(API.UPDATE_USER_INFO, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        mUser.setNickName(nickname);
                        UserInfoManager.put(mUser, mActivity);
                        finish();
                    }
                }, OkHttpClientManager.Params.get("id", mUser.getId() + ""),
                OkHttpClientManager.Params.get("nickname", nickname));
    }

    private void updateUserName() {
        String username = mEdit.getText().toString();
        mHttp.requestPostDomain(API.UPDATE_USER_INFO, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        mUser.setUserName(username);
                        UserInfoManager.put(mUser, mActivity);
                        finish();
                    }
                }, OkHttpClientManager.Params.get("id", mUser.getId() + ""),
                OkHttpClientManager.Params.get("username", username));
    }

    @OnClick(R.id.tv_save)
    public void clickSave() {
        switch (type) {
            case USER_NAME:
                updateUserName();
                break;
            case NICK_NAME:
                updateNickName();
                break;
            case USER_SIGN:
                updateUserSign();
                break;
        }
    }

    @Override
    protected void initEvent() {
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mSave.isEnabled()) {
                    mSave.setBackgroundColor(0xFF05af10);
                    mSave.setTextColor(0xFFFFFFFF);
                    mSave.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mLength.setText((contentLength - s.toString().length()) + "");
            }
        });
    }
}
