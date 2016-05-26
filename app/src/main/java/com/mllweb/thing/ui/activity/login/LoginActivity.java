package com.mllweb.thing.ui.activity.login;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mllweb.model.UserInfo;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;
import com.mllweb.thing.ui.activity.register.RegisterActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.et_user_name)
    EditText mUserName;
    @InjectView(R.id.et_password)
    EditText mPassword;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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

        EMClient.getInstance().login("mllweb", "zaq123", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Toast.makeText(mActivity, "登录聊天服务器成功！", Toast.LENGTH_LONG).show();
                        UserInfoManager.init(new UserInfo() { });
                        startActivity(MainActivity.class);
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("mllweb-log", code + " " + message);
            }
        });

    }




}
