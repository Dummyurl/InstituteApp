package com.mksmcqapplicationtest.EnglishSpeaking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.MaterialDesignMainActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.LevelListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements ResponseListener {

    private RecyclerView recyclerView;
    LevelListAdapter levelListAdapter;
    String AudioListJSONString;
    List<AudioPlay> audioPlaysListResponse;
    View parentLayout;
    ImageView imgNoInternet;
    BottomNavigationView bottomNavigation;
    static boolean active = false;
    Context context;
    String showcaseviewid = "1";
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            this.context = getApplicationContext();
            parentLayout = findViewById(android.R.id.content);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            imgNoInternet = (ImageView) findViewById(R.id.imgNoInternet);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(context);
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                networkCallForLevel();
            }

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Spoken English"  );
            mToolbar.setContentInsetEndWithActions(0);
            mToolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(mToolbar);
            Advertise();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "HomeActivity", "onCreate", e);
            pc.showCatchException();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtility.IsFirstTimeHome=false;
        Intent intent = new Intent(HomeActivity.this, MaterialDesignMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void GetIMEINumber() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {
                    requestPermission();
                }
            }
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.getDeviceId();
            AppUtility.Mobile_IMEI_CODE = getDeviceIMEI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.READ_PHONE_STATE)) {
//            Snackbar.make(parentLayout, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Snackbar.LENGTH_INDEFINITE).show();
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, AppUtility.PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppUtility.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    AppUtility.Mobile_IMEI_CODE = getDeviceIMEI();
                    CreateJSON();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
//            Toast.makeText(SignUpForGuest.this,deviceUniqueIdentifier,Toast.LENGTH_LONG).show();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }


    private void networkCallForLevel() {
        try {if (AppUtility.KEY_USERNAME.equals("")) {
            Snackbar.make(parentLayout, "Something went wrong", Snackbar.LENGTH_SHORT).show();
        } else {
            GetIMEINumber();
            if(!AppUtility.Mobile_IMEI_CODE.equals("")){
                CreateJSON();}}
//            CreateJSON();
//            DataAccess dataAccess = new DataAccess(this, this);
//            dataAccess.getLevelListData(AudioListJSONString, AppUtility.GET_LEVELS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "HomeActivity", "networkCallForLevel", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            if ((!(AppUtility.KEY_USERNAME == null || AppUtility.KEY_USERNAME.equals(""))) && (!(AppUtility.KEY_PASSWORD == null || AppUtility.KEY_PASSWORD.equals("")))) {
            AudioPlay audioPlayResponse = new AudioPlay();
            Gson gson = new Gson();
            audioPlayResponse.setUserName(AppUtility.KEY_USERNAME);
            audioPlayResponse.setPassword(AppUtility.KEY_PASSWORD);
            audioPlayResponse.setStudentName(AppUtility.StudentName[0]);
                audioPlayResponse.setIMEI(AppUtility.Mobile_IMEI_CODE);
            String classResponseJSON = gson.toJson(audioPlayResponse);
            AudioListJSONString = "[" + classResponseJSON + "]";}
            else{
                final Dialog dialog = new Dialog(HomeActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog);
                TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                txtTitle.setText("Message!!");
                TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                txtMessage.setText("Something went wrong. Please login again");
                final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                dialogButton.setText("OK");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(HomeActivity.this, dialogButton);
                        bounce_button_class.BounceMethod();
                        SharedPreferences setting = getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = setting.edit();
                        editor.putBoolean("Loggedin", false);
                        editor.clear();

                        editor.commit();
                        Intent intent = new Intent(HomeActivity.this, MaterialDesignLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
                Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                CancledialogButton.setText("No");
                CancledialogButton.setVisibility(View.GONE);
                CancledialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "HomeActivity", "CreateJSON", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            imgNoInternet.setVisibility(View.GONE);
            switch (requestCode) {
                case 770:
                    audioPlaysListResponse = (List<AudioPlay>) data;
                    String responseMsg = audioPlaysListResponse.get(0).getResultCode().toString();
                    if (responseMsg.equals("1")) {
                        int levelCount = Integer.parseInt(audioPlaysListResponse.get(0).getLevelCount());
                        AppUtility.arraylist = new String[levelCount];
                        for (int i = 0; i < AppUtility.arraylist.length; i++) {
                            AppUtility.arraylist[i] = "false";
                        }
                        levelListAdapter = new LevelListAdapter(HomeActivity.this, audioPlaysListResponse);
                        recyclerView.setAdapter(levelListAdapter);
                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(HomeActivity.this,"Error",audioPlaysListResponse.get(0).getResult());
//                        Snackbar.make(parentLayout, "Something went wrong Try again later ", Snackbar.LENGTH_LONG).show();
                    }
                    break;

            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "HomeActivity", "OnResponse", ex);
            pc.showCatchException();

        }
    }

    @Override
    public void noResponse(String error) {
        try {
            imgNoInternet.setVisibility(View.VISIBLE);
            VollyResponse vollyResponse = new VollyResponse(parentLayout,HomeActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "HomeActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void Advertise() {
        try {
            if (AppUtility.IsTeacher.equals("G")) {
                //For Advertise
                if (AppUtility.IsAdvertiesVisibleForGuest) {
                    ShowAdvertisement();
                } else if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            } else {
                if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "TabExamActivity", "Advertise", e);
            pc.showCatchException();
        }

    }

    private void ShowAdvertisement() {
        try {
            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
            interstitialAdvertiesClass.showInterstitialAdver();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(HomeActivity.this,parentLayout, "TabExamActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }


}
