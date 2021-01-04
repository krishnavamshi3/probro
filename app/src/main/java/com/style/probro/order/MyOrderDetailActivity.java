package com.style.probro.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.style.probro.R;
import com.style.probro.app_utils.AppConstants;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.models.MyCartItem;
import com.style.probro.models.PBOrder;

public class MyOrderDetailActivity extends AppCompatActivity {
    private static final int SHIPPING_FEE = 100;
    RecyclerView mRecyclerView;
    MyOrderSummaryArticleAdapter adapter;
    private PBOrder mOrder;

    private TextView addressNameInput, addressLane1Input, addressLane2Input, addressPincode,
            addressPhone, addressEmail;
    private TextView totalActualText, totalPayPriceText, shippingFeeText, finalPriceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);

        AppUtils.hideActionBar(getSupportActionBar());


        if (getIntent().hasExtra(AppConstants.PB_ORDER_INTENT_KEY)) {
            mOrder = getIntent().getParcelableExtra(AppConstants.PB_ORDER_INTENT_KEY);
        }

        if (mOrder == null) return;

        mRecyclerView = findViewById(R.id.my_order_cart_rv);
        setupRecyclerView(mRecyclerView);
        setUpPriceDetails();

        setUpPaymentMethod();
        setUpShippingInfo();

        addressNameInput = findViewById(R.id.payment_address_show_delivery_name_tv);
        addressLane1Input = findViewById(R.id.payment_address_show_delivery_line1_tv);
        addressLane2Input = findViewById(R.id.payment_address_show_delivery_line2_tv);
        addressPincode = findViewById(R.id.payment_address_show_delivery_pincode_tv);
        addressPhone = findViewById(R.id.payment_address_show_delivery_phone_tv);
        addressEmail = findViewById(R.id.payment_address_show_delivery_email_tv);

        if(mOrder.getPbAddress() != null) {

            addressNameInput.setText(mOrder.getPbAddress().getName());
            addressLane1Input.setText(mOrder.getPbAddress().getAddressLine1());
            addressLane2Input.setText(mOrder.getPbAddress().getAddressLine2());
            addressPincode.setText(mOrder.getPbAddress().getZipCode());
            addressPhone.setText(mOrder.getPbAddress().getPhoneNumber());
            addressEmail.setText(mOrder.getPbAddress().getEmailID());
        }

    }

    private void setUpShippingInfo() {
        TextView shippingStatus = findViewById(R.id.shipping_status);
        TextView shippingNote = findViewById(R.id.shipping_note);
        TextView shippingUrl = findViewById(R.id.shipping_track_url);
        shippingUrl.setMovementMethod(LinkMovementMethod.getInstance());

        shippingStatus.setText(String.format(getString(R.string.shipping_status_text), mOrder.getShippingInfo().getShipStatus()));
        shippingNote.setText(String.format(getString(R.string.shipping_note_text),mOrder.getShippingInfo().getShipNote()));
        shippingUrl.setText(mOrder.getShippingInfo().getShipDocumentUrl());
    }

    private void setUpPaymentMethod() {
        TextView textView = findViewById(R.id.payment_method_choosen);
        textView.setText(mOrder.getOrderType());
    }

    private void setUpPriceDetails() {
        totalActualText = findViewById(R.id.payment_item_actual_price_tv);
        totalPayPriceText = findViewById(R.id.payment_item_pay_price_tv);
        shippingFeeText = findViewById(R.id.payment_item_shipping_price_tv);
        finalPriceText = findViewById(R.id.payment_item_final_pay_price_tv);

        int totalPayPrice = 0;
        int totalActualPrice = 0;
        for (MyCartItem cartItem : mOrder.getCartItems()) {
            totalPayPrice += cartItem.getPbArticle().getPayprice() * cartItem.getQuantity();
            totalActualPrice += cartItem.getPbArticle().getMrpprice() * cartItem.getQuantity();
        }

        totalActualText.setText(String.valueOf(totalActualPrice));
        totalPayPriceText.setText(String.valueOf(totalPayPrice));
        shippingFeeText.setText(String.valueOf(SHIPPING_FEE));
        finalPriceText.setText(String.valueOf(mOrder.getTotalPrice()));
    }

    private void setupRecyclerView(RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        adapter = new MyOrderSummaryArticleAdapter(this, mOrder.getCartItems());
        rv.setAdapter(adapter);
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }
}