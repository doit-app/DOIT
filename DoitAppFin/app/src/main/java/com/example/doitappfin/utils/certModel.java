package com.example.doitappfin.utils;

public class certModel {


    String title,price,desc,image,addetails,area,latlong,search;


    public certModel() {
    }

    public certModel(String title, String price, String desc, String image, String addetails,String area,String latlong,String search) {
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.image = image;
        this.addetails = addetails;
        this.area=area;
        this.latlong=latlong;
        this.search=search;
    }

    public String getArea() {
        return area;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
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
