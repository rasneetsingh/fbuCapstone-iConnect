package com.example.iconnect.Models;

public class ModelUser {

    String name, email, image, uid, school, country, major, work;

    public ModelUser(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ModelUser(String name, String email, String image, String school, String country, String major, String work) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.uid = uid;
        this.school = school;
        this.country = country;
        this.major = major;
        this.work = work;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getschool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
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

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}