package com.mksmcqapplicationtest.beans;

/**
 * Created by Samsung on 22/07/2017.
 */

public class TimeTable {
    String ClassCode,Result,StartTime,EndTime,Subject,NotificationFlag;

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getFlagSetting() {
        return NotificationFlag;
    }

    public void setFlagSetting(String NotificationFlag) {
        this.NotificationFlag = NotificationFlag;
    }
}
