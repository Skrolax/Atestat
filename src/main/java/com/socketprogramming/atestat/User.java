package com.socketprogramming.atestat;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {

    public int getID() {
        return ID;
    }
    public void setID(int ID){this.ID = ID;}
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


    private int ID;
    private String name;
    private String password;
    private String email;
    private LocalDate joinDate;
    private byte[] photo;
    private ArrayList<Review> reviews;

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.photo = PhotoManager.DEFAULT_IMAGE;
    }



}

