package com.example.iconnect.Models;

public class ModelUser {

    String name,  uid, school, country;

    public ModelUser(){

    }

    public ModelUser(String name, String uid, String email, String school, String country) {
        this.name = name;
        this.uid = uid;
        this.school = school;
        this.country = country;

    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}