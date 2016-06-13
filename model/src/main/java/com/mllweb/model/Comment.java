package com.mllweb.model;

/**
 * Created by Android on 2016/5/31.
 */
public class Comment {
    private int id;
    private int thingId;
    private int commentUserId;
    private String commentContent;
    private long commentDate;
    private int commentedUserId;
    private int commentedId;
    private String commentedContent;
    private String nickName;
    private String commentedNickName;
    private String headImage;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThingId() {
        return thingId;
    }

    public void setThingId(int thingId) {
        this.thingId = thingId;
    }

    public int getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(int commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(long commentDate) {
        this.commentDate = commentDate;
    }

    public int getCommentedUserId() {
        return commentedUserId;
    }

    public void setCommentedUserId(int commentedUserId) {
        this.commentedUserId = commentedUserId;
    }

    public int getCommentedId() {
        return commentedId;
    }

    public void setCommentedId(int commentedId) {
        this.commentedId = commentedId;
    }

    public String getCommentedContent() {
        return commentedContent;
    }

    public void setCommentedContent(String commentedContent) {
        this.commentedContent = commentedContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCommentedNickName() {
        return commentedNickName;
    }

    public void setCommentedNickName(String commentedNickName) {
        this.commentedNickName = commentedNickName;
    }
}
