package com.style.probro.order;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.style.probro.R;
import com.style.probro.app_utils.AppConstants;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.models.PBAddress;
import com.style.probro.models.PBOrder;

public class OrderSummaryActivity extends AppCompatActivity {

    private PBOrder mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        AppUtils.hideActionBar(getSupportActionBar());

        if(getIntent().hasExtra(AppConstants.PB_ORDER_INTENT_KEY)) {
            mOrder = getIntent().getParcelableExtra(AppConstants.PB_ORDER_INTENT_KEY);
        }

        if(mOrder == null) return;



    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    public void onClickResetAddress(View view) {

    }

    public void onClickOrderNow(View view) {
    }
}