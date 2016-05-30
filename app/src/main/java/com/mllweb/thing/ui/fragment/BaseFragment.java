package com.mllweb.thing.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mllweb.cache.ACache;
import com.mllweb.cache.ARealm;
import com.mllweb.network.OkHttpClientManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;

/**
 * Created by Android on 2016/5/18.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Bundle mArguments;
    protected View mContentView;
    protected Resources mResources;
    protected OkHttpClientManager mHttp;
    protected ImageLoader mImageLoader;
    protected ACache mCache;
    protected ARealm mRealm;
    protected Gson mGson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(initLayout(), container, false);
        initFiled();
        initData(mArguments);
        initEvent();
        return mContentView;
    }

    protected abstract int initLayout();

    private void initFiled() {
        ButterKnife.inject(this, mContentView);
        mActivity = getActivity();
        mArguments = getArguments();
        mResources = getResources();
        mHttp = OkHttpClientManager.getInstance();
        mImageLoader = ImageLoader.getInstance();
        mCache = ACache.get(mActivity);
        mRealm = ARealm.getInstance(mActivity);
        mGson=new Gson();
    }

    protected void initData(Bundle arguments) {
    }

    protected void initEvent() {
    }

    protected void startActivity(Class cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }
}
