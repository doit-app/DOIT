package com.example.doitappfin.utils;

public class myCourseModel {

    String ammount,title,location,time,paymentid,other;

    public myCourseModel() {
    }

    public myCourseModel(String ammount, String title, String location, String time, String paymentid, String other) {
        this.ammount = ammount;
        this.title = title;
        this.location = location;
        this.time = time;
        this.paymentid = paymentid;
        this.other = other;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
