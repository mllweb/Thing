package com.mllweb.thing.ui.activity.main.center;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.mllweb.loader.ImageLoader;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.post.ChooseFileActivity;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

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
        mImageLoader.displayImage(OkHttpClientManager.DOMAIN + mUser.getHeadImage(), mHeadImage, Utils.getListOptions());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            File[] file = (File[]) data.getSerializableExtra("files");
            uploadImage(file[0]);
        }
    }

    private void uploadImage(File file) {
        showLoading("正在上传图片...");
        String fileName = Utils.getFileName();
        mHttp.requestPostDomain(API.FILE_UPLOAD, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hideLoading();
            }

            @Override
            public void onResponse(Response response, String body) {
                updateHeadImage(fileName, file);
            }
        }, file, fileName);
    }

    private void updateHeadImage(String fileName, File f) {
        mHttp.requestPostDomain(API.UPDATE_USER_INFO, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(Response response, String body) {
                        hideLoading();
                        ImageLoader.getInstance().loadImage(f.getAbsolutePath(), mHeadImage);
                        mUser.setHeadImage(API.IMAGE + fileName);
                        UserInfoManager.put(mUser, mActivity);
                    }
                }, OkHttpClientManager.Params.get("id", mUser.getId() + "")
                , OkHttpClientManager.Params.get("headImage", fileName));
    }

    @OnClick(R.id.layout_nick_name)
    public void clickNickName() {
        Intent intent = new Intent(mActivity, EditInfoActivity.class);
        intent.putExtra("type", EditInfoActivity.NICK_NAME);
        startActivity(intent);
    }

    @OnClick(R.id.head_image)
    public void clickHeadImage() {
        Intent intent = new Intent(mActivity, ChooseFileActivity.class);
        startActivityForResult(intent, 0);
    }

}
