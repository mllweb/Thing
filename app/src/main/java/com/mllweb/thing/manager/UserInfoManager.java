package com.mllweb.thing.manager;

import com.google.gson.Gson;
import com.mllweb.cache.ACache;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.thing.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Android on 2016/5/26.
 */
public class UserInfoManager {
    private static UserInfo mUserInfo = null;
    private static String key = API.LOGIN;

    private UserInfoManager() {
    }


    public static UserInfo init(String json, ACache cache) {
        try {
            mUserInfo = new Gson().fromJson(new JSONObject(json).getJSONObject("result").toString(), UserInfo.class);
            cache.put(Utils.md5(key), json);
            return mUserInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String json, ACache cache) {
        mUserInfo = init(json, cache);
    }

    public static UserInfo get(ACache cache) {
        try {
            if (mUserInfo != null) {
                return mUserInfo;
            } else {
                String json = cache.getAsString(Utils.md5(key));
                mUserInfo = new Gson().fromJson(new JSONObject(json).getJSONObject("result").toString(), UserInfo.class);
                return mUserInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
