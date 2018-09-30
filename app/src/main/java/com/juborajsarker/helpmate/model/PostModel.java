package com.juborajsarker.helpmate.model;

public class PostModel {

    String postID;
    String userID;
    String postType;
    String userName;
    String postTime;
    String postDate;
    String postText;
    String postImageURL;
    int noOfLike;
    boolean hasImage;
    String likeUid;


    public PostModel() {


    }


    public PostModel(String postID, String userID, String postType, String userName, String postTime,
                     String postDate, String postText, String postImageURL, int noOfLike, boolean hasImage, String likeUid) {

        this.postID = postID;
        this.userID = userID;
        this.postType = postType;
        this.userName = userName;
        this.postTime = postTime;
        this.postDate = postDate;
        this.postText = postText;
        this.postImageURL = postImageURL;
        this.noOfLike = noOfLike;
        this.hasImage = hasImage;
        this.likeUid = likeUid;

    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImageURL() {
        return postImageURL;
    }

    public void setPostImageURL(String postImageURL) {
        this.postImageURL = postImageURL;
    }

    public int getNoOfLike() {
        return noOfLike;
    }

    public void setNoOfLike(int noOfLike) {
        this.noOfLike = noOfLike;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getLikeUid() {
        return likeUid;
    }

    public void setLikeUid(String likeUid) {
        this.likeUid = likeUid;
    }
}
