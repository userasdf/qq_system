package com.itheima.entity;

public class Relation {
    private String temp_username;
    private String target_username;
    private String val_msg;

    public String getVal_msg() {
        return val_msg;
    }

    public void setVal_msg(String val_msg) {
        this.val_msg = val_msg;
    }

    public String getTemp_username() {
        return temp_username;
    }

    public void setTemp_username(String temp_username) {
        this.temp_username = temp_username;
    }

    public String getTarget_username() {
        return target_username;
    }

    public void setTarget_username(String target_username) {
        this.target_username = target_username;
    }

    public Relation(String temp_username, String target_username) {
        this.temp_username = temp_username;
        this.target_username = target_username;
    }

    public Relation(String temp_username, String target_username, String val_msg) {
        this.temp_username = temp_username;
        this.target_username = target_username;
        this.val_msg = val_msg;
    }
}
