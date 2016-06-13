package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mllweb.model.Comment;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.mllweb.thing.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by Android on 2016/5/31.
 */
public class CommentAdapter extends BaseRecyclerAdapter<Comment> {
    public CommentAdapter(List<Comment> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_comment;
    }

    public void resetData(List<Comment> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    protected void onBind(BaseHolder holder, Comment comment) {
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView date = holder.getView(R.id.tv_date);
        TextView content = holder.getView(R.id.tv_content);
        TextView commentedNickName = holder.getView(R.id.tv_commented_nick_name);
        TextView commentedContent = holder.getView(R.id.tv_commented_content);
        LinearLayout commentedLayout = holder.getView(R.id.commented_layout);

        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + comment.getHeadImage(), headImage);
        nickName.setText(comment.getNickName());
        date.setText(Utils.caleDate(new Date(comment.getCommentDate())));
        content.setText(comment.getCommentContent());
        if (comment.getCommentedId() == 0) {
            commentedLayout.setVisibility(View.GONE);
        } else {
            commentedLayout.setVisibility(View.VISIBLE);
            commentedNickName.setText(comment.getCommentedNickName());
            commentedContent.setText(comment.getCommentedContent());
        }
    }
}
