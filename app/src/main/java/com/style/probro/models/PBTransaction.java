package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity
public class PBTransaction implements Parcelable {
    private String transactionID;
    private int transactionAmount;

    public PBTransaction() {
        transactionID = "";
        transactionAmount = -1;
    }

    protected PBTransaction(Parcel in) {
        transactionID = in.readString();
        transactionAmount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionID);
        dest.writeInt(transactionAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBTransaction> CREATOR = new Creator<PBTransaction>() {
        @Override
        public PBTransaction createFromParcel(Parcel in) {
            return new PBTransaction(in);
        }

        @Override
        public PBTransaction[] newArray(int size) {
            return new PBTransaction[size];
        }
    };

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
