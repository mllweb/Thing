package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mllweb.model.ThingFile;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.home.BrowseActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ThingFileAdapter extends BaseRecyclerAdapter<ThingFile> {
    public ThingFileAdapter(List mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing_file;
    }

    @Override
    protected void onBind(final BaseHolder holder, ThingFile thingFile) {
        RelativeLayout layout = holder.getView(R.id.layout);
        ImageView image = holder.getView(R.id.image);
        ImageLoader.getInstance().displayImage(OkHttpClientManager.DOMAIN + thingFile.getFilePath(), image);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntentExtras("data", (Serializable) mData);
                setIntentExtras("position",holder.getPosition());
                startActivity(BrowseActivity.class);
                mActivity.overridePendingTransition(R.anim.browse_start, R.anim.no_anim);
            }
        });
    }

}
