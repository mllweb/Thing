package com.mllweb.thing.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Android on 2016/5/18.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Bundle mArguments;
    protected View mContentView;
    protected Resources mResources;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        mResources=getResources();
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
