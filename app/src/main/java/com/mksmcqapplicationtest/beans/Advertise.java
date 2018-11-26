package com.mksmcqapplicationtest.beans;

public class Advertise {
    String UserName, Result, ResultCode, IsAdvertiseVisible, AboveAdvertiesIsVisible, AdvertiseVisibleForGuest,PackageName;

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setIsAdvertiseVisible(String isAdvertiseVisible) {
        IsAdvertiseVisible = isAdvertiseVisible;
    }

    public String getIsAdvertiseVisible() {
        return IsAdvertiseVisible;
    }

    public void setAboveAdvertiesIsVisible(String aboveAdvertiesIsVisible) {
        AboveAdvertiesIsVisible = aboveAdvertiesIsVisible;
    }

    public String getAboveAdvertiesIsVisible() {
        return AboveAdvertiesIsVisible;
    }

    public void setAdvertiseVisibleForGuest(String advertiseVisibleForGuest) {
        AdvertiseVisibleForGuest = advertiseVisibleForGuest;
    }

    public String getAdvertiseVisibleForGuest() {
        return AdvertiseVisibleForGuest;
    }
}
