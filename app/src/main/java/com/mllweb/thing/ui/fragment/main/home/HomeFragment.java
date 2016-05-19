package com.mllweb.thing.ui.fragment.main.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mllweb.model.Thing;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.main.home.ThingAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Android on 2016/5/18.
 */
public class HomeFragment extends BaseFragment {
    @InjectView(R.id.things_list)
    RecyclerView mThingsView;
    private ThingAdapter mThingAdapter;
    private List<Thing> mThingList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData(Bundle arguments) {
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingList.add(new Thing());
        mThingAdapter = new ThingAdapter(mThingList, mActivity);
        mThingsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mThingsView.setAdapter(mThingAdapter);
    }
}
