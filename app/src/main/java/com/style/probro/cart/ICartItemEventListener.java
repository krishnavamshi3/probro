package com.style.probro.cart;

import com.style.probro.models.MyCartItem;

public interface ICartItemEventListener {

    void onRemoveCartItem(MyCartItem myCartItem);

    void onSaveCartItem(MyCartItem myCartItem);

    void onCartUpdate(MyCartItem myCartItem);
}
