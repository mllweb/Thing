package com.mllweb.thing.ui.activity.main.center;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.message.ChatActivity;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.OnClick;

public class DetailsInfoActivity extends BaseActivity {
    private UserInfo mUserInfo;

    @Override
    protected int initLayout() {
        return R.layout.activity_details_info;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        int userId = getIntent().getIntExtra("userId", 0);
        mHttp.requestPostDomain(API.SELECT_USERINFO_BY_ID, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONObject data = responseObject.optJSONObject("data");
                        mUserInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("userId", userId + ""));
    }

    @OnClick(R.id.tv_chat)
    public void clickChat() {
        if (mRealm.isLogged()) {
            if (mUserInfo != null) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                intent.putExtra("user", mUserInfo);
                startActivity(intent);
            }
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }
}
