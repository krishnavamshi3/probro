package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "pb_image")
public class PBImage implements Parcelable {
    private String color;
    private String url;

    public PBImage() {
    }

    protected PBImage(Parcel in) {
        color = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBImage> CREATOR = new Creator<PBImage>() {
        @Override
        public PBImage createFromParcel(Parcel in) {
            return new PBImage(in);
        }

        @Override
        public PBImage[] newArray(int size) {
            return new PBImage[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @NotNull
    @Override
    public String toString() {
        String separator = "$";
        String stringBuilder = this.color +
                separator +
                this.url;
        return stringBuilder;

    }
}
