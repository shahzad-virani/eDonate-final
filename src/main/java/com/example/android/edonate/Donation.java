package com.example.android.edonate;

public class Donation {
    private String name;
    private String quantity;
    private String details;
    private String type;
    private String photoUrl;
    private String donorID;

    public Donation(){
        //needed
    }
    public Donation(String name, String quantity, String details, String type, String photoUrl, String donorID) {
        this.name = name;
        this.quantity = quantity;
        this.details = details;
        this.type = type;
        this.photoUrl = photoUrl;
        this.donorID = donorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDonorID(){return donorID;}

    public void setDonorID(){ this.donorID = donorID; }
}
