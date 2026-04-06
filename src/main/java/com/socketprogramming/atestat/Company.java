package com.socketprogramming.atestat;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;

public class Company {

    private int companyID;
    private String name;
    private String businessEmail;
    private String customerServiceEmail;
    private String businessPhoneNumber;
    private String customerServicePhoneNumber;
    private String address;
    private String websiteLink;
    private String servicesString;
    private LocalDate companyFoundedDate;
    private byte[] photo;


    public int getCompanyID() {
        return companyID;
    }
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBusinessEmail() {
        return businessEmail;
    }
    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }
    public String getCustomerServiceEmail() {
        return customerServiceEmail;
    }
    public void setCustomerServiceEmail(String customerServiceEmail) {
        this.customerServiceEmail = customerServiceEmail;
    }
    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }
    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }
    public String getCustomerServicePhoneNumber() {
        return customerServicePhoneNumber;
    }
    public void setCustomerServicePhoneNumber(String customerServicePhoneNumber) {
        this.customerServicePhoneNumber = customerServicePhoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getWebsiteLink() {
        return websiteLink;
    }
    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }
    public String getServicesString() {
        return servicesString;
    }
    public void setServicesString(String servicesString) {
        this.servicesString = servicesString;
    }
    public LocalDate getCompanyFoundedDate() {
        return companyFoundedDate;
    }
    public void setCompanyFoundedDate(LocalDate companyFoundedDate) {
        this.companyFoundedDate = companyFoundedDate;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


    public Company(String name, String servicesString, String businessEmail, String customerServiceEmail, String businessPhoneNumber, String customerServicePhoneNumber, String address, String websiteLink, LocalDate companyFoundedDate) {
        this.name = name;
        this.servicesString = servicesString;
        this.businessEmail = businessEmail;
        this.customerServiceEmail = customerServiceEmail;
        this.businessPhoneNumber = businessPhoneNumber;
        this.customerServicePhoneNumber = customerServicePhoneNumber;
        this.address = address;
        this.websiteLink = websiteLink;
        this.companyFoundedDate = companyFoundedDate;
    }

    @Override
    public String toString(){
        return name + " " + servicesString + " " + businessEmail + " " + customerServiceEmail + " " + businessPhoneNumber + " " + customerServicePhoneNumber + " " + address + " " + websiteLink + " " + companyFoundedDate + " " + photo;
    }

}
