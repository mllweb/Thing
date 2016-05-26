package com.mllweb.thing.manager;

import com.mllweb.model.UserInfo;

/**
 * Created by Android on 2016/5/26.
 */
public class UserInfoManager {
    private static UserInfo mUserInfo = null;

    private UserInfoManager() {
    }

    public static UserInfo getInstance() {
        return mUserInfo;
    }

    public static void init(UserInfo userinfo) {
        mUserInfo = userinfo;
    }
}
