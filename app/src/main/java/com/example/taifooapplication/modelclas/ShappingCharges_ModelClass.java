package com.example.taifooapplication.modelclas;

public class ShappingCharges_ModelClass {

    String shappingId,price,deliveryPrice,ShappingName;

    public ShappingCharges_ModelClass(String shappingId, String price, String deliveryPrice, String shappingName) {

        this.shappingId = shappingId;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.ShappingName = shappingName;
    }

    public String getShappingId() {
        return shappingId;
    }

    public void setShappingId(String shappingId) {
        this.shappingId = shappingId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getShappingName() {
        return ShappingName;
    }

    public void setShappingName(String shappingName) {
        ShappingName = shappingName;
    }

    @Override
    public String toString() {
        return "ShappingCharges_ModelClass{" +
                "shappingId='" + shappingId + '\'' +
                ", price='" + price + '\'' +
                ", deliveryPrice='" + deliveryPrice + '\'' +
                ", ShappingName='" + ShappingName + '\'' +
                '}';
    }
}
