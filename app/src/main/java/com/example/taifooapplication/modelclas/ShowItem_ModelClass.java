package com.example.taifooapplication.modelclas;

public class ShowItem_ModelClass {

    String Image,category_id,category_name;

    public ShowItem_ModelClass(String image, String category_id, String category_name) {
        Image = image;
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
