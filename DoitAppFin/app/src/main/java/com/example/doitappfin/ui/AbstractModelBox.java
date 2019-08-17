package com.example.doitappfin.ui;

import java.util.ArrayList;

public class AbstractModelBox {

    private String title;

    private String message;


    public AbstractModelBox(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public AbstractModelBox() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
