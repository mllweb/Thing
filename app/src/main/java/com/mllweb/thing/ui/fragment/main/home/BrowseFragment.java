package com.mllweb.thing.ui.fragment.main.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mllweb.model.ThingFile;
import com.mllweb.network.API;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.InjectView;

/**
 * Created by Android on 2016/6/16.
 */
public class BrowseFragment extends BaseFragment {
    @InjectView(R.id.image)
    ImageView mImage;

    @Override
    protected int initLayout() {
        return R.layout.fragment_browse;
    }

    @Override
    protected void initData(Bundle arguments) {
        ThingFile file = (ThingFile) arguments.getSerializable("file");
        mImageLoader.displayImage(API.DOMAIN + file.getFilePath(), mImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mImage.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }
}
