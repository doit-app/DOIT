package com.example.doitappfin.utils;

public class RecycleViewModel {

    private String adddetail;
    private String desc;
    private String image;
    private String price;
    private String title;

    public RecycleViewModel(String adddetail, String desc, String image, String price, String title) {
        this.adddetail = adddetail;
        this.desc = desc;
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public RecycleViewModel() {
    }

    public String getAdddetail() {
        return adddetail;
    }

    public void setAdddetail(String adddetail) {
        this.adddetail = adddetail;
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

    public void setImage(String pic) {
        this.image = pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
