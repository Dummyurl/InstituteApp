package com.mksmcqapplicationtest.Guest;

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
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.ImageSelectActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AdvertiesClass;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ProfileAdapterForStudent;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.view.View.GONE;

public class MyProfileForGuest extends AppCompatActivity implements ResponseListener, View.OnClickListener {

    String JSONForProfile;
    List<Student> students;
    View view;
    int count1;
    Spinner spinner, spinnerForClass;
    String[] UserNameDropDown = new String[1];
    String[] UserNameDropDownForClass = new String[1];
    int j = 0, j1 = 0;
    String StudentCodeForNetworkCall, ClassCodeForNetWorkCall;
    LinearLayout profile;
    Bitmap bmp;
    String JSONForUpdate;
    ImageView image, ImgEditProfile;
    RecyclerView recyclerViewProfileUsername, recyclerViewProfilePassword, recyclerViewProfileEmailAddress;
    TextView user_profile_name;
    ProfileAdapterForStudent profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_for_student);
        try {
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

            if (AppUtility.ClassName.length > 1) {
                UserNameDropDownForClass = new String[AppUtility.ClassName.length + 1];
                j1 = 0;
                UserNameDropDownForClass[j1] = "Select Class Name...";
                for (int i = 0; i < AppUtility.ClassName.length; i++) {
                    j1 = j1 + 1;
                    UserNameDropDownForClass[j1] = AppUtility.ClassName[i];

                }
            } else {
                UserNameDropDownForClass = new String[AppUtility.ClassName.length];
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
                    if (AppUtility.ClassName.length > 1) {

                        if (count1 != 0) {
                            ClassCodeForNetWorkCall = AppUtility.ClassCode[count1 - 1];
                            UserNameDropDown[j] = AppUtility.StudentName[position - 1];
                            StudentCodeForNetworkCall = AppUtility.StudentCode[position - 1];
                            ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(MyProfileForGuest.this, android.R.layout.simple_spinner_item, UserNameDropDown);
                            Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(Select_Sudent_Adapter);

                        } else {
                            spinner.setAdapter(null);
                            profile.setVisibility(GONE);
                        }
                    } else {
                        ClassCodeForNetWorkCall = AppUtility.ClassCode[count1];
                        UserNameDropDown[j] = AppUtility.StudentName[position];
                        StudentCodeForNetworkCall = AppUtility.StudentCode[position];
                        ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(MyProfileForGuest.this, android.R.layout.simple_spinner_item, UserNameDropDown);
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

                    NetWorkCallForProfile();
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

            if (AppUtility.IsAdvertiesVisibleForGuest) {
                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();

            }
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" My Profile");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForGuest.this,view, "MyProfileForGuest", "OnCreate", e);
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

    private void NetWorkCallForProfile() {
        try {
            String Msg = "Loading Profile Please Wait";

            CreayeJSonForProfile();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForGuest.this,view, "MyProfileForGuest", "NetWorkCallForProfile", e);
            pc.showCatchException();
        }
    }

    private void CreayeJSonForProfile() {
        Student student = new Student();
        Gson gson = new Gson();
        student.setStudentCode(StudentCodeForNetworkCall);
        student.setClassCode(ClassCodeForNetWorkCall);
        student.setWhichProfile("GuestProfile");
        String profilejson = gson.toJson(student);
        JSONForProfile = "[" + profilejson + "]";
    }

    @Override
    public void onResponse(Object data) {
        String Message = "Please Check internet Connection and retry";
        Snackbar.make(view, Message, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NetWorkCallForProfile();
                    }
                }).show();
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 480:
                    students = (List<Student>) data;
                    try {
                        if (students.get(0).getResult().equals("Suessfull")) {
                            profile.setVisibility(View.VISIBLE);
                            user_profile_name.setText(students.get(0).getStudentName());
                            loadImage(image, students.get(0).getProfile());

                            String MobileNo = students.get(0).getMobileNumber();
                            String Password = students.get(0).getNewPassword();
                            String FirstTwoCharacter = Password.substring(0, 2);
                            String LastTeoCharacter = Password.substring(Password.length() - 2, Password.length());
                            String FinalPassword = FirstTwoCharacter + "******" + LastTeoCharacter;

                            profileAdapter = new ProfileAdapterForStudent(this, "Username", MobileNo, R.string.fa_User);
                            recyclerViewProfileUsername.setAdapter(profileAdapter);
                            profileAdapter = new ProfileAdapterForStudent(this, "Password", FinalPassword, R.string.fa_password);
                            recyclerViewProfilePassword.setAdapter(profileAdapter);
                            profileAdapter = new ProfileAdapterForStudent(this, "Email Address", students.get(0).getEmailAddress(), R.string.fa_email);
                            recyclerViewProfileEmailAddress.setAdapter(profileAdapter);
                        } else if (students.get(0).getResult().equals("No Data Found")) {
                            String Message = "No Data Found";
//                            Snackbar.make(view, Message, Snackbar.LENGTH_LONG).show();
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(MyProfileForGuest.this,"Error",Message);

                        } else if (students.get(0).getResult().equals("Something went wrong")) {
                            String Message = "Something went wrong";
//                            Snackbar.make(view, Message, Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            NetWorkCallForProfile();
//                                        }
//                                    }).show();
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(MyProfileForGuest.this,"Error",Message);
                        }
                    } catch (Exception ex) {
                        String Message = "Something went wrong";
                        Snackbar.make(view, Message, Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCallForProfile();
                                    }
                                }).show();
                    }
                    break;
                case 490:
                    students = (List<Student>) data;
                    try {
                        if (students.get(0).getResult().equals("Image Save Successfully")) {
                            Snackbar.make(view, "Image Updated Successfully..", Snackbar.LENGTH_LONG).show();
                        } else if (students.get(0).getResult().equals("Unable To Save User Image")) {
                            Snackbar.make(view, "Fail to update profile..", Snackbar.LENGTH_LONG).show();
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(MyProfileForGuest.this,"Error","Fail to update profile..");
//                        image.setImageResource(R.mipmap.profile);
                        }
                    } catch (Exception ex) {
                        Log.d("Exception", "" + ex);
                    }
                    break;
            }


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForGuest.this,view, "MyProfileForGuest", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void loadImage(ImageView image, String profile) {
        if (profile != null || !profile.equals("")) {
            String imageString = AppUtility.baseUrl + profile;
            Picasso.with(this)
                    .load(imageString).into(image);
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(view,MyProfileForGuest.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForGuest.this,view, "MyProfileForGuest", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void NetworkcallUpdate() {
        try {
            String Msg = "Profile Updated Please Wait";
            CreayeJSonForUpdate();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForGuest.this,view, "MyProfileForGuest", "NetworkcallUpdate", e);
            pc.showCatchException();
        }
    }

    private void CreayeJSonForUpdate() {
        String ImageStringForJsonCreation = getStringImage(bmp);
        Student student = new Student();
        Gson gson = new Gson();
        student.setClassCode(AppUtility.KEY_CLASSCODE);
        student.setStudentCode(AppUtility.KEY_STUDENTCODE);
        student.setStudentProfile(ImageStringForJsonCreation);
        student.setWhoseImage("Student");
        String profilejson = gson.toJson(student);
        JSONForUpdate = "[" + profilejson + "]";
    }

    private String getStringImage(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        } else {
            return "";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                bmp = data.getParcelableExtra("Bitmap");
                image.setImageBitmap(bmp);
                NetworkcallUpdate();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(MyProfileForGuest.this, ImageSelectActivity.class);
        startActivityForResult(intent1, 1);

    }
}
