package com.style.probro.returnandrefund;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.style.probro.R;
import com.style.probro.app_utils.AppUtils;

public class ReturnPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_and_refund);
        AppUtils.hideActionBar(getSupportActionBar());
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

}