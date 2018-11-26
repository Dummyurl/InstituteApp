package com.mksmcqapplicationtest.MediaPlayer;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class MediaPlayActivity extends AppCompatActivity implements ResponseListener {

    String AudioListJSONString;
    List<AudioPlay> audioPlaysListResponse;
    View parentLayout;
    RecyclerView recyclerViewOfMediaName;
    MediaPlayerAdapter mediaPlayerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);
        try {
            parentLayout = findViewById(android.R.id.content);
            recyclerViewOfMediaName = (RecyclerView) findViewById(R.id.recyclerViewOfMediaName);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerViewOfMediaName.setLayoutManager(layoutManager);
            recyclerViewOfMediaName.setItemAnimator(new DefaultItemAnimator());
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                }
            });

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Spoken English");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MediaPlayActivity.this,parentLayout, "MediaPlayActivity", "OnCreate", e);
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

    private void CreateJSON() {
        try {
            AudioPlay audioPlayResponse = new AudioPlay();
            Gson gson = new Gson();
            audioPlayResponse.setTeacherCode("1");
            audioPlayResponse.setWhichList("all");
            String classResponseJSON = gson.toJson(audioPlayResponse);
            AudioListJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MediaPlayActivity.this,parentLayout, "MediaPlayActivity", "CreateJSON", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 510:
                    audioPlaysListResponse = (List<AudioPlay>) data;
                    String responseMsg = audioPlaysListResponse.get(0).getResult().toString();
                    if (responseMsg.equals("Sucessfull")) {
                        AppUtility.AudioFilesURL = audioPlaysListResponse.get(0).getDirectoryPath().toString();
                        mediaPlayerAdapter = new MediaPlayerAdapter(MediaPlayActivity.this, audioPlaysListResponse);
                        recyclerViewOfMediaName.setAdapter(mediaPlayerAdapter);

                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(MediaPlayActivity.this,"Error",responseMsg);
//                        Snackbar.make(parentLayout, "" + responseMsg, Snackbar.LENGTH_LONG).show();
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
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(parentLayout,MediaPlayActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MediaPlayActivity.this,parentLayout, "MediaPlayActivity", "noResponse", e);
            pc.showCatchException();

        }
    }
}
