package com.albert.practice.secondedition.data.model;

import java.io.Serializable;

public class Member implements Serializable {

    private String name;
    private String reword;
    private int picResourceId;

    public Member(String name, String reword, int picResourceId) {
        this.setName(name);
        this.setReword(reword);
        this.setPicResourceId(picResourceId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReword() {
        return reword;
    }

    public void setReword(String reword) {
        this.reword = reword;
    }

    public int getPicResourceId() {
        return picResourceId;
    }

    public void setPicResourceId(int picResourceId) {
        this.picResourceId = picResourceId;
    }
}
