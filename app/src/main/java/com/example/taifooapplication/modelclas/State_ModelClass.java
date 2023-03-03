package com.example.taifooapplication.modelclas;

public class State_ModelClass {

    String state, state_id;

    public State_ModelClass(String state, String state_id) {
        this.state = state;
        this.state_id = state_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public State_ModelClass(String city) {
        this.state = city;
    }

    public String getCity() {
        return state;
    }

    public void setCity(String city) {
        this.state = city;
    }
}
