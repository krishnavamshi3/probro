package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;


public class PBAddress implements Parcelable {
    private String id;
    private String name;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String zipCode;
    private String emailID;

    public PBAddress() {
        id = "";
        name = "";
        phoneNumber = "";
        addressLine1 = "";
        addressLine2 = "";
        zipCode = "";
        emailID = "";
    }

    protected PBAddress(Parcel in) {
        id = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        addressLine1 = in.readString();
        addressLine2 = in.readString();
        zipCode = in.readString();
        emailID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(addressLine1);
        dest.writeString(addressLine2);
        dest.writeString(zipCode);
        dest.writeString(emailID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBAddress> CREATOR = new Creator<PBAddress>() {
        @Override
        public PBAddress createFromParcel(Parcel in) {
            return new PBAddress(in);
        }

        @Override
        public PBAddress[] newArray(int size) {
            return new PBAddress[size];
        }
    };

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
