package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.model.Thing;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class ThingAdapter extends BaseRecyclerAdapter<Thing> {

    public ThingAdapter(List<Thing> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing;
    }


    @Override
    protected void onBind(BaseHolder holder, Thing thing) {
        TextView content = holder.getView(R.id.tv_comment);
        TextView topic = holder.getView(R.id.tv_topic);
        TextView createDate = holder.getView(R.id.tv_create_date);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView praiseCount = holder.getView(R.id.tv_praise);
        TextView dislikeCount = holder.getView(R.id.tv_dislike);
        TextView commentCount = holder.getView(R.id.tv_comment);
        TextView shareCount = holder.getView(R.id.tv_share);
        TextView follow = holder.getView(R.id.tv_follow);
        RelativeLayout praiseLayout = holder.getView(R.id.praise_layout);
        RelativeLayout dislikeLayout = holder.getView(R.id.dislike_layout);
        RelativeLayout shareLayout = holder.getView(R.id.share_layout);
        RelativeLayout commentLayout = holder.getView(R.id.comment_layout);
        RecyclerView attachmentListView = holder.getView(R.id.attachment_view);
        List<String> data = new ArrayList<>();
        data.add(""); data.add(""); data.add(""); data.add(""); data.add(""); data.add(""); data.add(""); data.add(""); data.add("");
        ThingAttachmentAdapter adapter = new ThingAttachmentAdapter(data, mActivity);
        attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        attachmentListView.setAdapter(adapter);
    }
}
