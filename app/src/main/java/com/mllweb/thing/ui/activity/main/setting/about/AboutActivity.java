package com.mllweb.thing.ui.activity.main.setting.about;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.utils.Utils;

import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_about;
    }

    @OnClick(R.id.tv_score)
    public void clickScore() {
        Utils.scoreApp(mActivity);
    }

    @OnClick(R.id.tv_feedback)
    public void clickFeedback() {
        startActivity(FeedbackActivity.class);
    }

    @OnClick(R.id.tv_version_update)
    public void clickVersionUpdate() {

    }
}
