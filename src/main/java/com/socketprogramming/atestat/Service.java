package com.socketprogramming.atestat;

public class Service {



    private int serviceID;
    private String service;

    public int getServiceID() {
        return serviceID;
    }
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }

    public Service(int serviceID, String service) {
        this.serviceID = serviceID;
        this.service = service;
    }

    @Override
    public String toString(){
        return service;
    }



}
