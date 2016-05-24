package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mllweb.model.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class BrowseAdapter extends PagerAdapter {
    private List<Banner> mData = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private Activity mActivity;


    public BrowseAdapter(List<Banner> data, Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
