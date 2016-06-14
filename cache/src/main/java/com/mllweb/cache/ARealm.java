package com.mllweb.cache;

import android.app.Activity;

import com.mllweb.model.Login;
import com.mllweb.model.LoginLog;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Android on 2016/5/30.
 */
public class ARealm {
    private RealmConfiguration mConfiguration;
    private Activity mActivity;
    private static ARealm mInstance;

    private ARealm(Activity activity) {
        mActivity = activity;
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(mActivity);
        mConfiguration = builder.build();
    }

    public synchronized static ARealm getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new ARealm(activity);
        }
        return mInstance;
    }

    /**
     * 登录
     *
     * @param userId
     */
    public void login(int userId, String json) {
        Realm realm = Realm.getInstance(mConfiguration);
        realm.beginTransaction();
        //修改登录状态和登录用户
        Login login = realm.where(Login.class).findFirst();
        if (login == null) {
            login = realm.createObject(Login.class);
        }
        login.setUserJson(json);
        login.setUserId(userId);
        login.setLogged(true);
        //增加登录记录
        LoginLog loginLog = realm.createObject(LoginLog.class);
        loginLog.setUserId(userId);
        loginLog.setLoginDate(new Date().getTime());
        realm.commitTransaction();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogged() {
        Realm realm = Realm.getInstance(mConfiguration);
        Login login = realm.where(Login.class).findFirst();
        if (login == null) {
            return false;
        } else {
            return login.isLogged();
        }
    }

    /**
     * 退出
     */
    public void logout() {
        Realm realm = Realm.getInstance(mConfiguration);
        realm.beginTransaction();
        Login login = realm.where(Login.class).findFirst();
        login.setLogged(false);
        realm.commitTransaction();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public String getUserJson() {
        Realm realm = Realm.getInstance(mConfiguration);
        Login login = realm.where(Login.class).findFirst();
        return login.getUserJson();
    }

    /**
     * 修改用户信息
     *
     * @param json
     */
    public void updateUserJson(String json) {
        Realm realm = Realm.getInstance(mConfiguration);
        Login login = realm.where(Login.class).findFirst();
        realm.beginTransaction();
        login.setUserJson(json);
        realm.commitTransaction();
    }

}
