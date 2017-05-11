package com.example.chenfu.Ex06_03;


import java.io.Serializable;

public class DataList implements Serializable {
    String title;
    int color;

    public DataList(String title, int color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
