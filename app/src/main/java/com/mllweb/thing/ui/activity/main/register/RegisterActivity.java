package com.mllweb.thing.ui.activity.main.register;

import android.util.Log;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @InjectView(R.id.et_user_name)
    EditText mUserName;
    @InjectView(R.id.et_password)
    EditText mPassword;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.tv_register)
    public void clickRegister() {
        //注册失败会抛出HyphenateException
        try {
            EMClient.getInstance().createAccount("","");
        } catch (HyphenateException e) {
            e.printStackTrace();
            Log.d("mllweb-log","注册失败原因=" + e.getErrorCode()+" "+e.getMessage()+" "+e.getLocalizedMessage());
        }
    }
}
