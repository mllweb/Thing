package com.mllweb.thing.ui.adapter.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm, List<Fragment> data) {
        super(fm);
        if (data != null) {
            mData = data;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
