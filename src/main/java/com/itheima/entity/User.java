package com.itheima.entity;

public class User {

    private String username;
    private String password;
    private String school;
    private String info;
    private int age = 0;
    private String address;
    private String val_msg;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVal_msg() {
        return val_msg;
    }

    public void setVal_msg(String val_msg) {
        this.val_msg = val_msg;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", school='" + school + '\'' +
                ", info='" + info + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", val_msg='" + val_msg + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
