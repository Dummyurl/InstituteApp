package com.mksmcqapplicationtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.Guest.MyProfileForGuest;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ProfileAdapterForStudent;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.view.View.GONE;

public class MyProfileForStudent extends AppCompatActivity implements  View.OnClickListener {
String password;
    String JSONForProfile;
    View view;
    int count, count1;
    List<Class> classResponse;
    Spinner spinner, spinnerForClass;
    String[] UserNameDropDown = new String[1];
    String[] UserNameDropDownForClass = new String[1];
    int j = 0, j1 = 0;
    String StudentCodeForNetworkCall, ClassNameJSONString, StudentNameArrayString, ClassCodeForNetWorkCall;
    LinearLayout profile;
    Bitmap bmp;
    String JSONForUpdate;
    ImageView image,ImgEditProfile;
    RecyclerView recyclerViewProfileUsername, recyclerViewProfilePassword,recyclerViewProfileEmailAddress;
    TextView user_profile_name;
    ProfileAdapterForStudent profileAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_for_student);
        try{
            view = findViewById(android.R.id.content);

            spinnerForClass = (Spinner) findViewById(R.id.spinnerForClass);
            spinnerForClass.setAdapter(null);
            spinner = (Spinner) findViewById(R.id.spinnerForStudentName);
            spinner.setAdapter(null);
            image = (ImageView) findViewById(R.id.image);
            ImgEditProfile = (ImageView) findViewById(R.id.ImgEditProfile);
            ImgEditProfile.setOnClickListener(this);
            profile = (LinearLayout) findViewById(R.id.ProfileLinearLayout);
            profile.setVisibility(GONE);
            if (AppUtility.IsDemoApplication) {
                ImgEditProfile.setClickable(false);
                ImgEditProfile.setEnabled(false);
            } else {
                ImgEditProfile.setClickable(true);
                ImgEditProfile.setEnabled(true);
            }
            user_profile_name = (TextView) findViewById(R.id.user_profile_name);

            if(AppUtility.ClassName.length>1){
                UserNameDropDownForClass = new String[AppUtility.ClassName.length + 1];
                j1 = 0;
                UserNameDropDownForClass[j1] = "Select Class Name...";
                for (int i = 0; i < AppUtility.ClassName.length; i++) {
                    j1 = j1 + 1;
                    UserNameDropDownForClass[j1] = AppUtility.ClassName[i];

                }
            }else{
                UserNameDropDownForClass = new String[AppUtility.ClassName.length];
                // UserNameDropDownForClass[j1] = "Select Class Name...";
                for (int i = 0; i < AppUtility.ClassName.length; i++) {

                    UserNameDropDownForClass[j1] = AppUtility.ClassName[i];
                    j1 = j1 + 1;
                }
            }


            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UserNameDropDownForClass);
            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerForClass.setAdapter(Select_Category_Adapter);
            j = 0;
            spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    count1 = position;
                    if(AppUtility.ClassName.length>1){
                        if (count1 != 0) {
                            ClassCodeForNetWorkCall = AppUtility.ClassCode[count1 - 1];
                            UserNameDropDown[j] = AppUtility.StudentName[position - 1];
                            StudentCodeForNetworkCall = AppUtility.StudentCode[position - 1];
                            ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(MyProfileForStudent.this, android.R.layout.simple_spinner_item, UserNameDropDown);
                            Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(Select_Sudent_Adapter);

                        } else {
                            spinner.setAdapter(null);
                            profile.setVisibility(GONE);
                        }}else {
                        ClassCodeForNetWorkCall = AppUtility.ClassCode[count1];
                        UserNameDropDown[j] = AppUtility.StudentName[position];
                        StudentCodeForNetworkCall = AppUtility.StudentCode[position];
                        ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(MyProfileForStudent.this, android.R.layout.simple_spinner_item, UserNameDropDown);
                        Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(Select_Sudent_Adapter);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            recyclerViewProfileUsername = (RecyclerView) findViewById(R.id.recyclerViewProfileUsername);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewProfileUsername.setLayoutManager(layoutManager);
            recyclerViewProfileUsername.setItemAnimator(new DefaultItemAnimator());


            recyclerViewProfilePassword = (RecyclerView) findViewById(R.id.recyclerViewProfilePassword);
            RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
            recyclerViewProfilePassword.setLayoutManager(layoutManager1);
            recyclerViewProfilePassword.setItemAnimator(new DefaultItemAnimator());

            recyclerViewProfileEmailAddress = (RecyclerView) findViewById(R.id.recyclerViewProfileEmailAdress);
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
            recyclerViewProfileEmailAddress.setLayoutManager(layoutManager2);
            recyclerViewProfileEmailAddress.setItemAnimator(new DefaultItemAnimator());

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" My Profile");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
        }catch(Exception e){
            PrintCatchException pc = new PrintCatchException(MyProfileForStudent.this,view, "MyProfileForStudent", "OnCreate", e);
            pc.showCatchException();
        }
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
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadImage(ImageView image, String profile) {
        try{
            if (profile != null || !profile.equals("")) {
                String imageString = AppUtility.baseUrl + profile;
                Picasso.with(this)
                        .load(imageString).into(image);
            }
        }catch(Exception e){
            PrintCatchException pc = new PrintCatchException(MyProfileForStudent.this,view, "MyProfileForStudent", "loadImage", e);
            pc.showCatchException();
        }
    }





    @Override
    public void onClick(View view) {
        try{
            Intent intent1 = new Intent(MyProfileForStudent.this, ImageSelectActivity.class);
            startActivityForResult(intent1, 1);
        }catch(Exception e){
            PrintCatchException pc = new PrintCatchException(MyProfileForStudent.this,view, "MyProfileForStudent", "onClick", e);
            pc.showCatchException();
        }

    }
}
