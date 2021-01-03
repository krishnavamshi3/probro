package com.style.probro.firebase_api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.style.probro.models.PrivacyPolicy;

public class PolicyAPI {
    private static final String TAG = PolicyAPI.class.getSimpleName();
    private static final String PRIVACY_POLICY_COLLECTION_NAME = "policy";
    private static final String TERMS_CONDITIONS_DOCUMENT_ID = "termsandconditions";

    private FirebaseFirestore db;

    public PolicyAPI() {
        this.db = FirebaseFirestore.getInstance();
    }


    public void readTermsAndConditions(OnFirebaseReadCallback<PrivacyPolicy> onFirebaseReadCallback) {
        db.collection(PRIVACY_POLICY_COLLECTION_NAME)
                .document(TERMS_CONDITIONS_DOCUMENT_ID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        PrivacyPolicy privacyPolicy = task.getResult().toObject(PrivacyPolicy.class);
                        if (privacyPolicy == null) {
                            onFirebaseReadCallback.notFound();
                        } else {
                            onFirebaseReadCallback.success(privacyPolicy);
                        }

                    } else if (task.isCanceled()) {
                        onFirebaseReadCallback.notConnected();
                    }
                });
    }

}
