package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.cache.ARealm;
import com.mllweb.model.Thing;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.home.ThingDetailsActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.mllweb.thing.ui.view.dialog.ShareDialog;
import com.mllweb.thing.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

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
        TextView content = holder.getView(R.id.tv_content);
        TextView topic = holder.getView(R.id.tv_topic);
        ImageView headImage = holder.getView(R.id.iv_head_image);
        TextView nickName = holder.getView(R.id.tv_nick_name);
        TextView praiseCount = holder.getView(R.id.tv_praise);
        TextView dislikeCount = holder.getView(R.id.tv_dislike);
        TextView commentCount = holder.getView(R.id.tv_comment);
        TextView shareCount = holder.getView(R.id.tv_share);
        ImageView praiseIcon = holder.getView(R.id.iv_praise);
        ImageView dislikeIcon = holder.getView(R.id.iv_dislike);
        RelativeLayout praiseLayout = holder.getView(R.id.praise_layout);
        RelativeLayout dislikeLayout = holder.getView(R.id.dislike_layout);
        RelativeLayout shareLayout = holder.getView(R.id.share_layout);
        RelativeLayout commentLayout = holder.getView(R.id.comment_layout);

        content.setText(thing.getContent());
        topic.setText("#" + thing.getTopicName() + "#");
        nickName.setText(thing.getNickName());
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + thing.getHeadImage(), headImage);
        praiseCount.setText(thing.getPraiseCount() + "");
        dislikeCount.setText(thing.getDislikeCount() + "");
        commentCount.setText(thing.getCommentCount() + "");
        shareCount.setText(thing.getShareCount() + "");
        praiseIcon.setImageResource(thing.isPraise() ? R.drawable.praise_ : R.drawable.praise);
        dislikeIcon.setImageResource(thing.isDislike() ? R.drawable.dislike_ : R.drawable.dislike);
        if (thing.isPraise() || thing.isDislike()) {
            praiseLayout.setOnClickListener(null);
            dislikeLayout.setOnClickListener(null);
        } else {
            praiseLayout.setOnClickListener(new OnPraiseClick(thing, praiseIcon, praiseCount, praiseLayout, dislikeLayout));
            dislikeLayout.setOnClickListener(new OnDislikeClick(thing, dislikeIcon, dislikeCount, praiseLayout, dislikeLayout));
        }
        commentLayout.setOnClickListener(new onCommentClick());
        shareLayout.setOnClickListener(new onShareClick());
        RecyclerView attachmentListView = holder.getView(R.id.attachment_view);
        ThingFileAdapter adapter = new ThingFileAdapter(thing.getThingFiles(), mActivity);
        attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        attachmentListView.setAdapter(adapter);


    }


    public class OnPraiseClick implements View.OnClickListener {
        private Thing thing;
        private ImageView icon;
        private TextView count;
        private RelativeLayout praiseLayout;
        private RelativeLayout dislikeLayout;

        public OnPraiseClick(Thing thing, ImageView icon, TextView count, RelativeLayout praise, RelativeLayout dislike) {
            this.thing = thing;
            this.icon = icon;
            this.count = count;
            this.praiseLayout = praise;
            this.dislikeLayout = dislike;
        }

        @Override
        public void onClick(View v) {
            if (ARealm.getInstance(mActivity).isLogged()) {
                thing.setPraise(true);
                thing.setPraiseCount(thing.getPraiseCount() + 1);
                count.setText(thing.getPraiseCount() + "");
                icon.setImageResource(R.drawable.praise_);
                praiseLayout.setOnClickListener(null);
                dislikeLayout.setOnClickListener(null);
            } else {
                Utils.toast(mActivity, "请先登录");
            }
        }
    }

    public class OnDislikeClick implements View.OnClickListener {
        private Thing thing;
        private ImageView icon;
        private TextView count;
        private RelativeLayout praiseLayout;
        private RelativeLayout dislikeLayout;

        public OnDislikeClick(Thing thing, ImageView icon, TextView count, RelativeLayout praise, RelativeLayout dislike) {
            this.thing = thing;
            this.icon = icon;
            this.count = count;
            this.praiseLayout = praise;
            this.dislikeLayout = dislike;
        }

        @Override
        public void onClick(View v) {
            if (ARealm.getInstance(mActivity).isLogged()) {
                thing.setDislike(true);
                thing.setDislikeCount(thing.getDislikeCount() + 1);
                count.setText(thing.getDislikeCount() + "");
                icon.setImageResource(R.drawable.dislike_);
                praiseLayout.setOnClickListener(null);
                dislikeLayout.setOnClickListener(null);
            } else {
                Utils.toast(mActivity, "请先登录");
            }
        }
    }

    public class onCommentClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(ThingDetailsActivity.class);
        }
    }

    private class onShareClick implements View.OnClickListener, ShareDialog.OnShareListener {
        @Override
        public void onClick(View v) {
            ShareDialog shareDialog = new ShareDialog();
            shareDialog.show(((BaseActivity) mActivity).getSupportFragmentManager(), "");
            shareDialog.setOnShareListener(this);
        }

        @Override
        public void share(int platform) {
            switch (platform){
                case ShareDialog.QQ:
                    break;
                case ShareDialog.QZONE:
                    break;
                case ShareDialog.WXFRIEND:
                    break;
                case ShareDialog.WXGROUP:
                    break;
                case ShareDialog.SINA:
                    break;
            }
        }
    }
}
