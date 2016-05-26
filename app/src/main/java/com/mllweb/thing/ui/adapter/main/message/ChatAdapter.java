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
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ChatAdapter extends RecyclerView.Adapter<BaseHolder> {
    private final static int CHAT_FROM_MINE = 0;
    private final static int CHAT_TO_MINE = 1;
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
            case CHAT_FROM_MINE:
                holder = new BaseHolder(mInflater.inflate(R.layout.adapter_chat_right, parent, false));
                break;
            case CHAT_TO_MINE:
                holder = new BaseHolder(mInflater.inflate(R.layout.adapter_chat_left, parent, false));
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
            case CHAT_FROM_MINE:
                ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + UserInfoManager.getInstance().getHeadImage(), headImage);
                content.setText(log.getContent());
                break;
            case CHAT_TO_MINE:
                ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + log.getFromUserHeadImage(), headImage);
                nickName.setText(log.getFromUserName());
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
        if (mData.get(position).getFromUserName().equals(UserInfoManager.getInstance().getUserName())) {
            return CHAT_FROM_MINE;
        } else {
            return CHAT_TO_MINE;
        }
    }
}
