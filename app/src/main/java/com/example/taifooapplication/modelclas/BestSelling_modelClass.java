package com.example.taifooapplication.modelclas;

public class BestSelling_modelClass {

    int image;
    String productName,productPrice,unit;

    public BestSelling_modelClass(int image, String productName, String productPrice, String unit) {
        this.image = image;
        this.productName = productName;
        this.productPrice = productPrice;
        this.unit = unit;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
