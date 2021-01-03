package com.style.probro.firebase_api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.style.probro.models.PBArticle;

import java.util.ArrayList;
import java.util.List;

public class ArticleAPI {
    private static final String TAG = ArticleAPI.class.getSimpleName();

    private FirebaseFirestore db;

    public ArticleAPI() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void readAllArticles(OnFirebaseReadCallback<List<PBArticle>> onFirebaseReadCallback) {
        List<PBArticle> pbArticleList = new ArrayList<>();
        db.collection(FirebaseConstants.ARTICLES_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                pbArticleList.add(document.toObject(PBArticle.class));
                            }

                            onFirebaseReadCallback.success(pbArticleList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            onFirebaseReadCallback.notFound();
                        }
                    }
                });
    }

}
