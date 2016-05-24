package com.mllweb.thing.ui.activity.start;

import android.os.Bundle;
import android.widget.ImageView;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;

import butterknife.InjectView;

public class AdvertActivity extends BaseActivity {
    @InjectView(R.id.iv_ad)
    ImageView mAdvert;
    private String mImageUrl;

    @Override
    protected int initLayout() {
        return R.layout.activity_advert;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mImageUrl = getIntent().getStringExtra("imageUrl");
        mImageLoader.displayImage(mImageUrl, mAdvert);
    }
}
