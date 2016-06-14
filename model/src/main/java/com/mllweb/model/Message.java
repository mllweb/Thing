package com.mllweb.model;

import java.io.Serializable;

/**
 * Created by Android on 2016/5/19.
 */
public class Message implements Serializable {
    private long lastSendDate;
    private String headImage;
    private String lastSendContent;
    private String nickName;
    private int unreadCount;
    private int userId;
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getLastSendDate() {
        return lastSendDate;
    }

    public void setLastSendDate(long lastSendDate) {
        this.lastSendDate = lastSendDate;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getLastSendContent() {
        return lastSendContent;
    }

    public void setLastSendContent(String lastSendContent) {
        this.lastSendContent = lastSendContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
