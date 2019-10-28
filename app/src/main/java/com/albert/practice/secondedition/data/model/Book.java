package com.albert.practice.secondedition.data.model;

import java.io.Serializable;

public class Book implements Serializable {

    private String name;
    private String price;
    private int picResourceId;

    public Book(String name, String price, int picResourceId) {
        this.setName(name);
        this.setPrice(price);
        this.setPicResourceId(picResourceId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPicResourceId() {
        return picResourceId;
    }

    public void setPicResourceId(int picResourceId) {
        this.picResourceId = picResourceId;
    }
}
