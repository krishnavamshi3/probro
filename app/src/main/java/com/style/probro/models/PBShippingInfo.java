package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity
public class PBShippingInfo implements Parcelable {

    private String shipStatus;
    private String shipDocumentUrl;
    private String shipNote;

    public PBShippingInfo() {
        shipStatus = "";
        shipDocumentUrl ="";
        shipNote = "";
    }

    protected PBShippingInfo(Parcel in) {
        shipStatus = in.readString();
        shipDocumentUrl = in.readString();
        shipNote = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shipStatus);
        dest.writeString(shipDocumentUrl);
        dest.writeString(shipNote);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBShippingInfo> CREATOR = new Creator<PBShippingInfo>() {
        @Override
        public PBShippingInfo createFromParcel(Parcel in) {
            return new PBShippingInfo(in);
        }

        @Override
        public PBShippingInfo[] newArray(int size) {
            return new PBShippingInfo[size];
        }
    };

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getShipDocumentUrl() {
        return shipDocumentUrl;
    }

    public void setShipDocumentUrl(String shipDocumentUrl) {
        this.shipDocumentUrl = shipDocumentUrl;
    }

    public String getShipNote() {
        return shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }
}
