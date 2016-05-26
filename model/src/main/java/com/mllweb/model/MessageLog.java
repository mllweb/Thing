package com.mllweb.model;

/**
 * Created by Android on 2016/5/25.
 */
public class MessageLog {
    private String fromUserName;
    private int fromUserId;
    private int toUserId;
    private String toUserName;
    private String fromNickName;
    private String toNickName;
    private long messageDate;
    private String fromUserHeadImage;
    private String content;

    public MessageLog(String fromUserName, String toUserName, String fromUserHeadImage, String content) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.fromUserHeadImage = fromUserHeadImage;
        this.content = content;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public long getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(long messageDate) {
        this.messageDate = messageDate;
    }

    public String getFromUserHeadImage() {
        return fromUserHeadImage;
    }

    public void setFromUserHeadImage(String fromUserHeadImage) {
        this.fromUserHeadImage = fromUserHeadImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
