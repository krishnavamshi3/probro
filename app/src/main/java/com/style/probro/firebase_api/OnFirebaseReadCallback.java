package com.style.probro.firebase_api;

public interface OnFirebaseReadCallback<T> {
    void success(T t);
    void notFound();
    void notConnected();
}
