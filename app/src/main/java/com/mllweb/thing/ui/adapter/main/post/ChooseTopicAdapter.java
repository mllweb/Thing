package com.mllweb.thing.ui.adapter.main.post;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.model.Topic;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Android on 2016/6/8.
 */
public class ChooseTopicAdapter extends BaseRecyclerAdapter<Topic> {
    public ChooseTopicAdapter(List<Topic> mData, Activity activity) {
        super(mData, activity);
    }

    public void resetData(List<Topic> data) {
        if (data != null) {
            mData = data;
            notifyDataSetChanged();
        }
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_topic;
    }

    @Override
    protected void onBind(BaseHolder holder, Topic topic) {
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView topicName = holder.getView(R.id.tv_topic_name);
        TextView partner = holder.getView(R.id.tv_partner);
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + topic.getTopicHeadImage(), headImage);
        topicName.setText("#" + topic.getTopicName() + "#");
        partner.setText(String.format("一共有%d位%s", 0, topic.getTopicPartnerName()));
    }
}
