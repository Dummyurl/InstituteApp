package com.mksmcqapplicationtest.beans;


public class TestList {
    String UserName,TestCode,TestName,TestDuration,AcDate,Result,ClassCode;

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public void setTestCode(String testCode) {
        TestCode = testCode;
    }

    public String getTestCode() {
        return TestCode;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestDuration(String testDuration) {
        TestDuration = testDuration;
    }

    public String getTestDuration() {
        return TestDuration;
    }


    public void setAcDate(String acDate) {
        AcDate = acDate;
    }

    public String getAcDate() {
        return AcDate;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }
}
