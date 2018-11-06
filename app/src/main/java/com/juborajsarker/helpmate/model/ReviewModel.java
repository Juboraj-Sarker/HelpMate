package com.juborajsarker.helpmate.model;

public class ReviewModel {

    String reviewId;
    String reviewRete;
    String reviewText;
    String reviewGivenById;
    String reviewGivenByName;
    String reviewGivenToName;
    String reviewGivenToId;

    public ReviewModel() {
    }

    public ReviewModel(String reviewId, String reviewRete, String reviewText,
                       String reviewGivenById, String reviewGivenByName, String reviewGivenToName, String reviewGivenToId) {
        this.reviewId = reviewId;
        this.reviewRete = reviewRete;
        this.reviewText = reviewText;
        this.reviewGivenById = reviewGivenById;
        this.reviewGivenByName = reviewGivenByName;
        this.reviewGivenToName = reviewGivenToName;
        this.reviewGivenToId = reviewGivenToId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewRete() {
        return reviewRete;
    }

    public void setReviewRete(String reviewRete) {
        this.reviewRete = reviewRete;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewGivenById() {
        return reviewGivenById;
    }

    public void setReviewGivenById(String reviewGivenById) {
        this.reviewGivenById = reviewGivenById;
    }

    public String getReviewGivenByName() {
        return reviewGivenByName;
    }

    public void setReviewGivenByName(String reviewGivenByName) {
        this.reviewGivenByName = reviewGivenByName;
    }

    public String getReviewGivenToName() {
        return reviewGivenToName;
    }

    public void setReviewGivenToName(String reviewGivenToName) {
        this.reviewGivenToName = reviewGivenToName;
    }

    public String getReviewGivenToId() {
        return reviewGivenToId;
    }

    public void setReviewGivenToId(String reviewGivenToId) {
        this.reviewGivenToId = reviewGivenToId;
    }
}
