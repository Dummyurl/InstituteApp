package com.mksmcqapplicationtest.EnglishSpeaking;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.MediaPlayer.MusicPlayerActivity;
import com.mksmcqapplicationtest.MyBounceInterpolator;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.DetailAudioUnlockListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;


public class UnlockAudioActivity extends AppCompatActivity implements View.OnClickListener {


    View parentLayout;
    String[] arraylist = {"Play English Speking Audio", "Test", "Given Test"};
    DetailAudioUnlockListAdapter audioUnlockListAdapter;

    TextView btnPlayAudio, btntest, btnGivenTest;
    RelativeLayout RL1, RL2, RL3;
    Bundle bundle;
    String AudioPath, AudioName, AudioFormula, AudioDescription, AudioCode, Stage;
    BottomNavigationView bottomNavigation;
    String showcaseviewid = "1";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_audio);
        try {
            parentLayout = findViewById(android.R.id.content);
            context = this.getApplicationContext();
            bundle = getIntent().getExtras();
            if (bundle != null) {
                AudioPath = bundle.getString("AudioPath");
                AudioName = bundle.getString("AudioName");
                AudioFormula = bundle.getString("AudioFormula");
                AudioDescription = bundle.getString("AudioDescription");
                AudioCode = bundle.getString("AudioCode");
                Stage = bundle.getString("Stage");
            }


            btnPlayAudio = (TextView) findViewById(R.id.btnPlayAudio);
            btnPlayAudio.setOnClickListener(this);
            btntest = (TextView) findViewById(R.id.btntest);
            btntest.setOnClickListener(this);
            btnGivenTest = (TextView) findViewById(R.id.btnGivenTest);
            btnGivenTest.setOnClickListener(this);


            RL1 = (RelativeLayout) findViewById(R.id.RL1);
            RL1.setOnClickListener(this);
            RL2 = (RelativeLayout) findViewById(R.id.RL2);
            RL2.setOnClickListener(this);
            RL3 = (RelativeLayout) findViewById(R.id.RL3);
            RL3.setOnClickListener(this);
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" " + AudioName);
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
            Advertise();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(UnlockAudioActivity.this,parentLayout, "UnlockAudioActivity", "onCreate", e);
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
            PrintCatchException pc = new PrintCatchException(UnlockAudioActivity.this,parentLayout, "TabExamActivity", "Advertise", e);
            pc.showCatchException();
        }

    }

    private void ShowAdvertisement() {
        try {
            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
            interstitialAdvertiesClass.showInterstitialAdver();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(UnlockAudioActivity.this,parentLayout, "TabExamActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.RL1:
                    RelativeBounce(RL1);
                    Intent newIntent = new Intent(UnlockAudioActivity.this, MusicPlayerActivity.class);
                    newIntent.putExtra("AudioPath", AudioPath);
                    newIntent.putExtra("AudioName", AudioName);
                    newIntent.putExtra("AudioFormula", AudioFormula);
                    newIntent.putExtra("AudioDescription", AudioDescription);
                    startActivity(newIntent);
                    break;
                case R.id.RL2:
                    RelativeBounce(RL2);
                    Intent intent1 = new Intent(UnlockAudioActivity.this, ExamListFragemtActivity.class);
                    intent1.putExtra("AudioCode", AudioCode);
                    intent1.putExtra("Stage", Stage);
                    startActivity(intent1);
                    break;
                case R.id.RL3:
                    RelativeBounce(RL3);
                    Intent intent2 = new Intent(UnlockAudioActivity.this, GivenTestListSearchStudentWise.class);
                    intent2.putExtra("AudioCode", AudioCode);
                    startActivity(intent2);
                    break;
                case R.id.btnPlayAudio:
                    RelativeBounce(RL1);
                    Intent newIntent1 = new Intent(UnlockAudioActivity.this, MusicPlayerActivity.class);
                    newIntent1.putExtra("AudioPath", AudioPath);
                    newIntent1.putExtra("AudioName", AudioName);
                    newIntent1.putExtra("AudioFormula", AudioFormula);
                    newIntent1.putExtra("AudioDescription", AudioDescription);
                    startActivity(newIntent1);
                    break;
                case R.id.btntest:
                    RelativeBounce(RL2);
                    Intent intent6 = new Intent(UnlockAudioActivity.this, ExamListFragemtActivity.class);
                    intent6.putExtra("AudioCode", AudioCode);
                    intent6.putExtra("Stage", Stage);
                    startActivity(intent6);
                    break;
                case R.id.btnGivenTest:
                    RelativeBounce(RL3);
                    Intent intent = new Intent(UnlockAudioActivity.this, GivenTestListSearchStudentWise.class);
                    intent.putExtra("AudioCode", AudioCode);
                    startActivity(intent);
                    break;
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(UnlockAudioActivity.this,parentLayout, "UnlockAudioActivity", "onClick", e);
            pc.showCatchException();

        }
    }


    public void RelativeBounce(RelativeLayout RL1) {
        try {
            final android.view.animation.Animation myAnim7 = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator interpolator7 = new MyBounceInterpolator(0.1, 20);
            myAnim7.setInterpolator(interpolator7);
            RL1.startAnimation(myAnim7);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(UnlockAudioActivity.this,parentLayout, "UnlockAudioActivity", "RelativeBounce", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtility.IsFirstTimeHome = false;
        AppUtility.IsFirstAudio = false;
    }
}

