package com.mllweb.thing.ui.adapter.main.post;

import android.app.Activity;
import android.widget.TextView;

import com.mllweb.model.Topic;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/6/8.
 */
public class ChooseTopicAdapter extends BaseRecyclerAdapter<Topic> {
    public ChooseTopicAdapter(List<Topic> mData, Activity activity) {
        super(mData, activity);
    }

    public void resetData(List<Topic> data) {
        if(data!=null){
            mData=data;
            notifyDataSetChanged();
        }
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_topic;
    }

    @Override
    protected void onBind(BaseHolder holder, Topic topic) {
        TextView topicName = holder.getView(R.id.tv_topic_name);
    }
}
