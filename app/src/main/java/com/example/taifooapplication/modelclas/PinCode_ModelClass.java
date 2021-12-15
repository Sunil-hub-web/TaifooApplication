package com.example.taifooapplication.modelclas;

public class PinCode_ModelClass {

    String pincode;
    String pin_id;


    public PinCode_ModelClass(String pincode, String pin_id) {
        this.pincode = pincode;
        this.pin_id = pin_id;
    }


    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPin_id() {
        return pin_id;
    }

    public void setPin_id(String pin_id) {
        this.pin_id = pin_id;
    }

    @Override
    public String toString() {
        return "PinCode_ModelClass{" +
                "pincode='" + pincode + '\'' +
                ", pin_id='" + pin_id + '\'' +
                '}';
    }
}
