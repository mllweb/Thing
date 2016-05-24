package com.mllweb.thing.ui.activity.main.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.home.BrowseAdapter;

import butterknife.InjectView;

public class BrowseActivity extends BaseActivity {
    @InjectView(R.id.browse_pager)
    ViewPager mBrowsePager;
    private BrowseAdapter mBrowseAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_browse;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBrowseAdapter = new BrowseAdapter(null, mActivity);
        mBrowsePager.setAdapter(mBrowseAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim,R.anim.browse_end);
    }
}
