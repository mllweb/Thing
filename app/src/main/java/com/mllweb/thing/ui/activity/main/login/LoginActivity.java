package com.mllweb.thing.ui.activity.main.login;

import android.widget.EditText;

import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mllweb.cache.ARealm;
import com.mllweb.model.Message;
import com.mllweb.model.MessageLog;
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

import org.json.JSONException;
import org.json.JSONObject;

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
                hideLoading();
            }

            @Override
            public void onError(int code, String message) {
                hideLoading();
            }
        });
    }

    private void callServerLogin() {
        showLoading("正在登陆...");
        mHttp.requestPostDomain(API.LOGIN, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Utils.toast(mActivity, "登录失败");
                        hideLoading();
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        try {
                            JSONObject object = new JSONObject(body);
                            JSONObject responseObject = object.optJSONObject("response");
                            if (responseObject.optString("state").equals(API.SUCC)) {
                                JSONObject data = responseObject.optJSONObject("data");
                                UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                                mRealm.login(userInfo.getId(), data.toString());
                                UserInfoManager.put(data.toString(), mActivity);
                                saveMessage();
                                saveMessageLog();
                                callIMLogin(userInfo);
                            } else {
                                hideLoading();
                                Utils.toast(mActivity, responseObject.optString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideLoading();
                        }
                    }
                }, OkHttpClientManager.Params.get("loginId", mUserName.getText().toString()),
                OkHttpClientManager.Params.get("loginPwd", mPassword.getText().toString()));
    }

    /**
     * 更新消息列表
     */
    private void saveMessage() {
        Message msg = new Message();
        msg.setUnreadCount(0);
        msg.setFromMobile("OfficialService");
        msg.setFromUserId(0);
        msg.setFromNickName("官方客服");
        msg.setFromHeadImage("/IMAGE/000000000000000000000");
        msg.setLastSendContent("欢迎你再次回到那点事儿。如果你在使用过程中有任何的问题或建议，记得给我发信反馈哦。");
        msg.setLastSendDate(System.currentTimeMillis());
        msg.setUnreadCount(1);
        ARealm.getInstance(getApplicationContext()).insertMessage(msg);
    }

    /**
     * 本地保存消息记录
     */
    private void saveMessageLog() {
        UserInfo mCurrentUser = UserInfoManager.get(getApplicationContext());
        MessageLog log = new MessageLog();
        log.setToUserId(mCurrentUser.getId());
        log.setContent("欢迎你再次回到那点事儿。如果你在使用过程中有任何的问题或建议，记得给我发信反馈哦。");
        log.setFile(null);
        log.setFromMobile("OfficialService");
        log.setFromUserHeadImage("/IMAGE/000000000000000000000");
        log.setFromNickName("官方客服");
        log.setFromUserId(0);
        log.setSendDate(System.currentTimeMillis());
        log.setToMobile(mCurrentUser.getMobile());
        log.setToNickName(mCurrentUser.getNickName());
        log.setType(1);
        ARealm.getInstance(getApplicationContext()).insertMessageLog(log);
    }
}