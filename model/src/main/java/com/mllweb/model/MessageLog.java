package com.mllweb.model;

/**
 * Created by Android on 2016/5/25.
 */
public class MessageLog {
    private String nickName;
    private String headImage;
    private long createDate;
    private String content;
    private int userId;

    public MessageLog(String nickName, String headImage, String content, int userId) {
        this.nickName = nickName;
        this.headImage = headImage;
        this.content = content;
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
