package com.style.probro.app_utils;

import androidx.appcompat.app.ActionBar;

public class AppUtils {


    /**
     * Hide Action Bar
     * @param actionBar Not Null.
     */
    public static void hideActionBar(ActionBar actionBar) {
        if(actionBar != null) {
            actionBar.hide();
        }
    }

}
