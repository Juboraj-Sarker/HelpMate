package com.juborajsarker.helpmate.model;

public class BitModel {

    String bitId;
    String uid;
    int bitPrice;
    String description;
    String estimatedTime;
    String expectedStartDate;
    String bitDate;
    String bitTime;

    String postId;
    String postText;

    String expertUid;
    String expertName;



    public BitModel() {

    }

    public BitModel(String bitId, String uid, int bitPrice, String description, String estimatedTime,
                    String expectedStartDate, String bitDate, String bitTime, String postId, String postText,
                    String expertUid, String expertName) {

        this.bitId = bitId;
        this.uid = uid;
        this.bitPrice = bitPrice;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.expectedStartDate = expectedStartDate;
        this.bitDate = bitDate;
        this.bitTime = bitTime;
        this.postId = postId;
        this.postText = postText;
        this.expertUid = expertUid;
        this.expertName = expertName;

    }

    public String getBitId() {
        return bitId;
    }

    public void setBitId(String bitId) {
        this.bitId = bitId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getBitPrice() {
        return bitPrice;
    }

    public void setBitPrice(int bitPrice) {
        this.bitPrice = bitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public String getBitDate() {
        return bitDate;
    }

    public void setBitDate(String bitDate) {
        this.bitDate = bitDate;
    }

    public String getBitTime() {
        return bitTime;
    }

    public void setBitTime(String bitTime) {
        this.bitTime = bitTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getExpertUid() {
        return expertUid;
    }

    public void setExpertUid(String expertUid) {
        this.expertUid = expertUid;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }
}
