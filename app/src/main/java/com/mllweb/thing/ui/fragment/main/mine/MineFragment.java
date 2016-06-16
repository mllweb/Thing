package com.mllweb.thing.ui.fragment.main.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.main.center.BaseInfoActivity;
import com.mllweb.thing.ui.activity.main.center.thing.AboutMeThingActivity;
import com.mllweb.thing.ui.activity.main.center.thing.MineCommentActivity;
import com.mllweb.thing.ui.activity.main.login.LoginActivity;
import com.mllweb.thing.ui.activity.main.setting.SettingActivity;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.mllweb.thing.utils.Utils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Android on 2016/5/18.
 */
public class MineFragment extends BaseFragment {
    @InjectView(R.id.un_login_layout)
    RelativeLayout mUnLoginLayout;
    @InjectView(R.id.login_layout)
    LinearLayout mLoginLayout;
    @InjectView(R.id.head_image)
    ImageView mHeadImage;
    @InjectView(R.id.tv_nick_name)
    TextView mNickName;
    @InjectView(R.id.tv_user_sign)
    TextView mUserSign;
    @InjectView(R.id.tv_post_count)
    TextView mPostCount;
    @InjectView(R.id.tv_comment_count)
    TextView mCommentCount;
    @InjectView(R.id.tv_collect_count)
    TextView mCollectCount;
    @InjectView(R.id.tv_praise_count)
    TextView mPraiseCount;

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRealm.isLogged()) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mUnLoginLayout.setVisibility(View.GONE);
            initUserInfo();
        } else {
            mLoginLayout.setVisibility(View.GONE);
            mUnLoginLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initUserInfo() {
        UserInfo userinfo = UserInfoManager.get(mActivity);
        mImageLoader.displayImage(API.DOMAIN + userinfo.getHeadImage(), mHeadImage, Utils.getListOptions());
        mNickName.setText(userinfo.getNickName());
        mPostCount.setText(userinfo.getPostCount() + "");
        mCommentCount.setText(userinfo.getCommentCount() + "");
        mCollectCount.setText(userinfo.getCollectCount() + "");
        mPraiseCount.setText(userinfo.getPraiseCount() + "");
        if (userinfo.getUserSign() == null || userinfo.getUserSign().equals("")) {
            mUserSign.setText("这家伙很懒，什么都没有留下");
        } else {
            mUserSign.setText("简介：" + userinfo.getUserSign());
        }
    }

    @OnClick(R.id.tv_setting)
    public void clickSetting() {
        startActivity(SettingActivity.class);
    }

    @OnClick(R.id.tv_login)
    public void clickLogin() {
        startActivity(LoginActivity.class);
    }

    @OnClick(R.id.login_layout)
    public void clickUserInfo() {
        startActivity(BaseInfoActivity.class);
    }

    @OnClick(R.id.my_post)
    public void clickPost() {
        if (mRealm.isLogged()) {
            Intent intent = new Intent(mActivity, AboutMeThingActivity.class);
            intent.putExtra("type", AboutMeThingActivity.ABOUT_ME_MINE);
            startActivity(intent);
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_comment)
    public void clickComment() {
        if (mRealm.isLogged()) {
            startActivity(MineCommentActivity.class);
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_collection)
    public void clickCollection() {
        if (mRealm.isLogged()) {
            Intent intent = new Intent(mActivity, AboutMeThingActivity.class);
            intent.putExtra("type", AboutMeThingActivity.ABOUT_ME_COLLECT);
            startActivity(intent);
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_praise)
    public void clickPraise() {
        if (mRealm.isLogged()) {
            Intent intent = new Intent(mActivity, AboutMeThingActivity.class);
            intent.putExtra("type", AboutMeThingActivity.ABOUT_ME_PRAISE);
            startActivity(intent);
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }
}
