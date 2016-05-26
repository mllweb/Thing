package com.mllweb.model;

/**
 * Created by Android on 2016/5/26.
 */
public class UserInfo {
    private int userId;
    private String nickName;
    private String userName="mllweb";
    private String headImage="/IMAGE/head_image.jpg";

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
