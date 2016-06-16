package com.mllweb.thing.ui.activity.main.center.thing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mllweb.model.Thing;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.ACacheManager;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.home.ThingAdapter;
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

public class AboutMeThingActivity extends BaseActivity {
    public static final int ABOUT_ME_PRAISE = 1;
    public static final int ABOUT_ME_COLLECT = 2;
    public static final int ABOUT_ME_MINE = 3;
    @InjectView(R.id.tv_title)
    TextView mTitle;
    @InjectView(R.id.things_list)
    RecyclerView mThingsView;
    private ThingAdapter mThingAdapter;
    private List<Thing> mThingList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_about_me_thing;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mThingsView.setLayoutManager(new LinearLayoutManager(mActivity));
        mThingAdapter = new ThingAdapter(mThingList, mActivity);
        mThingsView.setAdapter(mThingAdapter);
        switch (getIntent().getIntExtra("type", 0)) {
            case ABOUT_ME_PRAISE:
                mTitle.setText("我顶过的帖子");
                selectMinePraise();
                break;
            case ABOUT_ME_COLLECT:
                mTitle.setText("我收藏的帖子");
                selectMineCollect();
                break;
            case ABOUT_ME_MINE:
                mTitle.setText("我发表的帖子");
                selectMineThing();
                break;
        }
    }

    private void selectMineThing() {
        mHttp.requestPostDomain(API.SELECT_MINE_THING, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        ACacheManager manager = ACacheManager.getInstatnce(mActivity);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.optJSONObject(i);
                            Thing thing = mGson.fromJson(o.toString(), Thing.class);
                            mThingList.add(0, thing);
                            manager.insertThing(o);
                        }
                        mThingAdapter.resetData(mThingList);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + ""));
    }

    private void selectMineCollect() {
        mHttp.requestPostDomain(API.SELECT_MINE_THING, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        ACacheManager manager = ACacheManager.getInstatnce(mActivity);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.optJSONObject(i);
                            Thing thing = mGson.fromJson(o.toString(), Thing.class);
                            mThingList.add(0, thing);
                            manager.insertThing(o);
                        }
                        mThingAdapter.resetData(mThingList);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + ""));
    }

    private void selectMinePraise() {
        mHttp.requestPostDomain(API.SELECT_MY_PRAISE_THING, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                try {
                    JSONObject object = new JSONObject(body);
                    JSONObject responseObject = object.optJSONObject("response");
                    if (responseObject.optString("state").equals(API.SUCC)) {
                        JSONArray data = responseObject.optJSONArray("data");
                        ACacheManager manager = ACacheManager.getInstatnce(mActivity);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.optJSONObject(i);
                            Thing thing = mGson.fromJson(o.toString(), Thing.class);
                            mThingList.add(0, thing);
                            manager.insertThing(o);
                        }
                        mThingAdapter.resetData(mThingList);
                    } else {
                        Utils.toast(mActivity, responseObject.optString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + ""));
    }
}
