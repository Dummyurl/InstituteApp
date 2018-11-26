package com.mksmcqapplicationtest;

import android.content.Context;
import android.content.SharedPreferences;

import com.mksmcqapplicationtest.util.AppUtility;

public class UpdateLocalValues {


    public static void updateAllVauesLocaly(Context context, SharedPreferences sharedPreferences) {
        try {
            sharedPreferences = context.getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
            AppUtility.KEY_USERNAME = sharedPreferences.getString("Username", null);
            AppUtility.KEY_PASSWORD = sharedPreferences.getString("Password", null);
            AppUtility.KEY_STUDENTCODE = sharedPreferences.getString("StudentCode", null);
            AppUtility.KEY_STUDENTNAME = sharedPreferences.getString("StudentName", null);
            //TODO CLassCode
            AppUtility.PROFILE = sharedPreferences.getString("Profile", null);
            AppUtility.KEY_CLASSCODE = sharedPreferences.getString("ClassCode", null);
            AppUtility.KEY_CLASSNAME = sharedPreferences.getString("ClassName", null);
            AppUtility.IsTeacher = sharedPreferences.getString("IsTeacher", null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
