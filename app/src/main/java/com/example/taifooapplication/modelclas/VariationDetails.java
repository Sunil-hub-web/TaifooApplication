package com.example.taifooapplication.modelclas;

public class VariationDetails {

    String price_id,price,varations;

    public VariationDetails(String price_id, String price, String varations) {
        this.price_id = price_id;
        this.price = price;
        this.varations = varations;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVarations() {
        return varations;
    }

    public void setVarations(String varations) {
        this.varations = varations;
    }
}
