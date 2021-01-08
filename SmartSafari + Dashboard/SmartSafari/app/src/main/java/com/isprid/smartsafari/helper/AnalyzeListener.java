package com.isprid.smartsafari.helper;

public interface AnalyzeListener {
    void onSuccess(String type, String rate);

    void onError(String message);
}