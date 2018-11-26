package com.mksmcqapplicationtest.beans;


public class GivenTestListResponse {
    String Attempt, ClassCode, ClassName, Score, TestCode, TestName, Time, returnValue,
            SubmittedTestTime,UserName,ResultCode,NegativeMarks;

    public void setNegativeMarks(String negativeMarks) {
        NegativeMarks = negativeMarks;
    }

    public String getNegativeMarks() {
        return NegativeMarks;
    }

    public void setSubmittedTestTime(String submittedTestTime) {
        SubmittedTestTime = submittedTestTime;
    }
    public String getSubmittedTestTime() {
        return SubmittedTestTime;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public String getAttempt() {
        return Attempt;
    }

    public void setAttempt(String attempt) {
        Attempt = attempt;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getTestCode() {
        return TestCode;
    }

    public void setTestCode(String testCode) {
        TestCode = testCode;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }
}
