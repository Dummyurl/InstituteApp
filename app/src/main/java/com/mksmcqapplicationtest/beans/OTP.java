package com.mksmcqapplicationtest.beans;

public class OTP {
    String UserName, Password, OTP, Result, ResultCode;
    String Extra, ParamValue, SettingCode, resendMailToWhom, Id;
    String Uninstall, NewPackageName,ParamCode;

    public String getParamCode() {
        return ParamCode;
    }

    public void setParamCode(String paramCode) {
        ParamCode = paramCode;
    }

    public void setNewPackageName(String newPackageName) {
        NewPackageName = newPackageName;
    }

    public String getNewPackageName() {
        return NewPackageName;
    }

    public void setUninstall(String uninstall) {
        Uninstall = uninstall;
    }

    public String getUninstall() {
        return Uninstall;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    public String getExtra() {
        return Extra;
    }

    public void setParamValue(String paramValue) {
        ParamValue = paramValue;
    }

    public String getParamValue() {
        return ParamValue;
    }

    public String getSettingCode() {
        return SettingCode;
    }

    public void setSettingCode(String settingCode) {
        SettingCode = settingCode;
    }

    public String getResendMailToWhom() {
        return resendMailToWhom;
    }

    public void setResendMailToWhom(String resendMailToWhom) {
        this.resendMailToWhom = resendMailToWhom;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getOTP() {
        return OTP;
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
}
