package com.mksmcqapplicationtest.Guest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.OTPClass;
import com.google.gson.Gson;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AdvertiesClass;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.SignUpGuest;

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.mksmcqapplicationtest.util.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SignUpForGuest extends AppCompatActivity implements View.OnClickListener {
    TextView txtFName, txtMName, txtLName, txtMobile, txtPassword, txtConfirmPassword, txtEmailAddress;
    Button btnSignUp;
    Spinner spinnerForClass, spinnerForUniversity;
    RadioButton rdbMale, rdbFemale;
    View parentLayout;
    String FirstName, MiddleName, LastName, MobileNumber, EmailAddress, Password, ConfirmPassword,
            ClassCode = "0", UniversityCode = "0", Gender;
    String[] ClassName=new String[1];
    ArrayList<Class> classArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        try {
            parentLayout = findViewById(android.R.id.content);
            txtFName = (TextView) findViewById(R.id.txtmdFirstName);
            txtMName = (TextView) findViewById(R.id.txtmdMiddleName);
            txtLName = (TextView) findViewById(R.id.txtmdLastName);
            txtMobile = (TextView) findViewById(R.id.txtmdMobileNumber);
            txtPassword = (TextView) findViewById(R.id.txtmdpassword);
            txtConfirmPassword = (TextView) findViewById(R.id.txtmdconfirmpassword);
            txtEmailAddress = (TextView) findViewById(R.id.txtmdEmailAddress);

//            SpinnerDialog spinnerDialog=new SpinnerDialog(this,getClassList(),"Select Class",)
            spinnerForClass = (Spinner) findViewById(R.id.spinnerForClassForGuest);

            ClassName[0]="Select Class";
            ArrayAdapter<String> classAdpter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,ClassName);
            classAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerForClass.setAdapter(classAdpter);

//            spinnerForUniversity = (Spinner) findViewById(R.id.spinnerForUniversity);
            rdbMale = (RadioButton) findViewById(R.id.rdbMale);
            rdbFemale = (RadioButton) findViewById(R.id.rdbFemale);
            btnSignUp = (Button) findViewById(R.id.btnmdsignup);
            btnSignUp.setOnClickListener(this);

            rdbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rdbFemale.setChecked(false);
                        Gender = "M";
                    }
                }
            });
            rdbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        rdbMale.setChecked(false);
                        Gender = "F";
                    }
                }
            });


//            getSupportActionBar().setTitle("Sign Up");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);

            CustomTextViewBold txtactionbartitle=(CustomTextViewBold) findViewById(R.id.txtactionbartitle);
            txtactionbartitle.setText(getResources().getString(R.string.sign_up_title));
            ImageButton imagviewbackpress=(ImageButton)findViewById(R.id.imagviewbackpress);
            imagviewbackpress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        } catch (Exception e) {
        }
    }

    public ArrayList<Class> getClassList(){
//        classArrayList.add(new Class("1","Select"));
        return classArrayList;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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
                case R.id.btnmdsignup:

//                    CheckAllFiled();
                    OTPClass otpClass = new OTPClass(this, "", "", "SignUp", parentLayout);
                    otpClass.dialogForOTP();

                    break;
            }
        } catch (Exception e) {
        }
    }


    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() != 10) {
                check = false;
//                txtMobile.setError("Please Enter 10 digit mobile number.");
            } else {
                check = true;
            }
        } else {
            check = false;
        }
        return check;

    }

    private String CheckAllFiled() {
        String Errormsz = "";

        if (FirstName.equals("")) {
            Errormsz = Errormsz + "Enter First Name. \n";
        }
        if (MiddleName.equals("")) {
            Errormsz = Errormsz + "Enter Middle Name. \n";
        }
        if (LastName.equals("")) {
            Errormsz = Errormsz + "Enter Last Name. \n";
        }
        if (EmailAddress.equals("")) {
            Errormsz = Errormsz + "Enter Email Address.\n";
        } else {
            Boolean checkEmailValid = isValidMail(EmailAddress);
            if (checkEmailValid == false) {
                Errormsz = Errormsz + "Invalid Email Address.\n";
            }
        }
        if (MobileNumber.equals("")) {
            Errormsz = Errormsz + "Enter Mobile Number. \n";
        } else {
            Boolean checkMobileNo = isValidMobile(MobileNumber);
            if (checkMobileNo == false) {
                Errormsz = Errormsz + "Invaild Mobile Number. \n";
            }
        }

        if (Password.equals("")) {
            Errormsz = Errormsz + "Enter Password.\n";
        }
        if (ClassCode.equals("0") || ClassCode == null) {
            Errormsz = Errormsz + "Select Atleast One Class Name.\n ";
        }
        if (UniversityCode.equals("0") || UniversityCode == null) {
            Errormsz = Errormsz + "Select Atleast One University Name.\n ";
        }
        if (!(rdbMale.isChecked() || rdbFemale.isChecked())) {
            Errormsz = Errormsz + "Select atleast Male/Female.\n";
        } else if (!(Password.equals(ConfirmPassword))) {
            Errormsz = Errormsz + "Password and Confirm Password are match.\n";
        }
        if (Password.length() < 6) {
            Errormsz = Errormsz + "Should enter minimum 6 digit password.\n";
        }
        if (Errormsz != "")
            Snackbar.make(parentLayout, Errormsz, Snackbar.LENGTH_LONG).show();

        return Errormsz;

    }



}
