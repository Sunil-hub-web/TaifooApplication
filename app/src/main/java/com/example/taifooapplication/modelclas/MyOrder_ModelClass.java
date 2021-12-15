package com.example.taifooapplication.modelclas;

import java.util.ArrayList;

public class MyOrder_ModelClass {

    String orderId,orderDate,orderStatus,productName,price,unit,image,addressName,state,city,pincode,
            address,phoneNumber,shipping_charge,subtotal,total;
    ArrayList<MyOrderDetails> MyorderDetails;

    public MyOrder_ModelClass(String orderId, String orderDate, String orderStatus,
                              ArrayList<MyOrderDetails> myorderDetails,String productName,
                              String price, String unit, String image,String addressName,String state,
                              String city,String pincode,String address,String phoneNumber,String shipping_charge,
                              String subtotal,String total) {

        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.MyorderDetails = myorderDetails;
        this.productName = productName;
        this.price = price;
        this.unit = unit;
        this.image = image;
        this.addressName = addressName;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.shipping_charge = shipping_charge;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ArrayList<MyOrderDetails> getMyorderDetails() {
        return MyorderDetails;
    }

    public void setMyorderDetails(ArrayList<MyOrderDetails> myorderDetails) {
        MyorderDetails = myorderDetails;
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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(String shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
