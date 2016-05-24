package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.home.BrowseActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/5/24.
 */
public class ThingAttachmentAdapter extends BaseRecyclerAdapter<String> {
    public ThingAttachmentAdapter(List mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing_attachment;
    }

    @Override
    protected void onBind(BaseHolder holder, String s) {
        RelativeLayout layout = holder.getView(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(BrowseActivity.class);
                mActivity.overridePendingTransition(R.anim.browse_start,R.anim.no_anim);
            }
        });
    }

}
