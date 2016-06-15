package com.mllweb.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Android on 2016/5/19.
 */
public class Message extends RealmObject {
    private long lastSendDate;
    private String fromHeadImage;
    private String lastSendContent;
    private String fromNickName;
    private int unreadCount;
    private String fromMobile;
    @PrimaryKey
    private int fromUserId;


    public long getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(long lastSendDate) {
        this.lastSendDate = lastSendDate;
    }

    public String getFromHeadImage() {
        return fromHeadImage;
    }

    public void setFromHeadImage(String fromHeadImage) {
        this.fromHeadImage = fromHeadImage;
    }

    public String getLastSendContent() {
        return lastSendContent;
    }

    public void setLastSendContent(String lastSendContent) {
        this.lastSendContent = lastSendContent;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getFromMobile() {
        return fromMobile;
    }

    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }
}
