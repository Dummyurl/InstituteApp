package com.mksmcqapplicationtest.network;

/**
 * Created by USER on 6/27/2016.
 */
public interface NetworkResponseListener {
    void onDataReceived(int requestCode, Object data, String url);
    void onDataFailed(int requestCode, Object error);
}
