package com.mllweb.thing.ui.fragment.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mllweb.model.Thing;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.main.home.ThingDetailsActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.main.home.ThingAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.mllweb.thing.utils.Utils;
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
        mThingsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mThingAdapter = new ThingAdapter(mThingList, mActivity);
        mThingsView.setAdapter(mThingAdapter);
        int userId = 0;
        if (mRealm.isLogged()) {
            userId = UserInfoManager.get(mActivity).getId();
        }
        mHttp.requestPostDomain(API.SELECT_THING, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    JSONObject object = new JSONObject(mCache.getAsString("thing"));
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.getJSONObject(i);
                            Thing thing = mGson.fromJson(o.toString(), Thing.class);
                            mThingList.add(thing);
                        }
                        mThingAdapter.resetData(mThingList);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response, String body) {
                mCache.put("thing", body);
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.getJSONObject(i);
                            Thing thing = mGson.fromJson(o.toString(), Thing.class);
                            mThingList.add(thing);
                        }
                        mThingAdapter.resetData(mThingList);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("userId", userId + ""));
    }

    @Override
    protected void initEvent() {
        mThingAdapter.setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void itemClick(View rootView, int position) {
                Intent intent = new Intent(mActivity, ThingDetailsActivity.class);
                intent.putExtra("thing", mThingList.get(position));
                startActivity(intent);
            }
        });
    }

    public void loadMore() {
        Utils.toast(mActivity, "空啊早给你更多");
    }
}
