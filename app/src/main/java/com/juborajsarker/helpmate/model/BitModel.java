package com.juborajsarker.helpmate.model;

public class BitModel {

    String postID;
    String bitID;
    String uid;
    int bitPrice;
    String description;
    String bitDate;
    String bitTime;
    String estimatedTime;
    String expectedStartDate;


    public BitModel() {

    }


    public BitModel(String postID, String bitID, String uid, int bitPrice, String description, String bitDate,
                    String bitTime, String estimatedTime, String expectedStartDate) {

        this.postID = postID;
        this.bitID = bitID;
        this.uid = uid;
        this.bitPrice = bitPrice;
        this.description = description;
        this.bitDate = bitDate;
        this.bitTime = bitTime;
        this.estimatedTime = estimatedTime;

        this.expectedStartDate = expectedStartDate;

    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getBitID() {
        return bitID;
    }

    public void setBitID(String bitID) {
        this.bitID = bitID;
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
}
