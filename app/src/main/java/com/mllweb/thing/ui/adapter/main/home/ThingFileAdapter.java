package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mllweb.model.ThingFile;
import com.mllweb.network.API;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.home.BrowseActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ThingFileAdapter extends BaseRecyclerAdapter<ThingFile> {
    public ThingFileAdapter(List mData, Activity activity) {
        super(mData, activity);
        round = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, mActivity.getResources().getDisplayMetrics());
    }

    private int round;

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing_file;
    }

    @Override
    protected void onBind(final BaseHolder holder, ThingFile thingFile) {
        RelativeLayout layout = holder.getView(R.id.layout);
        ImageView image = holder.getView(R.id.image);
        ImageView gifIcon = holder.getView(R.id.iv_gif_icon);
        if (thingFile.getFileType() == 1) {
            gifIcon.setVisibility(View.GONE);
        } else if (thingFile.getFileType() == 2) {
            gifIcon.setVisibility(View.VISIBLE);
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        if (mData.size() == 1) {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, mActivity.getResources().getDisplayMetrics());
        } else if (mData.size() <= 4) {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, mActivity.getResources().getDisplayMetrics());
        } else {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, mActivity.getResources().getDisplayMetrics());
        }
        image.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(API.DOMAIN + thingFile.getFilePath(), image, new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(round)).build());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntentExtras("data", (Serializable) mData);
                setIntentExtras("position", holder.getPosition());
                startActivity(BrowseActivity.class);
                mActivity.overridePendingTransition(R.anim.browse_start, R.anim.no_anim);
            }
        });
    }

}
