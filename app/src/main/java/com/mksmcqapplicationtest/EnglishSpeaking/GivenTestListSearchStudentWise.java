package com.mksmcqapplicationtest.EnglishSpeaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AdvertiesClass;
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.beans.User;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.GivenTestListAdapterEnglishSpeaking;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;


public class GivenTestListSearchStudentWise extends AppCompatActivity implements ResponseListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    View coordinatorLayout;
    GivenTestListAdapterEnglishSpeaking adapter;
    String JSONArrayStringForGivenTestList, StudentNameArrayString;
    List<GivenTestListResponse> givenTestListResponses;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;
    Bundle bundle;
    String AudioCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.given_exam_list_fragment_serch_studwise);
        try {
            bundle = getIntent().getExtras();
            if (bundle != null) {
                AudioCode = bundle.getString("AudioCode");
            }
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            coordinatorLayout = findViewById(android.R.id.content);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(GivenTestListSearchStudentWise.this);
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                displayExamList();
            }
            if (AppUtility.IsTeacher.equals("G")) {
                if (AppUtility.IsAdvertiesVisibleForGuest) {
                    AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                    advertiesClass.ShowAdver();
                    AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                    advertiesClass1.ShowAdver();
                } else if (AppUtility.IsAdvertiesVisible) {
                    AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                    advertiesClass.ShowAdver();
                    AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                    advertiesClass1.ShowAdver();
                }
            } else {
                if (AppUtility.IsAdvertiesVisible) {
                    AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                    advertiesClass.ShowAdver();
                    AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                    advertiesClass1.ShowAdver();
                }
            }

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Given Test");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    displayExamList();

                }
            });




        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(GivenTestListSearchStudentWise.this,coordinatorLayout,"GivenTestListSearchStudentWise","onCreate",e);
            pc.showCatchException();
        }
    }

    private void displayExamList() {
        try {
            recyclerView.setAdapter(null);
            CreateJsonForGivenTestList();
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(GivenTestListSearchStudentWise.this,coordinatorLayout,"GivenTestListSearchStudentWise","displayExamList",e);
            pc.showCatchException();
        }
    }


    private void CreateJsonForGivenTestList() {
        try {
            Gson gson = new Gson();
            User user = new User();
            user.setUsername(AppUtility.KEY_USERNAME);
            user.setAudioCode(AudioCode);
            String jsonString = gson.toJson(user);
            JSONArrayStringForGivenTestList = "[" + jsonString + "]";
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(GivenTestListSearchStudentWise.this,coordinatorLayout,"GivenTestListSearchStudentWise","CreateJsonForGivenTestList",e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 800:
                    givenTestListResponses = (List<GivenTestListResponse>) data;
                    String Result = givenTestListResponses.get(0).getReturnValue();
                    String ResultCode = givenTestListResponses.get(0).getResultCode();
                    if (ResultCode.equals("1")) {
                        if (searchViewAndroidActionBar != null) {
                            searchViewAndroidActionBar.setOnQueryTextListener(this);
                        }
                        adapter = new GivenTestListAdapterEnglishSpeaking(this, givenTestListResponses, AppUtility.KEY_USERNAME);
                        recyclerView.setAdapter(adapter);
                        toast = Toast.makeText(this, "Total: " + String.valueOf(givenTestListResponses.size()), Toast.LENGTH_LONG);
                        toast.show();
                    } else if (ResultCode.equals("0")) {
//                        Snackbar snackbar = Snackbar
//                                .make(coordinatorLayout, "No Data found", Snackbar.LENGTH_LONG);
//                        snackbar.show();
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(GivenTestListSearchStudentWise.this,"Error","No Data found");
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "" + Result, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    break;


            }

        } catch (Exception ex) {
            Snackbar.make(coordinatorLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(coordinatorLayout,GivenTestListSearchStudentWise.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(GivenTestListSearchStudentWise.this,coordinatorLayout,"GivenTestListSearchStudentWise","noResponse",e);
            pc.showCatchException();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem serchViewItem = menu.findItem(R.id.search);
        searchViewAndroidActionBar = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(serchViewItem);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String newText) {
        newText = newText.toLowerCase();
        List<GivenTestListResponse> newTestList = new ArrayList<>();
        for (GivenTestListResponse test : givenTestListResponses) {
            if (test.getClassName() != null && test.getTestName() != null) {
                String name = test.getClassName().toLowerCase();
                String name1 = test.getTestName().toLowerCase();

                if (name.contains(newText) || name1.contains(newText)) {
                    newTestList.add(test);
                }

            } else if (test.getClassName() == null) {
                String name1 = test.getTestName().toLowerCase();

                if (name1.contains(newText)) {
                    newTestList.add(test);
                }
            } else if (test.getTestName() == null) {
                String name = test.getClassName().toLowerCase();

                if (name.contains(newText)) {
                    newTestList.add(test);
                }
            } else {
                return false;
            }
        }
        adapter.setFilter(newTestList);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<GivenTestListResponse> newTestList = new ArrayList<>();
        for (GivenTestListResponse test : givenTestListResponses) {
            if (test.getClassName() != null && test.getTestName() != null) {
                String name = test.getClassName().toLowerCase();
                String name1 = test.getTestName().toLowerCase();

                if (name.contains(newText) || name1.contains(newText)) {
                    newTestList.add(test);
                }

            } else if (test.getClassName() == null) {
                String name1 = test.getTestName().toLowerCase();

                if (name1.contains(newText)) {
                    newTestList.add(test);
                }
            } else if (test.getTestName() == null) {
                String name = test.getClassName().toLowerCase();

                if (name.contains(newText)) {
                    newTestList.add(test);
                }
            } else {
                return false;
            }
        }
        adapter.setFilter(newTestList);
        return true;
    }



}
