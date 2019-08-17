package com.example.doitappfin.utils;

public class certModel {


    String title,price,desc,image,addetails;


    public certModel() {
    }

    public certModel(String title, String price, String desc, String image, String addetails) {
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.addetails = addetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddetails() {
        return addetails;
    }

    public void setAddetails(String addetails) {
        this.addetails = addetails;
    }
}
