package com.example.taifooapplication.modelclas;

import java.util.ArrayList;

public class BestSelling_modelClass {

    String product_id,product_img,product_name,product_weight,product_grossweight,
            plate,regular_price,sale_price,quantity,description,var_id, var_price, var_name;

    ArrayList<VariationDetails> variation;

    public BestSelling_modelClass(String product_id, String product_img, String product_name,
                                  String product_weight, String product_grossweight, String plate,
                                  String regular_price, String sale_price, String quantity,String description,
                                  ArrayList<VariationDetails> variation,String var_id, String var_price, String var_name) {

        this.product_id = product_id;
        this.product_img = product_img;
        this.product_name = product_name;
        this.product_weight = product_weight;
        this.product_grossweight = product_grossweight;
        this.plate = plate;
        this.regular_price = regular_price;
        this.sale_price = sale_price;
        this.quantity = quantity;
        this.description = description;
        this.variation = variation;
        this.var_id = var_id;
        this.var_price = var_price;
        this.var_name = var_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_grossweight() {
        return product_grossweight;
    }

    public void setProduct_grossweight(String product_grossweight) {
        this.product_grossweight = product_grossweight;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<VariationDetails> getVariation() {
        return variation;
    }

    public void setVariation(ArrayList<VariationDetails> variation) {
        this.variation = variation;
    }

    public String getVar_id() {
        return var_id;
    }

    public void setVar_id(String var_id) {
        this.var_id = var_id;
    }

    public String getVar_price() {
        return var_price;
    }

    public void setVar_price(String var_price) {
        this.var_price = var_price;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }
}
