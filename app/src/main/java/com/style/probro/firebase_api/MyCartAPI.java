package com.style.probro.firebase_api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.style.probro.models.PBArticle;
import com.style.probro.models.PrivacyPolicy;

import java.util.ArrayList;
import java.util.List;

public class MyCartAPI {
    private static final String TAG = MyCartAPI.class.getSimpleName();

    private FirebaseFirestore db;

    public MyCartAPI() {
        this.db = FirebaseFirestore.getInstance();
    }
}
