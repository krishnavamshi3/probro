package com.style.probro.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.style.probro.AppDatabase;
import com.style.probro.R;
import com.style.probro.app_utils.AppConstants;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.cart.MyCartActivity;
import com.style.probro.firebase_api.FirebaseConstants;
import com.style.probro.firebase_api.OnCrudFirebaseCallback;
import com.style.probro.firebase_api.OrderAPI;
import com.style.probro.models.FailureObject;
import com.style.probro.models.MyCartItem;
import com.style.probro.models.PBAddress;
import com.style.probro.models.PBOrder;
import com.style.probro.models.PBShippingInfo;
import com.style.probro.room.MyCartDao;

import java.lang.ref.WeakReference;
import java.util.Date;

public class OrderSummaryActivity extends AppCompatActivity {
    private static final int SHIPPING_FEE = 100;
    RecyclerView mRecyclerView;
    MyOrderSummaryArticleAdapter adapter;
    private PBOrder mOrder;

    private EditText addressNameInput, addressLane1Input, addressLane2Input, addressPincode,
            addressPhone, addressEmail;
    private TextView totalActualText, totalPayPriceText, shippingFeeText, finalPriceText;

    private int finalPrice = 0;
    private OrderAPI mOrderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        AppUtils.hideActionBar(getSupportActionBar());

        if (getIntent().hasExtra(AppConstants.PB_ORDER_INTENT_KEY)) {
            mOrder = getIntent().getParcelableExtra(AppConstants.PB_ORDER_INTENT_KEY);
        }

        if (mOrder == null) return;

        mRecyclerView = findViewById(R.id.my_order_cart_rv);
        setupRecyclerView(mRecyclerView);
        setUpPriceDetails();

        addressNameInput = findViewById(R.id.payment_address_show_delivery_name_tv);
        addressLane1Input = findViewById(R.id.payment_address_show_delivery_line1_tv);
        addressLane2Input = findViewById(R.id.payment_address_show_delivery_line2_tv);
        addressPincode = findViewById(R.id.payment_address_show_delivery_pincode_tv);
        addressPhone = findViewById(R.id.payment_address_show_delivery_phone_tv);
        addressEmail = findViewById(R.id.payment_address_show_delivery_email_tv);

        mOrderAPI = new OrderAPI();

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

        finalPrice = totalPayPrice + SHIPPING_FEE;

        totalActualText.setText(String.valueOf(totalActualPrice));
        totalPayPriceText.setText(String.valueOf(totalPayPrice));
        shippingFeeText.setText(String.valueOf(SHIPPING_FEE));
        finalPriceText.setText(String.valueOf(finalPrice));
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

    public void onClickResetAddress(View view) {

    }

    public void onClickOrderNow(View view) {

        CheckBox checkBox = findViewById(R.id.cod_checkbox_payment);
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please select mode of payment!", Toast.LENGTH_SHORT).show();
            return;
        }
        mOrder.setOrderType(AppConstants.ORDER_TYPE_COD);

        finalizeAddress();
        if (!isAddressValid()) {
            Toast.makeText(this, "Enter valid address", Toast.LENGTH_SHORT).show();
            return;
        }

        mOrder.setTotalPrice(finalPrice);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null || account.getEmail() == null) {
            Toast.makeText(this, "Cant place this order!", Toast.LENGTH_SHORT).show();
            return;
        }
        mOrder.setUserName(account.getEmail()
                .replace("@", "")
                .replace(".", "")
                .replace("gmail", "")
                .replace("com","")
        );
        mOrder.setUserEmail(account.getEmail());
        mOrder.setShippingFee(SHIPPING_FEE);
        mOrder.setOrderID(mOrder.getPbAddress().getName() + String.valueOf((new Date()).getTime()));
        mOrder.setOrderDate(new Date());
        PBShippingInfo shippingInfo = new PBShippingInfo();
        shippingInfo.setShipStatus("OrderPlaced.");
        shippingInfo.setShipNote("Your Order is Placed. We are about to accept your order. Please recieve call from our authorized agent for confirming the order.");
        ProgressDialog dialog = ProgressDialog.show(this, "","Please wait...", true, false);
        mOrderAPI.writeNewOrder(mOrder, new OnCrudFirebaseCallback<PBOrder>() {
            @Override
            public void success(PBOrder userFromFirebase) {
                dialog.dismiss();
                Toast.makeText(OrderSummaryActivity.this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show();
                new DeleteAsyncTask(OrderSummaryActivity.this).execute();
                finish();
            }

            @Override
            public void Failure(FailureObject object) {
                dialog.dismiss();
                Toast.makeText(OrderSummaryActivity.this, " Order Failed! " + object.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isAddressValid() {
        if (mOrder.getPbAddress().getName().isEmpty()) {
            return false;
        } else if (mOrder.getPbAddress().getAddressLine1().isEmpty()) {
            return false;
        } else if (mOrder.getPbAddress().getAddressLine2().isEmpty()) {
            return false;
        } else if (mOrder.getPbAddress().getZipCode().isEmpty()) {
            return false;
        } else if (mOrder.getPbAddress().getPhoneNumber().isEmpty()) {
            return false;
        } else if (mOrder.getPbAddress().getEmailID().isEmpty()) {
            return false;
        }


        return true;
    }

    private void finalizeAddress() {
        String name = addressNameInput.getText().toString();
        String aL1 = addressLane1Input.getText().toString();
        String al2 = addressLane2Input.getText().toString();
        String zipcode = addressPincode.getText().toString();
        String phone = addressPhone.getText().toString();
        String email = addressEmail.getText().toString();
        PBAddress pbAddress = new PBAddress();
        pbAddress.setId(name + aL1 + al2 + zipcode + phone);
        pbAddress.setName(name);
        pbAddress.setAddressLine1(aL1);
        pbAddress.setAddressLine2(al2);
        pbAddress.setPhoneNumber(phone);
        pbAddress.setZipCode(zipcode);
        pbAddress.setEmailID(email);
        mOrder.setPbAddress(pbAddress);
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;


        public DeleteAsyncTask(Activity activity) {
            weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MyCartDao agentDao = AppDatabase.getDb().myCartDao();
            agentDao.deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}