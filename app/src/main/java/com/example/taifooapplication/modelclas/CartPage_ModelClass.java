package com.example.taifooapplication.modelclas;

public class CartPage_ModelClass {

    int image;
    String productName,unit,productPrice;

    public CartPage_ModelClass(int image, String productName, String unit, String productPrice) {
        this.image = image;
        this.productName = productName;
        this.unit = unit;
        this.productPrice = productPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
