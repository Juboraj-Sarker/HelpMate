package com.juborajsarker.helpmate.model;

public class CommentModel {


    String postID;
    String userID;
    String userImageURL;
    String userName;
    String commentText;
    String commentDate;
    String commentTime;
    String commentID;

    public CommentModel() {

    }

    public CommentModel(String postID, String userID, String userImageURL, String userName, String commentText,
                        String commentDate, String commentTime, String commentID) {

        this.postID = postID;
        this.userID = userID;
        this.userImageURL = userImageURL;
        this.userName = userName;
        this.commentText = commentText;
        this.commentDate = commentDate;
        this.commentTime = commentTime;
        this.commentID = commentID;

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

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }
}
