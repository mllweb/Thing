package com.mllweb.thing.ui.adapter.main.message;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.mllweb.model.Message;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.mllweb.thing.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class MessageAdapter extends BaseRecyclerAdapter<Message> {

    public MessageAdapter(List<Message> mData, Activity activity) {
        super(mData, activity);
    }

    public void resetData(List<Message> data) {
        if (data != null) {
            mData = data;
            notifyDataSetChanged();
        }
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_message;
    }


    @Override
    protected void onBind(BaseHolder holder, Message message) {
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView content = holder.getView(R.id.tv_content);
        TextView date = holder.getView(R.id.tv_date);
        BadgeView badge = null;
        if (headImage.getTag() == null) {
            badge = new BadgeView(mActivity);
            badge.setTargetView(headImage);
            headImage.setTag(badge);
        } else {
            badge = (BadgeView) headImage.getTag();
        }
        badge.setBadgeCount(message.getUnreadCount());
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + message.getHeadImage(), headImage);
        nickName.setText(message.getNickName());
        content.setText(message.getContent());
        date.setText(Utils.caleDate(message.getCreateDate()));
    }
}
