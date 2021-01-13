package com.isprid.smartsafari.model;

import java.io.Serializable;

public class User implements Serializable {
    String uid;
    String fname;
    String email;
    String phone;
    String password;
    String role;

    public User() {
    }


    public User(String uid, String fname, String email, String phone, String password, String role) {
        this.uid = uid;
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public User(String fname, String email, String phone, String password, String role) {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
}
