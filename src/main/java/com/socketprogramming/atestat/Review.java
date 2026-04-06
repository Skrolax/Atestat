package com.socketprogramming.atestat;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Review {



    private int reviewID;
    private int userID;
    private String userName;
    private int companyID;

    private String companyName;
    private String reviewText;
    private float rating;
    private LocalDateTime reviewDateTime;
    private boolean isAnonymous;
    private byte[] userPhoto;

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
    public byte[] getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }




    Review(int reviewID, int userID, String userName, int companyID, String companyName, String reviewText, float rating, boolean isAnonymous, LocalDateTime reviewDateTime, byte[] photo){
        this.reviewID = reviewID;
        this.userID = userID;
        this.userName = userName;
        this.companyID = companyID;
        this.companyName = companyName;
        this.reviewText = reviewText;
        this.rating = rating;
        this.isAnonymous = isAnonymous;
        this.userPhoto = photo;
        this.reviewDateTime = reviewDateTime;
    }


}