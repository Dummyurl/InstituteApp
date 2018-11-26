package com.mksmcqapplicationtest.TabStructure.Exam;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
//import android.widget.Spinner;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.MaterialDesignMainActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.TestListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabMcqTest extends Fragment implements ResponseListener, SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    private List<Test> tests;
    private RecyclerView recyclerView;
    View coordinatorLayout;
    TestListAdapter adapter;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, TestListJSONString;
    int count1;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;
    TextView txtmovingText;
    CustomTextViewBold txtactionbartitle;
LinearLayout layoutspinnerofclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        coordinatorLayout = inflater.inflate(R.layout.tab_mcq_test_new, container, false);

        try {
            setUI();

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

//            if (AppUtility.IsAdvertiesVisible) {
//                AdvertiesClass advertiesClass = new AdvertiesClass(getContext(), getActivity(), R.id.adView);
//                advertiesClass.ShowAdver();
//
//            }

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        displayExamList();
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return coordinatorLayout;
    }

    private void setUI() {
        layoutspinnerofclass=coordinatorLayout.findViewById(R.id.layoutspinnerofclass);
        txtmovingText = coordinatorLayout.findViewById(R.id.txtmovingText);
        recyclerView =  coordinatorLayout.findViewById(R.id.recyclerView);
        spinnerForClass = layoutspinnerofclass.findViewById(R.id.spinnerForClass);
        swipeRefreshLayout = coordinatorLayout.findViewById(R.id.swipeRefreshLayout);

        txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.mcq_exam_fragment_title));

        ImageButton imageadd = (ImageButton) getActivity().findViewById(R.id.imageadd);
        imageadd.setVisibility(View.GONE);

        ImageButton imageButtonBackPress = (ImageButton) getActivity().findViewById(R.id.imagviewbackpress);
        imageButtonBackPress.setVisibility(View.VISIBLE);
        imageButtonBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    private void displayExamList() {
        try {
            if (!(AppUtility.KEY_CLASSCODE.equals("0") || AppUtility.KEY_CLASSCODE == null)) {
                recyclerView.setAdapter(null);
                TestListJSONString = new CreateJsonFunction().CreateJsonForGetTestList(AppUtility.KEY_CLASSCODE, "active");
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getAllTestList(AppUtility.GET_ALL_TEST_LIST, TestListJSONString);

            } else {
                Snackbar.make(coordinatorLayout, "Select Class Name ", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "displayExamList", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCallForClass() {
        try {
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                ClassNameJSONString = new CreateJsonFunction().CreateJSONForClass();
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getClassNameList(ClassNameJSONString, AppUtility.GET_CLASS_NAME_LIST);
            } else {
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getListClass();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onResponse(Object data) {
        swipeRefreshLayout.setRefreshing(false);
//        try {
//            tests = (List<Test>) data;
//            if (tests.get(0).getResultCode().equals("1")) {
//                searchViewAndroidActionBar.setOnQueryTextListener(this);
//                adapter = new TestListAdapter(this, tests);
//                recyclerView.setAdapter(adapter);
//                toast = Toast.makeText(this, "Total: " + String.valueOf(tests.size()), Toast.LENGTH_LONG);
//                toast.show();
//            } else if (tests.get(0).getResultCode().equals("0")) {
//                Snackbar snackbar = Snackbar.make(coordinatorLayout, "No test are available..", Snackbar.LENGTH_LONG)
//                        .setAction("Retry", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                displayExamList();
//                            }
//                        });
//                snackbar.show();
//            } else {
//                Snackbar.make(coordinatorLayout, "Something went wrong ", Snackbar.LENGTH_SHORT).show();
//            }
//        } catch (Exception ex) {
//            Snackbar.make(coordinatorLayout, "" + ex, Snackbar.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 170:
                    classResponse = (List<Class>) data;
                    if (classResponse != null) {
                        if (classResponse.get(0).getResult().equals("Suessfull")) {

                            AppUtility.CLASS_RESPONSE = classResponse;
                            UserNameDropDownForClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j1 = 0;
                            UserNameDropDownForClass[j1] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownForClass[j1] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }
                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForClass);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForClass.setAdapter(Select_Category_Adapter);
                            spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();
                                        recyclerView.setAdapter(null);
                                        if (toast != null) {
                                            toast.cancel();
                                        }
                                        displayExamList();
                                    } else {
                                        recyclerView.setAdapter(null);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else if (classResponse.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                        } else if (classResponse.get(0).getResult().equals("Something went wrong")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCallForClass();
                                    }
                                }).show();
                    }
                    break;

                case 670:
                    tests = (List<Test>) data;
                    try {

                        if (tests.get(0).getResultCode().equals("1")) {
                            adapter = new TestListAdapter(getContext(), tests);
                            recyclerView.setAdapter(adapter);
                            toast = Toast.makeText(getContext(), "Total: " + String.valueOf(tests.size()), Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", "No test are available..");
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
            VollyResponse vollyResponse = new VollyResponse(coordinatorLayout, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "noResponse", e);
            pc.showCatchException();
        }
    }


    public void CheckForUserType() {
        try {
            NetWorkCallForClass();
            if (AppUtility.IsTeacher.equals("G")) {
                //For Moving Text
                txtmovingText.setVisibility(View.VISIBLE);
                Animation animationToLeft = new TranslateAnimation(1000, 0, 0, 0);
                animationToLeft.setDuration(12000);
                animationToLeft.setRepeatMode(Animation.RESTART);
                animationToLeft.setRepeatCount(Animation.INFINITE);
                txtmovingText.setAnimation(animationToLeft);
                txtmovingText.setText(R.string.MoveTextGuestSide);

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
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "CheckForUserType", e);
            pc.showCatchException();
        }
    }

    private void ShowAdvertisement() {
        try {
//            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//            interstitialAdvertiesClass.showInterstitialAdver();

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), coordinatorLayout, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), coordinatorLayout, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "ExamListFragemtActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onQueryTextSubmit(String newText) {
        try {
            if (tests != null) {
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
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "TabMcqTest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (tests != null) {
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
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "TabMcqTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }

}


