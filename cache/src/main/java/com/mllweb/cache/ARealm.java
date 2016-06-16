package com.mllweb.cache;

import android.app.Activity;
import android.content.Context;

import com.mllweb.model.Login;
import com.mllweb.model.LoginLog;
import com.mllweb.model.Message;
import com.mllweb.model.MessageLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Android on 2016/5/30.
 */
public class ARealm {
    private RealmConfiguration mConfiguration;
    private Context mActivity;
    private static ARealm mInstance;

    private ARealm(Activity activity) {
        mActivity = activity;
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(mActivity);
        mConfiguration = builder.build();
    }

    private ARealm(Context context) {
        mActivity = context;
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(mActivity);
        mConfiguration = builder.build();
    }

    public synchronized static ARealm getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new ARealm(activity);
        }
        return mInstance;
    }

    public synchronized static ARealm getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ARealm(context);
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

    public List<MessageLog> selectMessageLog(String mobile1, String mobile2) {
        List<MessageLog> list = new ArrayList<>();
        Realm realm = Realm.getInstance(mConfiguration);
        RealmResults<MessageLog> logs = realm.where(MessageLog.class).beginGroup().equalTo("fromMobile", mobile1).equalTo("toMobile", mobile2).endGroup()
                .or().beginGroup().equalTo("fromMobile", mobile2).equalTo("toMobile", mobile1).endGroup().findAll();
        list.addAll(logs);
        return list;
    }

    public List<Message> selectMessage() {
        List<Message> list = new ArrayList<>();
        Realm realm = Realm.getInstance(mConfiguration);
        RealmResults<Message> msg = realm.where(Message.class).findAllSorted("lastSendDate", Sort.DESCENDING);
        list.addAll(msg);
        return list;
    }

    public void clearMessageCount(int userId) {
        Realm realm = Realm.getInstance(mConfiguration);
        Message msg = realm.where(Message.class).equalTo("fromUserId", userId).findFirst();
        realm.beginTransaction();
        msg.setUnreadCount(0);
        realm.copyToRealmOrUpdate(msg);
        realm.commitTransaction();
    }

    public void insertMessageLog(MessageLog log) {
        Realm realm = Realm.getInstance(mConfiguration);
        realm.beginTransaction();
        realm.copyToRealm(log);
        realm.commitTransaction();
    }

    public void insertMessage(Message msg) {
        Realm realm = Realm.getInstance(mConfiguration);
        Message m = realm.where(Message.class).equalTo("fromUserId", msg.getFromUserId()).findFirst();
        realm.beginTransaction();
        if (m == null) {
            realm.copyToRealm(msg);
        }else{
            m.setUnreadCount(m.getUnreadCount()+msg.getUnreadCount());
            m.setLastSendDate(msg.getLastSendDate());
            m.setLastSendContent(msg.getLastSendContent());
            realm.copyToRealmOrUpdate(m);
        }
        realm.commitTransaction();
    }
}
