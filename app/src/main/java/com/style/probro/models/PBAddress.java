package com.style.probro.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pb_address")
public class PBAddress {
    @PrimaryKey
    private String id;
    private String name;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String emailID;

    public PBAddress() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public String getAddressLine1() {
        if(addressLine1 == null) {
            return "";
        }
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        if(addressLine2 == null) {
            return "";
        }
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmailID() {
        if(emailID == null) {
            return "";
        }
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
