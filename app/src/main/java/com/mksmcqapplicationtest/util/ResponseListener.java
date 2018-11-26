package com.mksmcqapplicationtest.util;

/**
 * Created by USER on 7/4/2016.
 */
public interface ResponseListener {
    void onResponse(Object data);
    void onResponseWithRequestCode(Object data,int requestCode);
    void noResponse(String error);
}
