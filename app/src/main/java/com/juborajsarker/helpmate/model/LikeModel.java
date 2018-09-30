package com.juborajsarker.helpmate.model;

public class LikeModel {

    String likeID;
    String uid;
    String postID;
    String postText;

    public LikeModel() {

    }

    public LikeModel(String likeID, String uid, String postID, String postText) {
        this.likeID = likeID;
        this.uid = uid;
        this.postID = postID;
        this.postText = postText;
    }

    public String getLikeID() {
        return likeID;
    }

    public void setLikeID(String likeID) {
        this.likeID = likeID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }
}
