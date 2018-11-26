package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;


public class AddTheoryTestActivity extends AppCompatActivity implements  View.OnClickListener {

    Spinner spinnerForClass;
    List<Class> classResponse;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall = "0", SubjectListJSONString;
    int count1 = 0;
    int j1 = 0;
    View parentLayout;
    String TheoryTestName, TheoryTestAddJSONString, ClassDataJSONString;
    Toast toast;
    EditText edtTheoryTestName;
    Button btnAddTheoryTest;
//            , btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theory_test);
        try {
            parentLayout = findViewById(android.R.id.content);
            spinnerForClass = (Spinner) findViewById(R.id.spinnerForClass);
            edtTheoryTestName = (EditText) findViewById(R.id.edtTheoryTestName);
            btnAddTheoryTest = (Button) findViewById(R.id.btnAddTheoryTest);
//            btnClear = (Button) findViewById(R.id.btnClear);
            btnAddTheoryTest.setOnClickListener(this);
//            btnClear.setOnClickListener(this);

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Add Theory Test");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AddTheoryTestActivity.this,parentLayout, "AddTheoryTestActivity", "OnCreate", e);
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


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnAddTheoryTest:
                    if (!ClassCodeForNetworkcall.equals("0")) {
                        TheoryTestName = edtTheoryTestName.getText().toString().trim();
                        if (TheoryTestName.equals("")) {
                            Snackbar.make(parentLayout, "Plese Enter Theory Test Name", Snackbar.LENGTH_LONG).show();
                        } else {
                        }
                    } else {
                        Snackbar.make(parentLayout, "Plese Select Class", Snackbar.LENGTH_LONG).show();
                    }
                    break;

//                case R.id.btnClear:
//
//                    edtTheoryTestName.setText("");
//                    spinnerForClass.setSelection(0);
//                    break;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AddTheoryTestActivity.this,parentLayout, "AddTheoryTestActivity", "onClick", e);
            pc.showCatchException();
        }
    }
}
