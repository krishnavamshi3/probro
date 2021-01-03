package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.style.probro.RoomDataConverters;

@Entity(tableName = "my_cart_item")
public class MyCartItem implements Parcelable {
    @PrimaryKey
    @NonNull
    String cartItemID;

    @TypeConverters(RoomDataConverters.class)
    private PBArticle pbArticle;
    private int quantity;
    private String selectedSize;
    private String selectedColor;

    public MyCartItem() {
    }

    protected MyCartItem(Parcel in) {
        cartItemID = in.readString();
        pbArticle = in.readParcelable(PBArticle.class.getClassLoader());
        quantity = in.readInt();
        selectedSize = in.readString();
        selectedColor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cartItemID);
        dest.writeParcelable(pbArticle, flags);
        dest.writeInt(quantity);
        dest.writeString(selectedSize);
        dest.writeString(selectedColor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyCartItem> CREATOR = new Creator<MyCartItem>() {
        @Override
        public MyCartItem createFromParcel(Parcel in) {
            return new MyCartItem(in);
        }

        @Override
        public MyCartItem[] newArray(int size) {
            return new MyCartItem[size];
        }
    };

    public PBArticle getPbArticle() {
        return pbArticle;
    }

    public void setPbArticle(PBArticle pbArticle) {
        this.pbArticle = pbArticle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(String cartItemID) {
        this.cartItemID = cartItemID;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }
}
