package com.mllweb.thing.ui.fragment.main.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ant.liao.GifView;
import com.mllweb.model.ThingFile;
import com.mllweb.network.API;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.InjectView;

/**
 * Created by Android on 2016/6/16.
 */
public class BrowseFragment extends BaseFragment {
    @InjectView(R.id.image)
    ImageView mImage;
    @InjectView(R.id.gif)
    GifView mGif;
    @InjectView(R.id.image_layout)
    RelativeLayout mImageLayout;
    @InjectView(R.id.gif_layout)
    RelativeLayout mGifLayout;

    @Override
    protected int initLayout() {
        return R.layout.fragment_browse;
    }

    @Override
    protected void initData(Bundle arguments) {
        ThingFile file = (ThingFile) arguments.getSerializable("file");
        if (file.getFileType() == 1) {
            mImageLayout.setVisibility(View.VISIBLE);
            mImageLoader.loadImage(API.DOMAIN + file.getFilePath(), new DisplayImageOptions.Builder()
                    .imageScaleType(ImageScaleType.NONE)
                    .build(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    float scale = (float) bitmap.getHeight() / bitmap.getWidth();
                    DisplayMetrics metrics = new DisplayMetrics();
                    mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    int screenWidth = metrics.widthPixels;
                    int screenHeight = metrics.heightPixels;
                    int height = (int) (screenWidth * scale);
                    if (height < screenHeight) {
                        height = screenHeight;
                    }
                    ViewGroup.LayoutParams params = mImage.getLayoutParams();
                    params.width = screenWidth;
                    params.height = height;
                    mImage.setLayoutParams(params);
                    mImage.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else if (file.getFileType() == 2) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mGifLayout.setVisibility(View.VISIBLE);
            mHttp.requestGetFileDomain(file.getFilePath(), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    baos.write(response.body().bytes());
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DisplayMetrics metrics = new DisplayMetrics();
                            mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
                            float scale = (float) bitmap.getHeight() / bitmap.getWidth();
                            int screenWidth = metrics.widthPixels;
                            int screenHeight = metrics.heightPixels;
                            int height = (int) (screenWidth * scale);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mGif.getLayoutParams();
                            params.width = screenWidth;
                            params.height = height;
                            if (height < screenHeight) {
                                params.topMargin = (screenHeight - height) / 3*2;
                            }
                            mGif.setLayoutParams(params);
                            mGif.setShowDimension(screenWidth, (int) (screenWidth * scale));
                            mGif.setGifImage(baos.toByteArray());
                        }
                    });
                }
            });
        }
    }
}
