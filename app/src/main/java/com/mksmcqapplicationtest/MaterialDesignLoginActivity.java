package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mksmcqapplicationtest.Guest.SignUpForGuest;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class MaterialDesignLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    TextView txtForgotPassword, txtGuestLogin, txtwelcomeText;
    Button btnLogin, btnClear;
    EditText txtUsername, txtPassword;
    View parentLayout;
    public static final String PREFS_NAME = "MyPrefsFile";
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = getApplicationContext();
        try {
            parentLayout = findViewById(android.R.id.content);
            setupUI();

        } catch (Exception e) {
        }
    }

    public void setupUI() {
        txtwelcomeText = (TextView) findViewById(R.id.txtwelcomeText);
        txtUsername = (EditText) findViewById(R.id.txtmdusername);
        txtPassword = (EditText) findViewById(R.id.txtmdpassword);
        btnLogin = (Button) findViewById(R.id.btnmdsignin);
        txtGuestLogin = (TextView) findViewById(R.id.txtGuestLogin);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        String signuptext = getResources().getString(R.string.create_new_account);
        txtGuestLogin.setText(Html.fromHtml("<u>"+signuptext+"</u>"));
//        txtForgotPassword.setText(Html.fromHtml("<u>Forgotten your password?</u>"));

        btnLogin.setOnClickListener(this);
        txtGuestLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        try {
            final Dialog dialog = new Dialog(MaterialDesignLoginActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle, txtMessage;
            final Button dialogButton, CancledialogButton;
            txtTitle = dialog.findViewById(R.id.text_dialog);
            txtTitle.setText(R.string.titletext_exit);
            txtMessage = dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText(R.string.messagetext_exit);
            dialogButton = dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText(R.string.okbtntext_yes);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentback = new Intent(Intent.ACTION_MAIN);
                    intentback.addCategory(Intent.CATEGORY_HOME);
                    intentback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intentback.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentback);
                    finish();
                }
            });
            CancledialogButton = dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText(R.string.canclebtntext_no);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnmdsignin:
                    username = txtUsername.getText().toString().trim();
                    password = txtPassword.getText().toString().trim();
                    if (username.length() > 0 && password.isEmpty()) {
                        Snackbar.make(parentLayout, "Enter Password", Snackbar.LENGTH_LONG).show();
                    } else if (username.isEmpty() && password.length() > 0) {
                        Snackbar.make(parentLayout, "Enter Username", Snackbar.LENGTH_LONG).show();
                    } else if (username.isEmpty() && password.isEmpty()) {
                        Snackbar.make(parentLayout, "Enter User Name And Password", Snackbar.LENGTH_LONG).show();
                    } else {
                        openNextActivity();
                    }
                    break;
                case R.id.txtGuestLogin:
                    Intent intentSignUpGuest = new Intent(this, SignUpForGuest.class);
                    startActivity(intentSignUpGuest);
                    break;
                case R.id.txtForgotPassword:
                    Intent intentForgotPassword = new Intent(this, ForgotPasswordActivity.class);
                    startActivity(intentForgotPassword);
                    break;
            }
        } catch (Exception e) {

        }
    }


    private void openNextActivity() {
        try {
            Intent mainActivity = new Intent(this, MaterialDesignMainActivity.class);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);
            finish();
        } catch (Exception e) {
        }
    }


}
