package com.mksmcqapplicationtest;

import com.mksmcqapplicationtest.beans.Advertise;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.Group;
import com.mksmcqapplicationtest.beans.IMEI;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.SendQuery;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.util.AppUtility;
import com.google.gson.Gson;

public class CreateJsonFunction {

    public String CreateJSONForClass() {
        String WhichList = "assign";
        if (AppUtility.IsTeacher.equals("T"))
            WhichList = "all";
        else if (AppUtility.IsTeacher.equals("A"))
            WhichList = "all";
        Class classResponse = new Class();
        Gson gson = new Gson();
        classResponse.setMasterCode(AppUtility.MASTERCODE);
        classResponse.setUserName(AppUtility.KEY_USERNAME);
        classResponse.setWhichList(WhichList);
        String classResponseJSON = gson.toJson(classResponse);
        String ClassNameJSONString = "[" + classResponseJSON + "]";
        return ClassNameJSONString;
    }

    public String CreateJsonForGetTestList(String Classcode, String WhichList) {

        User user = new User();
        Gson gson = new Gson();
        user.setUsername(AppUtility.KEY_USERNAME);
        user.setClassCode(Classcode);
        user.setWhichList(WhichList);
        String jso = gson.toJson(user);
        String TestListJSONString = "[" + jso + "]";
        return TestListJSONString;
    }


    public String CreateJSONForStudentList(String ClassCode, String WhichList) {
        Gson gson = new Gson();
        Class classResponse = new Class();
        classResponse.setUserName(AppUtility.KEY_USERNAME);
        classResponse.setClassCode(ClassCode);
        classResponse.setWhichList(WhichList);
        String ClassResponseString = gson.toJson(classResponse);
        String StudentNameArrayString = "[" + ClassResponseString + "]";
        return StudentNameArrayString;
    }

    public String CreateJSonForSetting(String WhichList) {
        SendQuery sendQuery = new SendQuery();
        Gson gson = new Gson();
        sendQuery.setUserName(AppUtility.KEY_USERNAME);
        sendQuery.setWhichSetting(WhichList);
        String getQuery = gson.toJson(sendQuery);
        String SettingJSONString = "[" + getQuery + "]";
        return SettingJSONString;
    }

    public String CreateJSONForGetData(String ClassCode, String WhichList, String Extra) {
        Gson gson = new Gson();
        Data dataresponse = new Data();
        dataresponse.setClassCode(ClassCode);
        dataresponse.setUserName(AppUtility.KEY_USERNAME);
        dataresponse.setExtra(Extra);
        dataresponse.setWhichList(WhichList);
        String jsonData = gson.toJson(dataresponse);
        String ClassDataJSONString = "[" + jsonData + "]";
        return ClassDataJSONString;
    }

    public String CreateJsonForSendNotification(String ClassCode, String IsNotificationForGuest, String NotificationText, String NotificationTitle, String StudentCode) {
        SendNotification sendNotification = new SendNotification();
        Gson gson = new Gson();
        sendNotification.setClassCode(ClassCode);
        sendNotification.setUserName(AppUtility.KEY_USERNAME);
        sendNotification.setIsNotificationForGuest(IsNotificationForGuest);
        sendNotification.setNotificationText(NotificationText);
        sendNotification.setNotificationTitle(NotificationTitle);
        sendNotification.setStudentCode(StudentCode);
        String sendNotificationJSON = gson.toJson(sendNotification);
        String sendNotificationJSONString = "[" + sendNotificationJSON + "]";
        return sendNotificationJSONString;
    }

    public String CreteJsonForGetGroupMenu() {
        Group classResponse = new Group();
        Gson gson = new Gson();

        classResponse.setUserName(AppUtility.KEY_USERNAME);
        classResponse.setWhichList(AppUtility.IsTeacher);
        String classResponseJSON = gson.toJson(classResponse);
        String ClassNameJSONString = "[" + classResponseJSON + "]";
        return ClassNameJSONString;
    }
    public String CreteJsonForGetChildMenu() {
        Group classResponse = new Group();
        Gson gson = new Gson();

        classResponse.setUserName(AppUtility.KEY_USERNAME);
        classResponse.setWhichList(AppUtility.IsTeacher);
        String classResponseJSON = gson.toJson(classResponse);
        String ClassNameJSONString = "[" + classResponseJSON + "]";
        return ClassNameJSONString;
    }

    public String CreateJsonForAdvertiseParameter(){
        Advertise  advertise=new Advertise();
        Gson gson=new Gson();
        advertise.setUserName(AppUtility.KEY_USERNAME);
        String stringgsontoJson=gson.toJson(advertise);
        String JsonString="["+stringgsontoJson+"]";
        return JsonString;
    }

    public String CreateJSonForReferences() {
        Advertise sendQuery = new Advertise();
        Gson gson = new Gson();
        sendQuery.setUserName(AppUtility.KEY_USERNAME);
        sendQuery.setPackageName(AppUtility.PackageName);
        String getQuery = gson.toJson(sendQuery);
        String SettingJSONString = "[" + getQuery + "]";
        return SettingJSONString;
    }
    public String CreateJSonForIMEI() {
        IMEI imei = new IMEI();
        Gson gson = new Gson();
        imei.setUserName(AppUtility.KEY_USERNAME);
        imei.setIMEI(AppUtility.Mobile_IMEI_CODE);
        String getQuery = gson.toJson(imei);
        String SettingJSONString = "[" + getQuery + "]";
        return SettingJSONString;
    }

}

