package com.mllweb.thing.ui.activity.main.register;

import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @InjectView(R.id.et_mobile)
    @NotEmpty(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "手机号格式错误")
    @Order(1)
    EditText mMobile;
    @InjectView(R.id.et_password)
    @Order(2)
    @NotEmpty(message = "请输入密码")
    @Password(scheme = Password.Scheme.ANY, message = "密码格式不正确")
    EditText mPassword;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.tv_register)
    public void clickRegister() {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        registerServer();
    }

    private void registerServer() {
        String mobile = mMobile.getText().toString();
        String password = mPassword.getText().toString();
        mHttp.requestPostDomain(API.Register, new OkHttpClientManager.RequestCallback() {
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
                                UserInfo userInfo = UserInfoManager.put(data.toString(), mActivity);
                                mRealm.login(userInfo.getId(), data.toString());
                                registerIM(userInfo);
                            } else {
                                Utils.toast(mActivity, responseObject.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, OkHttpClientManager.Params.get("mobile", mobile),
                OkHttpClientManager.Params.get("password", password));
    }

    private void registerIM(UserInfo userInfo) {
        new Thread(() -> {
            try {
                EMClient.getInstance().createAccount(userInfo.getMobile(), Utils.md5(userInfo.getMobile() + userInfo.getPassword()));
                startActivity(MainActivity.class);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
