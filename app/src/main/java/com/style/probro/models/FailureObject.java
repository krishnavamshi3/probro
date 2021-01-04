package com.style.probro.models;

public class FailureObject {
    private int code;
    private String message;


    public FailureObject() {
    }

    public FailureObject(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
