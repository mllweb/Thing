package com.mllweb.thing.ui.activity.main.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.mllweb.model.ThingFile;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.home.BrowseAdapter;

import java.util.List;

import butterknife.InjectView;

public class BrowseActivity extends BaseActivity {
    @InjectView(R.id.browse_pager)
    ViewPager mBrowsePager;
    @InjectView(R.id.tv_pager)
    TextView mPager;
    private BrowseAdapter mBrowseAdapter;
    private List<ThingFile> mData;
    private int position;

    @Override
    protected int initLayout() {
        return R.layout.activity_browse;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mData = (List<ThingFile>) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("position", 0);
        mPager.setText((position + 1) + "/" + mData.size());
        mBrowseAdapter = new BrowseAdapter(mData, mActivity);
        mBrowsePager.setAdapter(mBrowseAdapter);
        mBrowsePager.setCurrentItem(position);
    }

    @Override
    protected void initEvent() {
        mBrowsePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPager.setText((position + 1) + "/" + mData.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.browse_end);
    }
}
