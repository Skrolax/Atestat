package com.socketprogramming.atestat;

import javafx.scene.image.Image;

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
        return email;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public Image getPhotoImage() {
        return photoImage;
    }
    public void setPhotoImage(Image photoImage) {
        this.photoImage = photoImage;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }


    private int ID;
    //TODO gasit metoda pentru a avea ID unic
    private String name;
    private String password;
    private int phoneNumber;
    private final String email;
    private Image photoImage;
    private ArrayList<Review> reviews;
    //TODO add join date

    User(String name, String password, String email, int phoneNumber){
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photoImage = Photo.DEFAULT_IMAGE;
    }



}

//photoImage-ul ăsta va trebui să aibă o poză by default;
