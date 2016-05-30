package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.model.Thing;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class ThingAdapter extends BaseRecyclerAdapter<Thing> {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView praiseCount = holder.getView(R.id.tv_praise);
        TextView dislikeCount = holder.getView(R.id.tv_dislike);
        TextView commentCount = holder.getView(R.id.tv_comment);
        TextView shareCount = holder.getView(R.id.tv_share);
        RelativeLayout praiseLayout = holder.getView(R.id.praise_layout);
        RelativeLayout dislikeLayout = holder.getView(R.id.dislike_layout);
        RelativeLayout shareLayout = holder.getView(R.id.share_layout);
        RelativeLayout commentLayout = holder.getView(R.id.comment_layout);

        content.setText(thing.getContent());
        topic.setText("#" + thing.getTopicName() + "#");
        createDate.setText(format.format(new Date(thing.getCreateDate())));
        nickName.setText(thing.getNickName());
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + thing.getHeadImage(), headImage);
        praiseCount.setText(thing.getPraiseCount() + "");
        dislikeCount.setText(thing.getDislikeCount() + "");
        commentCount.setText(thing.getCommentCount() + "");
        shareCount.setText(thing.getShareCount() + "");

        RecyclerView attachmentListView = holder.getView(R.id.attachment_view);
        ThingFileAdapter adapter = new ThingFileAdapter(thing.getThingFiles(), mActivity);
        attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        attachmentListView.setAdapter(adapter);
    }
}
