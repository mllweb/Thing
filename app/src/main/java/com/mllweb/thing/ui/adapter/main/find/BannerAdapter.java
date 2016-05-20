package com.mllweb.thing.ui.adapter.main.find;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mllweb.model.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/20.
 */
public class BannerAdapter extends PagerAdapter {
    private List<Banner> mData = new ArrayList<>();
    private List<ImageView> mImages = new ArrayList<>();
    private Activity mActivity;
    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public BannerAdapter(List<Banner> data, Activity activity) {
        this.mActivity = activity;
        if (data != null) {
            mData = data;
        }
        int size = mData.size();
        if (size > 0) {
            mImages.add(createImageView(mData.get(size - 1)));
            for (Banner b : mData) {
                mImages.add(createImageView(b));
            }
            mImages.add(createImageView(mData.get(0)));
        }
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(mImages.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final ImageView iv = mImages.get(position);
        if (mItemClickListener != null) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.itemClick(iv, position);
                }
            });
        }
        view.addView(iv);
        return mImages.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private ImageView createImageView(Banner banner) {
        ImageView iv = new ImageView(mActivity);
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setImageResource(banner.getDrawableResId());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return iv;
    }

    public interface OnItemClickListener {
        void itemClick(ImageView imageView, int position);
    }
}
