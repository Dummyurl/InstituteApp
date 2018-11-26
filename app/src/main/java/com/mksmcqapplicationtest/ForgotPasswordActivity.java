package com.mksmcqapplicationtest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.google.gson.Gson;

import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSearch, btnCancel;
    TextView  txtError;
    CustomEditText txtEmailMobile;
    String EmailOrMobile, SearchJSONString;

    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        try {

            parentLayout = findViewById(android.R.id.content);
            txtEmailMobile = (CustomEditText) findViewById(R.id.txtEmailMobile);
            txtError = (TextView) findViewById(R.id.txtError);
            btnSearch = (Button) findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(this);

//            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setTitle(" Forgot Password");
//            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
//            setSupportActionBar(toolbar);

            CustomTextViewBold txtactionbartitle=(CustomTextViewBold) findViewById(R.id.txtactionbartitle);
            txtactionbartitle.setText(getResources().getString(R.string.forgot_password_title));
            ImageButton imagviewbackpress=(ImageButton)findViewById(R.id.imagviewbackpress);
            imagviewbackpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotPasswordActivity.this,parentLayout, "ForgotPasswordActivity", "OnCreate", e);
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

                case R.id.btnSearch:

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    OTPClass otpClass = new OTPClass(ForgotPasswordActivity.this, EmailOrMobile, "11223344556677889900", "ForgotPassword", parentLayout);
                    otpClass.dialogForOTP();
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ForgotPasswordActivity.this,parentLayout, "ForgotPasswordActivity", "onClick", e);
            pc.showCatchException();
        }
    }



}
