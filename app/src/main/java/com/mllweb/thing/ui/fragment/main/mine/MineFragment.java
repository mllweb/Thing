package com.mllweb.thing.ui.fragment.main.mine;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.setting.SettingActivity;
import com.mllweb.thing.ui.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Android on 2016/5/18.
 */
public class MineFragment extends BaseFragment {
    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.tv_setting)
    public void clickSetting() {
        startActivity(SettingActivity.class);
    }
}
