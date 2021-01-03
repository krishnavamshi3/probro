package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(tableName = "pb_article")
public class PBArticle implements Parcelable {
    private boolean availability;
    private String id;
    private List<String> color;
    private List<PBImage> images;
    private int mrpprice;
    private int payprice;
    private String name;
    private String thumbimageurl;
    private String type;
    private String videourl;
    private String description;
    private List<String> size;

    public PBArticle() {
    }

    protected PBArticle(Parcel in) {
        availability = in.readByte() != 0;
        id = in.readString();
        color = in.createStringArrayList();
        images = in.createTypedArrayList(PBImage.CREATOR);
        mrpprice = in.readInt();
        payprice = in.readInt();
        name = in.readString();
        thumbimageurl = in.readString();
        type = in.readString();
        videourl = in.readString();
        description = in.readString();
        size = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (availability ? 1 : 0));
        dest.writeString(id);
        dest.writeStringList(color);
        dest.writeTypedList(images);
        dest.writeInt(mrpprice);
        dest.writeInt(payprice);
        dest.writeString(name);
        dest.writeString(thumbimageurl);
        dest.writeString(type);
        dest.writeString(videourl);
        dest.writeString(description);
        dest.writeStringList(size);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBArticle> CREATOR = new Creator<PBArticle>() {
        @Override
        public PBArticle createFromParcel(Parcel in) {
            return new PBArticle(in);
        }

        @Override
        public PBArticle[] newArray(int size) {
            return new PBArticle[size];
        }
    };

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<PBImage> getImages() {
        if(this.images == null)return new ArrayList<>();
        return images;
    }

    public void setImages(List<PBImage> images) {
        this.images = images;
    }

    public int getMrpprice() {
        return mrpprice;
    }

    public void setMrpprice(int mrpprice) {
        this.mrpprice = mrpprice;
    }

    public int getPayprice() {
        return payprice;
    }

    public void setPayprice(int payprice) {
        this.payprice = payprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbimageurl() {
        return thumbimageurl;
    }

    public void setThumbimageurl(String thumbimageurl) {
        this.thumbimageurl = thumbimageurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

}
