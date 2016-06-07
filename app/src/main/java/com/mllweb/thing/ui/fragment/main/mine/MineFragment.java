package com.mllweb.thing.ui.fragment.main.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.model.UserInfo;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.main.center.BaseInfoActivity;
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
        mImageLoader.displayImage(userinfo.getHeadImage(), mHeadImage);
        mNickName.setText(userinfo.getNickName());
        mUserSign.setText("简介：" + userinfo.getUserSign());
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

        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_comment)
    public void clickComment() {
        if (mRealm.isLogged()) {

        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_collection)
    public void clickCollection() {
        if (mRealm.isLogged()) {

        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.my_praise)
    public void clickPraise() {
        if (mRealm.isLogged()) {

        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }
}
