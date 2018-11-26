package com.mksmcqapplicationtest.beans;


public class SendNotification {
    String NotificationText,ClassCode,Result,NotificationId,NotificationTitle,AcDate,AcFlag,
            ResultCode,Check,IsNotificationForGuest,UserName,WhichList,StudentCode;
    private boolean selected;

    public void setStudentCode(String studentCode) {
        StudentCode = studentCode;
    }

    public String getStudentCode() {
        return StudentCode;
    }

    public void setWhichList(String whichList) {
        WhichList = whichList;
    }

    public String getWhichList() {
        return WhichList;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setNotificationText(String notificationText) {
        NotificationText = notificationText;
    }

    public String getNotificationText() {
        return NotificationText;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }

    public void setNotificationId(String notificationId) {
        NotificationId = notificationId;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setAcDate(String acDate) {
        AcDate = acDate;
    }

    public String getAcDate() {
        return AcDate;
    }

    public void setAcFlag(String acFlag) {
        AcFlag = acFlag;
    }

    public String getAcFlag() {
        return AcFlag;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setCheck(String check) {
        Check = check;
    }

    public String getCheck() {
        return Check;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setIsNotificationForGuest(String isNotificationForGuest) {
        IsNotificationForGuest = isNotificationForGuest;
    }

    public String getIsNotificationForGuest() {
        return IsNotificationForGuest;
    }
}
