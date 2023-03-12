package com.example.taifooapplication.modelclas;

public class MyOrderDetails {

    String productName,price,unit,image,qty;

    public MyOrderDetails(String productName, String price, String unit, String image,String qty) {

        this.productName = productName;
        this.price = price;
        this.unit = unit;
        this.image = image;
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "MyOrderDetails{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", unit='" + unit + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
