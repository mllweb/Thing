package com.mllweb.thing.ui.adapter.main.message;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.model.MessageLog;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ChatAdapter extends RecyclerView.Adapter<BaseHolder> {
    private List<MessageLog> mData = new ArrayList<>();
    private Activity mActivity;
    private LayoutInflater mInflater;

    public ChatAdapter(List<MessageLog> mData, Activity activity) {
        if (mData != null) {
            this.mData = mData;
        }
        this.mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseHolder holder = null;
        switch (viewType) {
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
        MessageLog log = mData.get(position);
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView date = holder.getView(R.id.tv_date);
        TextView content = holder.getView(R.id.tv_content);
        switch (getItemViewType(position)) {
            case 0:
                ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + log.getHeadImage(), headImage);
               // nickName.setVisibility(View.GONE);
                nickName.setText(log.getNickName());
                content.setText(log.getContent());
                break;
            case 1:
                ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + log.getHeadImage(), headImage);
                content.setText(log.getContent());
                break;
        }
        if (position % 5 == 0) {
            date.setVisibility(View.VISIBLE);
        } else {
            date.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getUserId() == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
