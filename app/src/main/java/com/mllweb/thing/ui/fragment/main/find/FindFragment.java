package com.mllweb.thing.ui.fragment.main.find;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.mllweb.model.Banner;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.main.find.BannerAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Android on 2016/5/18.
 */
public class FindFragment extends BaseFragment {
    @InjectView(R.id.find_banner)
    ViewPager mBannerPage;
    private BannerAdapter mBannerAdapter;
    private List<Banner> mData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initData(Bundle arguments) {
        mData.add(new Banner(R.drawable.banner_1));
        mData.add(new Banner(R.drawable.banner_2));
        mData.add(new Banner(R.drawable.banner_3));
        mData.add(new Banner(R.drawable.banner_4));
        mData.add(new Banner(R.drawable.banner_5));
        mData.add(new Banner(R.drawable.banner_6));
        mBannerAdapter = new BannerAdapter(mData, mActivity);
        mBannerPage.setAdapter(mBannerAdapter);
        mBannerPage.setCurrentItem(1);
    }

    @Override
    protected void initEvent() {
        mBannerPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0) {
                    int size =mBannerAdapter.getCount();
                    if (position == 0) {
                        mBannerPage.setCurrentItem(size - 2, false);
                    } else if (position == size - 1) {
                        mBannerPage.setCurrentItem(1, false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
            @Override
            public void itemClick(ImageView imageView, int position) {

            }
        });
    }
}
