package com.style.probro.app_utils;

import android.app.Activity;
import android.content.Intent;

import com.style.probro.order.OrderSummaryActivity;
import com.style.probro.models.PBOrder;
import com.style.probro.auth.FirebaseUIActivity;
import com.style.probro.cart.MyCartActivity;
import com.style.probro.dashboard.MainActivity;
import com.style.probro.models.PBArticle;
import com.style.probro.product_description.ProductDescriptionActivity;
import com.style.probro.returnandrefund.ReturnPolicyActivity;
import com.style.probro.termsandcondition.PrivacyPolicyActivity;

public class AppRoute {

    public static void dashboardActivity(Activity ac) {
        ac.startActivity(new Intent(ac, MainActivity.class));
    }

    public static void termsAndConditionActivity(Activity ac) {
        ac.startActivity(new Intent(ac, PrivacyPolicyActivity.class));
    }

    public static void returnPolicyActivity(Activity ac) {
        ac.startActivity(new Intent(ac, ReturnPolicyActivity.class));
    }

    public static void firebaseUIActivity(Activity ac) {
        ac.startActivity(new Intent(ac, FirebaseUIActivity.class));
    }

    public static void productDescriptionActivity(Activity ac, PBArticle pbArticle) {
        Intent intent = new Intent(ac, ProductDescriptionActivity.class);
        intent.putExtra(AppConstants.PB_ARTICLE_INTENT_KEY, pbArticle);
        ac.startActivity(intent);
    }

    public static void myCart(Activity ac) {
        ac.startActivity(new Intent(ac, MyCartActivity.class));
    }

    public static void orderSummary(Activity ac, PBOrder pbOrder) {
        Intent intent = new Intent(ac, OrderSummaryActivity.class);
        intent.putExtra(AppConstants.PB_ORDER_INTENT_KEY, pbOrder);
        ac.startActivity(intent);
    }
}
