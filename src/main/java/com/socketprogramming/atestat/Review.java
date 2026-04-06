package com.socketprogramming.atestat;

import java.time.LocalDateTime;

public class Review {



    private int reviewID;
    private int userID;
    private int companyID;
    private String reviewText;
    private float rating;
    private LocalDateTime reviewDateTime;
    private boolean isAnonymous;

    public int getReviewID() {
        return reviewID;
    }
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getCompanyID() {
        return companyID;
    }
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    public LocalDateTime getReviewDateTime() {
        return reviewDateTime;
    }
    public void setReviewDateTime(LocalDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }
    public boolean isAnonymous() {
        return isAnonymous;
    }
    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }


    Review(int userID, int companyID, String reviewText, float rating, boolean isAnonymous){
        this.userID = userID;
        this.companyID = companyID;
        this.reviewText = reviewText;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString(){
        return reviewID + " " + userID + " " + companyID + " " + reviewText + " " + rating + " " + isAnonymous;
    }

}