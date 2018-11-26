package com.mksmcqapplicationtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.google.gson.Gson;

import java.util.List;


public class OTPClass  {

    public Context context;
    String UserName, Password, ActivityName, OTP, otpJSONString, ResendOtpJSONString;
    View parentLayout;
    Button ResendButton;
    TextView text_error;

    public OTPClass(Context context, String userName, String password, String activityName, View parentLayout) {
        this.context = context;
        this.UserName = userName;
        this.Password = password;
        this.ActivityName = activityName;
        this.parentLayout = parentLayout;

    }

    public void dialogForOTP() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_otp);
           // TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            //txtTitle.setText("Enter OPT");
//            TextView txtMessage = (TextView) dialog.findViewById(R.id.txtOtp);
           // text_error = (TextView) dialog.findViewById(R.id.text_error);
            //txtMessage.setText("OTP is send on your register email address and mobile number");
            //final EditText editTextEnterOTP = (EditText) dialog.findViewById(R.id.edtEnterOTP);
            final CustomEditText editTextEnterOTP =dialog.findViewById(R.id.editTextEnterOTP);
            final Button dialogButton = (Button) dialog.findViewById(R.id.btnVerify);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OTP = editTextEnterOTP.getText().toString();
                    if (OTP.equals("") || OTP == null) {
                        editTextEnterOTP.setError("Enter OTP");
                    } else {
                        dialogForResponse();
                    }

                }
            });

            CustomTextViewBold ResendButton =  dialog.findViewById(R.id.txtResend);
            ResendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialogForResponse();
                }
            });
//
            final ImageButton CancledialogButton = dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception ex) {
            Log.d("Exception", "" + ex);
        }

    }



    private void dialogForResponse() {
        try{
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Congratulation!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("OTP Verify Successfully");
            txtMessage.setTextColor(context.getResources().getColor(R.color.black));
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("OK");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ActivityName.equals("SignUp")) {
                        Intent inttentTohome = new Intent(context, MaterialDesignLoginActivity.class);
                        context.startActivity(inttentTohome);
                        ((Activity) context).finish();
                    } else if (ActivityName.equals("Login")) {
                        SharedPreferences setting = context.getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = setting.edit();
                        editor.putBoolean("LoggedIn", true);
                        editor.putString("Username", AppUtility.KEY_USERNAME);
                        editor.putString("Password", AppUtility.KEY_PASSWORD);
                        editor.putString("StudentCode", AppUtility.KEY_STUDENTCODE);
                        editor.putString("StudentName", AppUtility.KEY_STUDENTNAME);
                        //TODO CLassCode
                        editor.putString("ClassCode", AppUtility.KEY_CLASSCODE);
                        editor.putString("ClassName", AppUtility.KEY_CLASSNAME);
                        editor.putString("IsTeacher", AppUtility.IsTeacher);
                        editor.putString("Profile", AppUtility.PROFILE);
                        editor.commit();
                        AppUtility.IsFirstTimeHome = true;
                        Intent inttentTohome = new Intent(context, MaterialDesignMainActivity.class);
                        context.startActivity(inttentTohome);
                        ((Activity) context).finish();
                    }
                    else if(ActivityName.equals("ForgotPassword")){
                        Intent inttentTohome = new Intent(context, ForgotChangePassword.class);
                        context.startActivity(inttentTohome);
                        ((Activity) context).finish();
                    }

                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            dialog.show();

        }catch(Exception e){
            PrintCatchException pc = new PrintCatchException(context,parentLayout, "OTPClass", "dialogForResponse", e);
            pc.showCatchException();
        }
    }


}
