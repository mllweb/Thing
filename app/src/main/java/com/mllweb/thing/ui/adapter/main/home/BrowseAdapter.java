package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.mllweb.model.ThingFile;
import com.mllweb.network.OkHttpClientManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Android on 2016/5/24.
 */
public class BrowseAdapter extends PagerAdapter {
    private List<ThingFile> mData = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    private Activity mActivity;


    public BrowseAdapter(List<ThingFile> data, Activity activity) {
        this.mActivity = activity;
        this.mData = data;
        ImageLoader loader = ImageLoader.getInstance();
        for (ThingFile f : mData) {
            PhotoView image = new PhotoView(mActivity);
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            loader.displayImage(OkHttpClientManager.DOMAIN + f.getFilePath(), image,new DisplayImageOptions.Builder().build());
            mViews.add(image);
        }
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
        View view1 = mViews.get(position);
        view.addView(view1);
        return view1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
