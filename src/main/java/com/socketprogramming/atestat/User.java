package com.socketprogramming.atestat;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {

    public int getID() {
        return userID;
    }
    public void setID(int ID){this.userID = ID;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photoImage) {
        this.photo = photoImage;
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }
    public LocalDate getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }


    private int userID;
    private String name;
    private String password;
    private String email;


    private LocalDate joinDate;
    private byte[] photo;
    private ArrayList<Review> reviews;

    User(int userID, String name, String password, String email, byte[] photo){
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.photo = photo;
    }



}

