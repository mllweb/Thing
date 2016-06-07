package com.mllweb.thing.ui.activity.main.center;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.mllweb.model.UserInfo;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class BaseInfoActivity extends BaseActivity {
    @InjectView(R.id.iv_head_image)
    ImageView mHeadImage;
    @InjectView(R.id.tv_user_name)
    TextView mUserName;
    @InjectView(R.id.tv_nick_name)
    TextView mNickName;
    @InjectView(R.id.tv_gender)
    TextView mGender;
    @InjectView(R.id.tv_user_sign)
    TextView mUserSign;

    private UserInfo mUser;

    @Override
    protected int initLayout() {
        return R.layout.activity_base_info;
    }


    private void initBaseData() {
        mUser = UserInfoManager.get(mActivity);
        mUserName.setText("".equals(mUser.getUserName()) ? "未填写" : mUser.getUserName());
        mNickName.setText("".equals(mUser.getNickName()) ? "未填写" : mUser.getNickName());
        mUserSign.setText("".equals(mUser.getUserSign()) ? "未填写" : mUser.getUserSign());
        switch (mUser.getGender()) {
            case 0:
                mGender.setText(R.string.female);
                break;
            case 1:
                mGender.setText(R.string.male);
                break;
            case 2:
                mGender.setText(R.string.no_filled);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBaseData();
    }

    @OnClick(R.id.logout)
    public void clickLogout() {
        mRealm.logout();
        EMClient.getInstance().logout(true);
        finish();
    }

    @OnClick(R.id.layout_user_sign)
    public void clickSign() {
        Intent intent = new Intent(mActivity, EditInfoActivity.class);
        intent.putExtra("type", EditInfoActivity.USER_SIGN);
        startActivity(intent);
    }

    @OnClick(R.id.layout_user_name)
    public void clickUserName() {
        if (mUser.getUserName().equals("")) {
            Intent intent = new Intent(mActivity, EditInfoActivity.class);
            intent.putExtra("type", EditInfoActivity.USER_NAME);
            startActivity(intent);
        }
    }

    @OnClick(R.id.layout_nick_name)
    public void clickNickName() {
        Intent intent = new Intent(mActivity, EditInfoActivity.class);
        intent.putExtra("type", EditInfoActivity.NICK_NAME);
        startActivity(intent);
    }
}
