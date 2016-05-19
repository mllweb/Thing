package com.mllweb.thing.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/12.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    protected Activity mActivity;
    protected LayoutInflater mInflater;
    protected List<T> mData = new ArrayList<>();

    public BaseRecyclerAdapter(List<T> mData, Activity activity) {
        if (mData != null) {
            this.mData = mData;
        }
        this.mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = new BaseHolder(mInflater.inflate(onCreate(), parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        onBind(holder, mData.get(position));
    }

    protected abstract int onCreate();

    protected abstract void onBind(BaseHolder holder, T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
