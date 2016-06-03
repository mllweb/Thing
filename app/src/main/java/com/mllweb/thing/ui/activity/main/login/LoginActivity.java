package com.mllweb.thing.ui.activity.main.login;

import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;
import com.mllweb.thing.ui.activity.main.register.RegisterActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.et_user_name)
    @NotEmpty(message = "用户名不能为空")
    @Order(1)
    EditText mUserName;
    @InjectView(R.id.et_password)
    @Order(2)
    @NotEmpty(message = "请输入密码")
    @Password(scheme = Password.Scheme.ANY, message = "密码格式不正确")
    EditText mPassword;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.tv_register)
    public void clickRegister() {
        startActivity(RegisterActivity.class);
    }

    @OnClick(R.id.tv_login)
    public void clickLogin() {
        mValidator.validate();
    }

    /**
     * 输入框验证成功
     */
    @Override
    public void onValidationSucceeded() {
        callServerLogin();
    }

    private void callIMLogin(final UserInfo userInfo) {
        EMClient.getInstance().login(userInfo.getMobile(), Utils.md5(userInfo.getMobile() + userInfo.getPassword()), new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(MainActivity.class);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

    private void callServerLogin() {
        mHttp.requestPostDomain(API.LOGIN, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Utils.toast(mActivity, "登录失败");
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        UserInfo userInfo = UserInfoManager.init(body, mActivity);
                        mRealm.login(userInfo.getId(), body);
                        callIMLogin(userInfo);
                    }
                }, OkHttpClientManager.Params.get("userName", mUserName.getText().toString()),
                OkHttpClientManager.Params.get("password", mPassword.getText().toString()));
    }


}
