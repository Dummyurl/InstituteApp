package com.mksmcqapplicationtest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.FeesAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class ShowDetailFees extends AppCompatActivity {
    String studentCode, classCode, FeesArrayString, StudentName, EducationalYear;
    Bundle bundle;
    RecyclerView recyclerViewFeesDetail;
    View parentLayout;
    FeesAdapter feesAdapter;
    TextView txtStudName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_fees);
        try {
            bundle = getIntent().getExtras();
            if (bundle != null) {
                studentCode = bundle.getString("StudentCode");
                classCode = bundle.getString("ClassCode");
                StudentName = bundle.getString("StudentName");
                EducationalYear = bundle.getString("EducationalYear");
            }

            txtStudName = (TextView) findViewById(R.id.txtStudName);
            txtStudName.setText(StudentName);
            recyclerViewFeesDetail = (RecyclerView) findViewById(R.id.recyclerViewFeesDetail);
            parentLayout = findViewById(android.R.id.content);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerViewFeesDetail.setLayoutManager(layoutManager);
            recyclerViewFeesDetail.setItemAnimator(new DefaultItemAnimator());



            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Details Of Fees");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ShowDetailFees.this,parentLayout, "ShowDetailFees", "OnCreate", e);
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
