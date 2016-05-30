package com.mllweb.model;

import io.realm.RealmObject;

/**
 * Created by Android on 2016/5/30.
 */
public class Login extends RealmObject {
    private int userId;
    private boolean isLogged;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
