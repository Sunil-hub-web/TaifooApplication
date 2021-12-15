package com.example.taifooapplication.modelclas;

public class ViewAddressDetails_ModelClass {

    String addressId,cityId,userName,cityName,area,pincode,mobileNo,address,email;

    public ViewAddressDetails_ModelClass(String addressId, String cityId, String userName, String cityName,
                                         String area, String pincode, String mobileNo, String address, String email) {
        this.addressId = addressId;
        this.cityId = cityId;
        this.userName = userName;
        this.cityName = cityName;
        this.area = area;
        this.pincode = pincode;
        this.mobileNo = mobileNo;
        this.address = address;
        this.email = email;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ViewAddressDetails_ModelClass{" +
                "addressId='" + addressId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", userName='" + userName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", area='" + area + '\'' +
                ", pincode='" + pincode + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
