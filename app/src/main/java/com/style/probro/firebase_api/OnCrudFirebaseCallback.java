package com.style.probro.firebase_api;


import com.style.probro.models.FailureObject;

public interface OnCrudFirebaseCallback<T> {
    void success(T userFromFirebase);
    void Failure(FailureObject object);
}
