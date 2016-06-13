package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.cache.ARealm;
import com.mllweb.model.Thing;
import com.mllweb.model.ThingFile;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.WebActivity;
import com.mllweb.thing.ui.activity.main.home.ThingDetailsActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.mllweb.thing.ui.view.dialog.ShareDialog;
import com.mllweb.thing.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.IOException;
import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class ThingAdapter extends BaseRecyclerAdapter<Thing> implements UMShareListener {

    public ThingAdapter(List<Thing> mData, Activity activity) {
        super(mData, activity);
    }

    public Thing mShareThing;
    public TextView mShareTextCount;

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing;
    }

    public void resetData(List<Thing> list) {
        if (list != null) {
            mData = list;
            notifyDataSetChanged();
        }
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
        TextView link = holder.getView(R.id.tv_link);
        ImageView praiseIcon = holder.getView(R.id.iv_praise);
        ImageView dislikeIcon = holder.getView(R.id.iv_dislike);
        RelativeLayout praiseLayout = holder.getView(R.id.praise_layout);
        RelativeLayout dislikeLayout = holder.getView(R.id.dislike_layout);
        RelativeLayout shareLayout = holder.getView(R.id.share_layout);
        RelativeLayout commentLayout = holder.getView(R.id.comment_layout);

        content.setText(thing.getContent());
        topic.setText("#" + thing.getTopicName() + "#");
        if (thing.getLink() != null && (!thing.getLink().equals(""))) {
            link.setText(thing.getLink());
            link.setVisibility(View.VISIBLE);
            link.setOnClickListener(new OnLinkClick(thing.getLink()));
        } else {
            link.setVisibility(View.GONE);
        }
        nickName.setText(thing.getNickName());
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + thing.getHeadImage(), headImage,Utils.getListOptions());
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
        commentLayout.setOnClickListener(new onCommentClick(thing));
        shareLayout.setOnClickListener(new OnShareClick(thing, shareCount));
        //资源
        RecyclerView attachmentListView = holder.getView(R.id.attachment_view);
        List<ThingFile> files = thing.getThingFiles();
        if (files != null && files.size() > 0) {
            ThingFileAdapter adapter = new ThingFileAdapter(files, mActivity);
            if (files.size() == 1) {
                attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 1));
            } else if (files.size() <= 4) {
                attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 2));
            } else {
                attachmentListView.setLayoutManager(new GridLayoutManager(mActivity, 3));
            }
            attachmentListView.setAdapter(adapter);
        } else {
            attachmentListView.setAdapter(null);
        }
    }

    public class OnLinkClick implements View.OnClickListener {
        String link;

        public OnLinkClick(String link) {
            this.link = link;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, WebActivity.class);
            intent.putExtra("URI", link);
            mActivity.startActivity(intent);
        }
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
                praiseLayout.setOnClickListener(null);
                dislikeLayout.setOnClickListener(null);
                OkHttpClientManager.getInstance().requestPostDomain(API.INSERT_THING_PRAISE, new OkHttpClientManager.RequestCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                praiseLayout.setOnClickListener(OnPraiseClick.this);
                                dislikeLayout.setOnClickListener(OnPraiseClick.this);
                            }

                            @Override
                            public void onResponse(Response response, String body) {
                                thing.setPraise(true);
                                thing.setPraiseCount(thing.getPraiseCount() + 1);
                                count.setText(thing.getPraiseCount() + "");
                                icon.setImageResource(R.drawable.praise_);
                            }
                        }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                        , OkHttpClientManager.Params.get("thingId", thing.getId() + ""));
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
                praiseLayout.setOnClickListener(null);
                dislikeLayout.setOnClickListener(null);
                OkHttpClientManager.getInstance().requestPostDomain(API.INSERT_THING_DISLIKE, new OkHttpClientManager.RequestCallback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                praiseLayout.setOnClickListener(OnDislikeClick.this);
                                dislikeLayout.setOnClickListener(OnDislikeClick.this);
                            }

                            @Override
                            public void onResponse(Response response, String body) {
                                thing.setDislike(true);
                                thing.setDislikeCount(thing.getDislikeCount() + 1);
                                count.setText(thing.getDislikeCount() + "");
                                icon.setImageResource(R.drawable.dislike_);
                            }
                        }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                        , OkHttpClientManager.Params.get("thingId", thing.getId() + ""));
            } else {
                Utils.toast(mActivity, "请先登录");
            }
        }
    }

    public class onCommentClick implements View.OnClickListener {
        Thing thing;

        public onCommentClick(Thing thing) {
            this.thing = thing;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, ThingDetailsActivity.class);
            intent.putExtra("thing", thing);
            mActivity.startActivity(intent);
        }
    }

    public class OnShareClick implements View.OnClickListener, ShareDialog.OnShareListener {
        Thing shareThing;
        TextView shareTextCount;

        public OnShareClick(Thing thing, TextView textCount) {
            shareThing = thing;
            shareTextCount = textCount;
        }

        @Override
        public void onClick(View v) {
            ShareDialog shareDialog = new ShareDialog();
            shareDialog.show(((BaseActivity) mActivity).getSupportFragmentManager(), "");
            shareDialog.setOnShareListener(this);
        }

        @Override
        public void share(SHARE_MEDIA platform) {
            mShareTextCount = shareTextCount;
            mShareThing = shareThing;
            sharePlatform(platform);
        }
    }

    private void sharePlatform(SHARE_MEDIA platform) {
        new ShareAction(mActivity)
                .setPlatform(platform)
                .setCallback(this)
                .withTitle("标题")
                .withText("那点事儿")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(new UMImage(mActivity, R.mipmap.ic_launcher))
                .share();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Utils.toast(mActivity, "分享成功了");
        OkHttpClientManager.getInstance().requestPostDomain(API.INSERT_THING_SHARE, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        mShareThing.setShareCount(mShareThing.getShareCount() + 1);
                        mShareTextCount.setText(mShareThing.getShareCount() + "");
                    }
                }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + "")
                , OkHttpClientManager.Params.get("thingId", mShareThing.getId() + "")
                , OkHttpClientManager.Params.get("platform", share_media.name()));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Utils.toast(mActivity, "分享失败了");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Utils.toast(mActivity, "分享取消了");
    }
}
