package com.mllweb.thing.manager;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.mllweb.cache.ARealm;
import com.mllweb.model.UserInfo;

/**
 * Created by Android on 2016/5/26.
 */
public class UserInfoManager {
    private static UserInfo mUserInfo = null;

    private UserInfoManager() {
    }


    public static UserInfo put(String json, Activity activity) {
        mUserInfo = new Gson().fromJson(json, UserInfo.class);
        ARealm.getInstance(activity).updateUserJson(json);
        return mUserInfo;
    }

    public static UserInfo put(UserInfo userInfo, Activity activity) {
        return put(new Gson().toJson(userInfo), activity);
    }

    public static UserInfo get(Activity activity) {
        if (mUserInfo != null) {
            return mUserInfo;
        } else {
            String json = ARealm.getInstance(activity).getUserJson();
            mUserInfo = new Gson().fromJson(json, UserInfo.class);
            return mUserInfo;
        }
    }
    public static UserInfo get(Context context) {
        if (mUserInfo != null) {
            return mUserInfo;
        } else {
            String json = ARealm.getInstance(context).getUserJson();
            mUserInfo = new Gson().fromJson(json, UserInfo.class);
            return mUserInfo;
        }
    }
}
