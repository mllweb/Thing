package com.mllweb.thing.ui.activity.main.center;

import com.hyphenate.chat.EMClient;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;

import butterknife.OnClick;

public class BaseInfoActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_base_info;
    }

    @OnClick(R.id.tv_logout)
    public void clickLogout() {
        mRealm.logout();
        EMClient.getInstance().logout(true);
        finish();
    }
}
