package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PBOrder implements Parcelable {
    private List<MyCartItem> cartItems;
    private int totalPrice;
    private PBAddress pbAddress;
    private PBTransaction pbTransaction;
    private PBShippingInfo shippingInfo;

    public PBOrder() {
    }

    protected PBOrder(Parcel in) {
        cartItems = in.createTypedArrayList(MyCartItem.CREATOR);
        totalPrice = in.readInt();
        pbAddress = in.readParcelable(PBAddress.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(cartItems);
        dest.writeInt(totalPrice);
        dest.writeParcelable(pbAddress, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PBOrder> CREATOR = new Creator<PBOrder>() {
        @Override
        public PBOrder createFromParcel(Parcel in) {
            return new PBOrder(in);
        }

        @Override
        public PBOrder[] newArray(int size) {
            return new PBOrder[size];
        }
    };

    public List<MyCartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<MyCartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PBAddress getPbAddress() {
        return pbAddress;
    }

    public void setPbAddress(PBAddress pbAddress) {
        this.pbAddress = pbAddress;
    }

    public PBTransaction getPbTransaction() {
        return pbTransaction;
    }

    public void setPbTransaction(PBTransaction pbTransaction) {
        this.pbTransaction = pbTransaction;
    }

    public PBShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(PBShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
