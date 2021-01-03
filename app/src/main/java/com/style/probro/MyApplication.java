package com.style.probro;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.init(getApplicationContext());
    }
}
