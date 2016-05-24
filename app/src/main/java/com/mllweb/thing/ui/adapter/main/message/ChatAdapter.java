package com.mllweb.thing.ui.adapter.main.message;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ChatAdapter extends RecyclerView.Adapter<BaseHolder> {
    private List<Object> mData = new ArrayList<>();
    private Activity mActivity;
    private LayoutInflater mInflater;
    public ChatAdapter(List<Object> mData, Activity activity) {
        if (mData != null) {
            this.mData = mData;
        }
        this.mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder=null;
        switch (viewType){
            case 0:
                 holder = new BaseHolder(mInflater.inflate(R.layout.adapter_chat_left, parent, false));
                break;
            case 1:
                 holder = new BaseHolder(mInflater.inflate(R.layout.adapter_chat_right, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 0;
        }else{
            return 1;
        }
    }
}
