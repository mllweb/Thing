package com.mllweb.thing.ui.activity.main.setting;

import com.hyphenate.chat.EMClient;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.setting.about.AboutActivity;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @OnClick(R.id.tv_about)
    public void clickAbout() {
        startActivity(AboutActivity.class);
    }

    @OnClick(R.id.tv_logout)
    public void clickLogout() {
        EMClient.getInstance().logout(true);
    }
}
