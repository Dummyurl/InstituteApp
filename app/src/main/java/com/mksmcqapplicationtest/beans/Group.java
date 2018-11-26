package com.mksmcqapplicationtest.beans;

public class Group {
    String GroupMenuCode,GroupMenuName,UserName,WhichList,Result,ResultCode;
    String fontAwsome;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setWhichList(String whichList) {
        WhichList = whichList;
    }

    public String getWhichList() {
        return WhichList;
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

    public void setGroupMenuCode(String groupMenuCode) {
        GroupMenuCode = groupMenuCode;
    }

    public String getGroupMenuCode() {
        return GroupMenuCode;
    }

    public void setGroupMenuName(String groupMenuName) {
        GroupMenuName = groupMenuName;
    }

    public String getGroupMenuName() {
        return GroupMenuName;
    }

    public void setFontAwsome(String fontAwsome) {
        this.fontAwsome = fontAwsome;
    }

    public String getFontAwsome() {
        return fontAwsome;
    }
}
