package com.example.iconnect.Models;

public class ModelUser {

    String name, email, image, uid, school, country;

    public ModelUser(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ModelUser(String name, String email, String image, String school, String country) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.uid = uid;
        this.school = school;
        this.country = country;
    }

    public String getschool() {
        return school;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSchoolName(String school) {
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
