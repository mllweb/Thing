package com.mllweb.thing.ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.mllweb.thing.R;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Android on 2016/6/1.
 */
public class ShareDialog extends DialogFragment {
    private View mView;
    private OnShareListener listener;

    public void setOnShareListener(OnShareListener listener) {
        this.listener = listener;
    }

    public ShareDialog() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = inflater.inflate(R.layout.dialog_share, container, false);
        ButterKnife.inject(this, mView);
        return mView;
    }

    @OnClick(R.id.qq)
    public void clickQQ() {
        if (listener != null) {
            listener.share(SHARE_MEDIA.QQ);
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.qzone)
    public void clickQZONE() {
        if (listener != null) {
            listener.share(SHARE_MEDIA.QZONE);
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.wxfriend)
    public void clickWXFriend() {
        if (listener != null) {
            listener.share(SHARE_MEDIA.WEIXIN);
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.wxgrounp)
    public void clickWXGroup() {
        if (listener != null) {
            listener.share(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        getDialog().dismiss();
    }

    @OnClick(R.id.sina)
    public void clickSina() {
        if (listener != null) {
            listener.share(SHARE_MEDIA.SINA);
        }
        getDialog().dismiss();
    }

    public interface OnShareListener {
        void share(SHARE_MEDIA platform);
    }
}
