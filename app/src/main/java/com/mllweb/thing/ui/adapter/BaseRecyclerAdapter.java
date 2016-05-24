package com.mllweb.thing.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
    private BaseHolder.OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(BaseHolder.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public BaseRecyclerAdapter(List<T> mData, Activity activity) {
        if (mData != null) {
            this.mData = mData;
        }
        this.mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = new BaseHolder(mInflater.inflate(onCreate(), parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseHolder holder, final int position) {
        if (mItemClickListener != null) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.itemClick(holder.getItemView(), position);
                }
            });
        }
        onBind(holder, mData.get(position));
    }

    protected abstract int onCreate();

    protected abstract void onBind(BaseHolder holder, T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected void startActivity(Class cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }

}
