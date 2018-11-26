package com.mksmcqapplicationtest.EnglishSpeaking;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AudioPlay;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ItemListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class EnglishSpeakingActivity extends AppCompatActivity implements ResponseListener {

    private RecyclerView recyclerView;
    ItemListAdapter itemListAdapter;
    String AudioListJSONString, LevelCount;
    List<AudioPlay> audioPlaysListResponse;
    View parentLayout;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.englishspeakingactivity);
        try {
            bundle = getIntent().getExtras();
            if (bundle != null) {
                LevelCount = bundle.getString("LevelCount");
            }

            parentLayout = findViewById(android.R.id.content);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(EnglishSpeakingActivity.this);
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                networkCallForAudio();
            }

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Level " + LevelCount);
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(EnglishSpeakingActivity.this,parentLayout, "MainActivity", "onCreate", e);
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

    private void networkCallForAudio() {
        try {
            CreateJSON();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(EnglishSpeakingActivity.this,parentLayout, "MainActivity", "networkCallForAudio", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            AudioPlay audioPlayResponse = new AudioPlay();
            Gson gson = new Gson();
            audioPlayResponse.setUserName(AppUtility.KEY_USERNAME);
            audioPlayResponse.setLevelCount(LevelCount);
            audioPlayResponse.setWhichList("all");
            String classResponseJSON = gson.toJson(audioPlayResponse);
            AudioListJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(EnglishSpeakingActivity.this,parentLayout, "MainActivity", "CreateJSON", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 780:
                    audioPlaysListResponse = (List<AudioPlay>) data;
                    String responseMsg = audioPlaysListResponse.get(0).getResult().toString();
                    if (responseMsg.equals("Sucessfull")) {
                        AppUtility.AudioFilesURL = audioPlaysListResponse.get(0).getDirectoryPath().toString();
                        itemListAdapter = new ItemListAdapter(EnglishSpeakingActivity.this, audioPlaysListResponse);
                        recyclerView.setAdapter(itemListAdapter);
                    } else {
//                        Snackbar.make(parentLayout, "" + responseMsg, Snackbar.LENGTH_LONG).show();
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(EnglishSpeakingActivity.this,"Error",responseMsg);
                    }
                    break;

            }
        } catch (Exception ex) {
            Snackbar.make(parentLayout, "Something went wrong Try again later", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,EnglishSpeakingActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(EnglishSpeakingActivity.this,parentLayout, "MainActivity", "noResponse", e);
            pc.showCatchException();
        }
    }



}
