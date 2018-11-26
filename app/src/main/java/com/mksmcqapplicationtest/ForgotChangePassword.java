package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.google.gson.Gson;

import java.util.List;

public class ForgotChangePassword extends AppCompatActivity implements View.OnClickListener {

    CustomEditText edtNewPassword, edtConfirmPassword;
    CustomButton btnChangePassword;
            Button btnCancel;
    String NewPassword, ConfirmPassword, ChangePasswordArrayString, ClassNameJSONString;
    View parentLayout;

    SharedPreferences sharedPreferences;
    Bundle bundle;
    String Username;
    int count = 0;
    List<Class> classResponse;
    int hide1 = 0, hide2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_change_password);
        try {
            parentLayout = findViewById(android.R.id.content);
//            sharedPreferences = getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
            edtNewPassword = (CustomEditText) findViewById(R.id.edtNewPassword);
            edtConfirmPassword = (CustomEditText) findViewById(R.id.edtConfirmPassword);
            btnChangePassword = (CustomButton) findViewById(R.id.btnChangePassword);
            btnChangePassword.setOnClickListener(this);
//            btnCancel = (Button) findViewById(R.id.btnCancel);
//            btnCancel.setOnClickListener(this);

//            bundle = getIntent().getExtras();
//            if (bundle != null) {
//                Username = bundle.getString("UserName");
//            }

//            edtNewPassword.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    int DRAWABLE_LEFT = 0;
//                    int DRAWABLE_TOP = 1;
//                    int DRAWABLE_RIGHT = 2;
//                    int DRAWABLE_BOTTOM = 3;
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        if (event.getRawX() >= (edtNewPassword.getRight() - edtNewPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                            if (hide1 == 0) {
//                                edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                                hide1 = 1;
//                                edtNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
//                            } else {
//                                edtNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
//                                edtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                                hide1 = 0;
//                            }
//
//                        }
//                    }
//
//                    return false;
//                }
//            });
//
//
//            edtConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    int DRAWABLE_LEFT = 0;
//                    int DRAWABLE_TOP = 1;
//                    int DRAWABLE_RIGHT = 2;
//                    int DRAWABLE_BOTTOM = 3;
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        if (event.getRawX() >= (edtConfirmPassword.getRight() - edtConfirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                            if (hide2 == 0) {
//                                edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                                hide2 = 1;
//                                edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
//                            } else {
//                                edtConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
//                                edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                                hide2 = 0;
//                            }
//
//                        }
//                    }
//
//                    return false;
//                }
//            });


            CustomTextViewBold txtactionbartitle=(CustomTextViewBold) findViewById(R.id.txtactionbartitle);
            txtactionbartitle.setText(getResources().getString(R.string.forgot_password_title));
            ImageButton imagviewbackpress=(ImageButton)findViewById(R.id.imagviewbackpress);
            imagviewbackpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
//            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle(" Forgot Password");
//            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
//            setSupportActionBar(toolbar);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotChangePassword.this,parentLayout, "ForgotChangePassword", "OnCreate", e);
            pc.showCatchException();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnChangePassword:

                    CheckValidation();

                    break;
//                case R.id.btnCancel:
//
//                    edtNewPassword.setText("");
//                    edtConfirmPassword.setText("");
//                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotChangePassword.this,parentLayout, "ForgotChangePassword", "onClick", e);
            pc.showCatchException();
        }
    }

    private void CheckValidation() {
        try {
            NewPassword = edtNewPassword.getText().toString().trim();
            ConfirmPassword = edtConfirmPassword.getText().toString().trim();
            CheckAllFiled();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotChangePassword.this,parentLayout, "ForgotChangePassword", "CheckValidation", e);
            pc.showCatchException();
        }
    }

    private void CheckAllFiled() {
        try {
            String Errormsz = "";

            if (NewPassword.equals("")) {
                Errormsz = "Please Enter Password.";
                Snackbar.make(parentLayout, Errormsz, Snackbar.LENGTH_LONG).show();
            } else if (ConfirmPassword.equals("")) {
                Errormsz = "Please Enter Confirm Password.";
                Snackbar.make(parentLayout, Errormsz, Snackbar.LENGTH_LONG).show();
            } else if (!NewPassword.equals(ConfirmPassword)) {
                Errormsz = "New Password and Confirm Password are not same";
                Snackbar.make(parentLayout, Errormsz, Snackbar.LENGTH_LONG).show();
            } else if (NewPassword.length() < 6) {
                Errormsz = "Should enter minimum 6 digit password.";
                Snackbar.make(parentLayout, Errormsz, Snackbar.LENGTH_LONG).show();
            } else {
                AppUtility.KEY_NEW_PASSWORD = NewPassword;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotChangePassword.this,parentLayout, "ForgotChangePassword", "CheckAllFiled", e);
            pc.showCatchException();
        }
    }




}
