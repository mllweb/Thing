package com.mllweb.thing.ui.activity.main.center.thing;

import android.os.Bundle;
import android.widget.TextView;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;

import butterknife.InjectView;

public class AboutMeThingActivity extends BaseActivity {
    public static final int ABOUT_ME_PRAISE = 1;
    public static final int ABOUT_ME_COLLECT = 2;
    public static final int ABOUT_ME_MINE = 3;
    @InjectView(R.id.tv_title)
    TextView mTitle;

    @Override
    protected int initLayout() {
        return R.layout.activity_about_me_thing;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        switch (getIntent().getIntExtra("type", 0)) {
            case ABOUT_ME_PRAISE:
                mTitle.setText("我顶过的帖子");
                break;
            case ABOUT_ME_COLLECT:
                mTitle.setText("我收藏的帖子");
                break;
            case ABOUT_ME_MINE:
                mTitle.setText("我发表的帖子");
                break;
        }
    }
}
