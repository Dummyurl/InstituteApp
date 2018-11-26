package com.mksmcqapplicationtest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.Guest.SignUpForGuest;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.mksmcqapplicationtest.util.AppUtility;

import java.util.List;

public class LauncherActivity extends AppCompatActivity  {
    ImageView imageView;
    View parentLayout;
    public static final String PREFS_NAME = "MyPrefsFile";
    Context context;
    private ThreeBounce mThreeBounceDrawable;
    Dialog dialog;
    Animation uptodown, downtoup, zoomin, zoomout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        try {
            this.context = getApplicationContext();
            imageView = (ImageView) findViewById(R.id.image);
            zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
            zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
            imageView.setAnimation(zoomout);
            imageView.setAnimation(zoomin);
            parentLayout = findViewById(android.R.id.content);
            TextView textView = (TextView) findViewById(R.id.text);
            mThreeBounceDrawable = new ThreeBounce();
            mThreeBounceDrawable.setBounds(0, 0, 100, 100);
            mThreeBounceDrawable.setColor(getResources().getColor(R.color.colorPrimary));
            textView.setCompoundDrawables(null, null, mThreeBounceDrawable, null);
            textView.setVisibility(View.GONE);
            zoomin.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    imageView.startAnimation(zoomout);
                }
            });

            zoomout.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    imageView.startAnimation(zoomin);

                }
            });
            LauncherHandler();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(LauncherActivity.this,parentLayout, "LauncherActivity", "OnCreate", e);
            pc.showCatchException();
        }
    }


    private void LauncherHandler() {
        try {
                Intent intent = new Intent(LauncherActivity.this, MaterialDesignLoginActivity.class);
                startActivity(intent);
                finish();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(LauncherActivity.this,parentLayout, "LauncherActivity", "LauncherHandler", e);
            pc.showCatchException();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        mThreeBounceDrawable.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mThreeBounceDrawable.stop();
    }



    @Override
    protected void onStart() {
        super.onStart();

    }



}
