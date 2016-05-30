package com.mllweb.model;

import io.realm.RealmObject;

/**
 * Created by Android on 2016/5/30.
 */
public class LoginLog extends RealmObject {
    private int userId;
    private long loginDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }
}
