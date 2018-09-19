package com.juborajsarker.helpmate.model;

public class BitModel {

    String postID;
    String uid;
    int bitPrice;
    String bitDate;
    String bitTime;
    String bitID;

    public BitModel() {

    }

    public BitModel(String postID, String uid, int bitPrice, String bitDate, String bitTime, String bitID) {
        this.postID = postID;
        this.uid = uid;
        this.bitPrice = bitPrice;
        this.bitDate = bitDate;
        this.bitTime = bitTime;
        this.bitID = bitID;

    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

    public String getBitID() {
        return bitID;
    }

    public void setBitID(String bitID) {
        this.bitID = bitID;
    }
}
