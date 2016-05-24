package com.mllweb.thing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mllweb.cache.ACache;
import com.mllweb.network.OkHttpClientManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;

/**
 * Created by Android on 2016/5/18.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mActivity;
    protected Resources mResources;
    protected OkHttpClientManager mHttp;
    protected ImageLoader mImageLoader;
    protected ACache mCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initFiled();
        initData(savedInstanceState);
        initEvent();
    }

    /**
     * 初始化布局文件
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 初始化属性
     */
    private void initFiled() {
        mResources = getResources();
        ButterKnife.inject(this);
        mActivity = this;
        mHttp = OkHttpClientManager.getInstance();
        mImageLoader=ImageLoader.getInstance();
        mCache=ACache.get(mActivity);
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    protected void clickBack(View v) {
        finish();
    }

    protected void startActivity(Class cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }
}
