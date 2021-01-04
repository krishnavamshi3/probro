package com.style.probro.firebase_api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.style.probro.models.FailureObject;
import com.style.probro.models.PBOrder;

public class OrderAPI {
    private static final String TAG = OrderAPI.class.getSimpleName();

    private FirebaseFirestore db;

    public OrderAPI() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void writeNewOrder(final PBOrder order, final OnCrudFirebaseCallback<PBOrder> callback) {
        db
                .collection(order.getUserName()+"_orders")
                .document(order.getOrderID()).set(order)
                .addOnSuccessListener(aVoid -> callback.success(order))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.Failure(new FailureObject(-1, e.getMessage()));
                    }
                });
    }


}
