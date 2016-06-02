package com.mllweb.thing.manager;

import android.app.Activity;

import com.google.gson.Gson;
import com.mllweb.cache.ARealm;
import com.mllweb.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Android on 2016/5/26.
 */
public class UserInfoManager {
    private static UserInfo mUserInfo = null;

    private UserInfoManager() {
    }


    public static UserInfo init(String json, Activity activity) {
        try {
            mUserInfo = new Gson().fromJson(new JSONObject(json).getJSONObject("result").toString(), UserInfo.class);
            return mUserInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String json, Activity activity) {
        try {
            mUserInfo = new Gson().fromJson(new JSONObject(json).getJSONObject("result").toString(), UserInfo.class);
            ARealm.getInstance(activity).updateUserJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static UserInfo get(Activity activity) {
        try {
            if (mUserInfo != null) {
                return mUserInfo;
            } else {
                String json = ARealm.getInstance(activity).getUserJson();
                mUserInfo = new Gson().fromJson(new JSONObject(json).getJSONObject("result").toString(), UserInfo.class);
                return mUserInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
