package com.mllweb.thing.ui.view.popup;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.mllweb.model.ImageFloder;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.popup.ChooseImageAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Android on 2016/5/20.
 */
public class ChooseImagePopup {
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private View mView;
    @InjectView(R.id.image_dir_list)
    RecyclerView mImageDirListView;
    private ChooseImageAdapter mChooseImageAdapter;
    private List<ImageFloder> mData;

    public ChooseImagePopup(Activity activity, List<ImageFloder> data) {
        mData = data;
        mActivity = activity;
        DisplayMetrics outMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mPopupWindow = new PopupWindow(mActivity);
        mPopupWindow.setWidth(outMetrics.widthPixels);
        mPopupWindow.setHeight((int) (outMetrics.heightPixels * 0.7));
        mView = LayoutInflater.from(mActivity).inflate(R.layout.popup_image_dir_list, null);
        mPopupWindow.setContentView(mView);
        mPopupWindow.setAnimationStyle(R.style.popup_anim_style);
        ButterKnife.inject(this, mView);

        mChooseImageAdapter = new ChooseImageAdapter(mData, mActivity);
        mImageDirListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mImageDirListView.setAdapter(mChooseImageAdapter);

    }

    public ChooseImageAdapter getmChooseImageAdapter() {
        return mChooseImageAdapter;
    }

    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    public void showAsDropDown(View anchor) {
        mPopupWindow.showAsDropDown(anchor);
    }
}
