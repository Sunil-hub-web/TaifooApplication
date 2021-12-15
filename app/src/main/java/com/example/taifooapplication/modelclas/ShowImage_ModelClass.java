package com.example.taifooapplication.modelclas;

public class ShowImage_ModelClass {

    String image,banner_Id,title;

    public ShowImage_ModelClass(String image,String banner_Id,String title) {
        this.image = image;
        this.banner_Id = banner_Id;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
