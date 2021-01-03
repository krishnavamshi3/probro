package com.style.probro.termsandcondition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.style.probro.R;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.firebase_api.OnFirebaseReadCallback;
import com.style.probro.firebase_api.PolicyAPI;
import com.style.probro.models.PrivacyPolicy;

public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView privacyPolicyTV;
    String privacyPolicyText;

    PolicyAPI mPolicyAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        AppUtils.hideActionBar(getSupportActionBar());
        privacyPolicyTV = findViewById(R.id.privacy_policy_tv);
        mPolicyAPI = new PolicyAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(privacyPolicyText == null) {
            mPolicyAPI.readTermsAndConditions(new OnFirebaseReadCallback<PrivacyPolicy>() {
                @Override
                public void success(PrivacyPolicy privacyPolicy) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        privacyPolicyTV.setText(Html.fromHtml(privacyPolicy.getContent(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        privacyPolicyTV.setText(Html.fromHtml(privacyPolicy.getContent()));
                    }
                    //termsAndConditionsTV.setText(privacyPolicy.getContent());
                }

                @Override
                public void notFound() {
                    privacyPolicyTV.setText(getString(R.string.terms_and_conditions_not_found_in_server_msg));
                }

                @Override
                public void notConnected() {
                    privacyPolicyTV.setText(getString(R.string.unknown_error_occur_error_string));
                }
            });
        }
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }
}