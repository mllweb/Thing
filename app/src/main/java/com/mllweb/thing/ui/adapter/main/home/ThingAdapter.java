package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;

import com.mllweb.model.Thing;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/5/18.
 */
public class ThingAdapter extends BaseRecyclerAdapter<Thing> {

    public ThingAdapter(List<Thing> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_thing;
    }



    @Override
    protected void onBind(BaseHolder holder, Thing thing) {

    }
}
