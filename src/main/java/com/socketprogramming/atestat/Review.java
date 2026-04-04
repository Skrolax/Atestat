package com.socketprogramming.atestat;

public class Review {

    public String getBodyText() {
        return bodyText;
    }
    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }
    public int getClientID() {
        return clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    public int getCompanyID() {
        return companyID;
    }
    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    private String bodyText;
    // TODO private Integer rating;
    private int clientID;
    private int companyID;



    Review(String bodyTexT, int clientID, int companyID){
        this.bodyText = bodyTexT;
        this.clientID = clientID;
        this.companyID = companyID;
    }

}

// companyID = ID-ul firmei care a primit review-ul respectiv
// clienID = ID-ul clientului care a trimis review-ul
