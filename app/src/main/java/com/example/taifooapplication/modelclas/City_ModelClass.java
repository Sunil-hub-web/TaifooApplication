package com.example.taifooapplication.modelclas;

public class City_ModelClass {

   String city;
   String City_id;

    public City_ModelClass(String city, String city_id) {
        this.city = city;
        City_id = city_id;
    }

    public String getCity_id() {
        return City_id;
    }

    public void setCity_id(String city_id) {
        City_id = city_id;
    }

    public City_ModelClass(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
