package com.mksmcqapplicationtest.Guest;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;


import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.YoutubeFragment;
import com.mksmcqapplicationtest.beans.AdvertiesClass;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.util.AppUtility;

public class YoutubeVideoPlayerActivity extends AppCompatActivity {
    public static final String API_KEY = "MKS";
    String VIDEO_ID, VideoName;
    Bundle bundle;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_player);

        try {
            view = findViewById(android.R.id.content);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                AppUtility.VIDEO_ID = bundle.getString("VideoID");
                VideoName = bundle.getString("VideoName");
            }

        if (AppUtility.IsTeacher.equals("G")) {
//            For Advertise
            if (AppUtility.IsAdvertiesVisibleForGuest) {
                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
                interstitialAdvertiesClass.showInterstitialAdver();

                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
            } else if (AppUtility.IsAdvertiesVisible) {
                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
                interstitialAdvertiesClass.showInterstitialAdver();

                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
            }
        } else {
            if (AppUtility.IsAdvertiesVisible) {
                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
                interstitialAdvertiesClass.showInterstitialAdver();

                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();
            }
        }
            YoutubeFragment fragment = new YoutubeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.main, fragment)
                    .commit();

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" " + VideoName);
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(YoutubeVideoPlayerActivity.this,view, "YoutubeVideoPlayerActivity", "OnCreate", e);
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


}

