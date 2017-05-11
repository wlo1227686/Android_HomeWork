package com.example.chenfu.ex05_01;

public class Spot {
    private int imageId;
    private int productsId;
    private String Name;
    private String Address;
    public Spot(int productsId,int imageId, String name, String address) {
        this.productsId=productsId;
        this.imageId = imageId;
        Name = name;
        Address = address;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
