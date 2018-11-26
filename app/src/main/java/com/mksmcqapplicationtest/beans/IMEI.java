package com.mksmcqapplicationtest.beans;

public class IMEI {

    String IMEI,UserName,Result,ResultCode;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMEI() {
        return IMEI;
    }
}
