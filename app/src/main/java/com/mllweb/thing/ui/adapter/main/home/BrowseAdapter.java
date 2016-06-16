package com.mllweb.thing.ui.adapter.main.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mllweb.model.ThingFile;
import com.mllweb.thing.ui.fragment.main.home.BrowseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class BrowseAdapter extends FragmentPagerAdapter {
    private List<ThingFile> mData = new ArrayList<>();
    private List<Fragment> mViews = new ArrayList<>();

    public BrowseAdapter(FragmentManager fm,List<ThingFile> data) {
        super(fm);
        this.mData = data;
        for (ThingFile f : mData) {
            BrowseFragment fragment=new BrowseFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("file",f);
            fragment.setArguments(bundle);
            mViews.add(fragment);
        }
    }


    @Override
    public Fragment getItem(int i) {
        return mViews.get(i);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }
}
