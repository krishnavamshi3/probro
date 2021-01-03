package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pb_address")
public class PBAddress implements Parcelable {
    @PrimaryKey
    private String id;
    private String name;
    private String phoneNumberPrimary;
    private String phoneNumberSecondary;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String emailID;

    public PBAddress() {
    }

    protected PBAddress(Parcel in) {
        id = in.readString();
        name = in.readString();
        phoneNumberPrimary = in.readString();
        phoneNumberSecondary = in.readString();
        addressLine1 = in.readString();
        addressLine2 = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        zipCode = in.readString();
        emailID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phoneNumberPrimary);
        dest.writeString(phoneNumberSecondary);
        dest.writeString(addressLine1);
        dest.writeString(addressLine2);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(country);
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

    public String getPhoneNumberPrimary() {
        return phoneNumberPrimary;
    }

    public void setPhoneNumberPrimary(String phoneNumberPrimary) {
        this.phoneNumberPrimary = phoneNumberPrimary;
    }

    public String getPhoneNumberSecondary() {
        if(phoneNumberSecondary == null) {
            return "";
        }
        return phoneNumberSecondary;
    }

    public void setPhoneNumberSecondary(String phoneNumberSecondary) {
        this.phoneNumberSecondary = phoneNumberSecondary;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        if(country == null) {
            return "";
        }
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
