package com.mllweb.model;

/**
 * Created by Android on 2016/6/8.
 */
public class Topic {
    private int id;
    private int createUserId;
    private String topicName;
    private String topicDescribe;
    private String topicHeadImage;
    private String topicPartnerName;
    private long createDate;
    private int thingCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescribe() {
        return topicDescribe;
    }

    public void setTopicDescribe(String topicDescribe) {
        this.topicDescribe = topicDescribe;
    }

    public String getTopicHeadImage() {
        return topicHeadImage;
    }

    public void setTopicHeadImage(String topicHeadImage) {
        this.topicHeadImage = topicHeadImage;
    }

    public String getTopicPartnerName() {
        return topicPartnerName;
    }

    public void setTopicPartnerName(String topicPartnerName) {
        this.topicPartnerName = topicPartnerName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getThingCount() {
        return thingCount;
    }

    public void setThingCount(int thingCount) {
        this.thingCount = thingCount;
    }
}
