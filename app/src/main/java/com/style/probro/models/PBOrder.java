package com.style.probro.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PBOrder implements Parcelable {
    private String orderID;
    private List<MyCartItem> cartItems;
    private int totalPrice;
    private PBAddress pbAddress;
    private PBTransaction pbTransaction;
    private PBShippingInfo shippingInfo;
    private String orderType;
    private String userName;
    private String userEmail;

    public PBOrder() {
    }

    protected PBOrder(Parcel in) {
        orderID = in.readString();
        cartItems = in.createTypedArrayList(MyCartItem.CREATOR);
        totalPrice = in.readInt();
        pbTransaction = in.readParcelable(PBTransaction.class.getClassLoader());
        shippingInfo = in.readParcelable(PBShippingInfo.class.getClassLoader());
        orderType = in.readString();
        userName = in.readString();
        userEmail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderID);
        dest.writeTypedList(cartItems);
        dest.writeInt(totalPrice);
        dest.writeParcelable(pbTransaction, flags);
        dest.writeParcelable(shippingInfo, flags);
        dest.writeString(orderType);
        dest.writeString(userName);
        dest.writeString(userEmail);
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
