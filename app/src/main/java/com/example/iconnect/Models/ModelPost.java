package com.example.iconnect.Models;

public class ModelPost {

    String pId,pTitle, pdDescr, pLikes, pImage, pTime, uid, pEmail, uDp, pName;

    public ModelPost(){

    }

    public ModelPost(String pId, String pTitle, String pdDescr, String pLikes, String pImage, String pTime, String uid, String pEmail, String uDp, String pName) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pdDescr = pdDescr;
        this.pLikes = pLikes;
        this.pImage = pImage;
        this.pTime = pTime;
        this.uid = uid;
        this.pEmail = pEmail;
        this.uDp = uDp;
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpdDescr() {
        return pdDescr;
    }

    public void setpdDescr(String pdDescr) {
        this.pdDescr = pdDescr;
    }

    public String getpLikes() {
        return pLikes;
    }

    public void setpLikes(String pLikes) {
        this.pLikes = pLikes;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getuid() {
        return uid;
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}