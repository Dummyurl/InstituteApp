package com.mksmcqapplicationtest.EnglishSpeaking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.beans.User;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.TestListAdapterEnglishSpeaking;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class ExamListFragemtActivity extends AppCompatActivity implements ResponseListener, android.support.v7.widget.SearchView.OnQueryTextListener {
    private List<Test> tests;
    private RecyclerView recyclerView;
    CoordinatorLayout coordinatorLayout;
    TestListAdapterEnglishSpeaking adapter;
    String TestListJSONString;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;
    String AudioCode, Stage;
    Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_list_fragment);
        try {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            bundle = getIntent().getExtras();
            if (bundle != null) {
                AudioCode = bundle.getString("AudioCode");
                Stage = bundle.getString("Stage");
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(ExamListFragemtActivity.this);
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                displayExamList();
            }

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" MCQ Test");
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    displayExamList();
                }
            });

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

        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(ExamListFragemtActivity.this,coordinatorLayout,"ExamListFragemtActivity","onCreate",e);
            pc.showCatchException();
        }
    }

    private void displayExamList() {
        try {
            recyclerView.setAdapter(null);
            CreateJsonForGetTestList();
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(ExamListFragemtActivity.this,coordinatorLayout,"ExamListFragemtActivity","displayExamList",e);
            pc.showCatchException();
        }
    }

    public void CreateJsonForGetTestList() {
        try {
            User user = new User();
            Gson gson = new Gson();
            user.setUsername(AppUtility.KEY_USERNAME);
            user.setAudioCode(AudioCode);
            String jso = gson.toJson(user);
            TestListJSONString = "[" + jso + "]";
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(ExamListFragemtActivity.this,coordinatorLayout,"ExamListFragemtActivity","CreateJsonForGetTestList",e);
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
                case 790:
                    tests = (List<Test>) data;
                    try {

                        if (tests.get(0).getResultCode().equals("1")) {
                            if (searchViewAndroidActionBar != null) {
                                searchViewAndroidActionBar.setOnQueryTextListener(this);
                            }
                            adapter = new TestListAdapterEnglishSpeaking(this, tests, Stage, AudioCode);
                            recyclerView.setAdapter(adapter);
                            toast = Toast.makeText(this, "Total: " + String.valueOf(tests.size()), Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(ExamListFragemtActivity.this,"Error","No test are available..");
//                            Snackbar snackbar = Snackbar.make(coordinatorLayout, "No test are available..", Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            displayExamList();
//                                        }
//                                    });
//                            snackbar.show();
                        }
                    } catch (Exception ex) {
                        Snackbar.make(coordinatorLayout, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception ex) {
            Snackbar.make(coordinatorLayout, "Something went wrong Try again later", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(coordinatorLayout,ExamListFragemtActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(ExamListFragemtActivity.this,coordinatorLayout,"ExamListFragemtActivity","noResponse",e);
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
        List<Test> newTestList = new ArrayList<>();
        for (Test test : tests) {
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
        List<Test> newTestList = new ArrayList<>();
        for (Test test : tests) {
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

    private void ShowAdvertisement() {
        try {
            if (AppUtility.IsAdvertiesVisible) {
                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(this);
                interstitialAdvertiesClass.showInterstitialAdver();

                AdvertiesClass advertiesClass = new AdvertiesClass(this, this, R.id.adView);
                advertiesClass.ShowAdver();

                if (AppUtility.AboveIsAdvertiesVisible) {
                    AdvertiesClass advertiesClass1 = new AdvertiesClass(this, this, R.id.adView1);
                    advertiesClass1.ShowAdver();
                }
            }
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(ExamListFragemtActivity.this,coordinatorLayout,"ExamListFragemtActivity","ShowAdvertisement",e);
            pc.showCatchException();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtility.IsFirstTimeHome=false;
        AppUtility.IsFirstAudio=false;
    }
}

