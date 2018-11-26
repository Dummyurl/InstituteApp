package com.mksmcqapplicationtest.ui.fragments;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class DataFragmentActivity extends AppCompatActivity  {
    private RelativeLayout mViewPager;
    TabLayout tabLayout;
    String JsonArrayString;
    Bundle bundle;
    String DataCode, DataName;
    List<Data> datas;
    int count = 0;
    Fragment fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    View view;
    String UploadAnswerSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fragment);
        try {
            view = findViewById(android.R.id.content);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                DataCode = bundle.getString("DataCode");
                DataName = bundle.getString("DataName");
                UploadAnswerSheet = bundle.getString("UploadAnswerSheet");

            }
            mViewPager = (RelativeLayout) findViewById(R.id.container);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setVisibility(View.GONE);
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" " + DataName);
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(DataFragmentActivity.this,view, "DataFragmentActivity", "OnCreate", e);
            pc.showCatchException();
        }
    }



}
