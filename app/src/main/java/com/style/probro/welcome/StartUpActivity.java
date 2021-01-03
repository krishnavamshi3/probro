package com.style.probro.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.R;
import com.style.probro.app_utils.AppSharedPreference;
import com.style.probro.app_utils.AppUtils;

public class StartUpActivity extends AppCompatActivity {
    private int delayedTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFirstTimeAppUsed = AppSharedPreference.getInstance(this.getApplicationContext()).getBoolean(AppSharedPreference.Key.FIRST_TIME_USE_APP_KEY, true);
        if(isFirstTimeAppUsed) {
            setContentView(R.layout.activity_what_we_are_single_page);
            delayedTime = 3000;
        } else {
            setContentView(R.layout.activity_startup);
            delayedTime = 2000;
        }
        AppUtils.hideActionBar(getSupportActionBar());
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(account == null && firebaseUser == null) {
            FirebaseAuth.getInstance().signInAnonymously();
        }
         new Handler().postDelayed(() -> {
             AppSharedPreference.getInstance().put(AppSharedPreference.Key.FIRST_TIME_USE_APP_KEY,  false);
             AppRoute.dashboardActivity(StartUpActivity.this);
             finish();
        }, delayedTime);
    }
}