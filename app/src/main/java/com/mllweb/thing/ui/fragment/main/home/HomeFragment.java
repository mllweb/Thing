package com.mllweb.thing.ui.fragment.main.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mllweb.model.Thing;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.main.home.ThingAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

        mHttp.requestGetDomain(API.SELECT_THING, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONArray jsonArray = new JSONArray(body);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Thing thing = mGson.fromJson(o.toString(), Thing.class);
                        mThingList.add(thing);
                    }
                    mThingAdapter = new ThingAdapter(mThingList, mActivity);
                    mThingsView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mThingsView.setAdapter(mThingAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
