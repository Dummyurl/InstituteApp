package com.mksmcqapplicationtest.TabStructure.Exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.GivenTestListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabGivenTest extends Fragment implements ResponseListener, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    View coordinatorLayout;
    GivenTestListAdapter adapter;
    String JSONArrayStringForGivenTestList;
    List<User> users;
    List<GivenTestListResponse> givenTestListResponses;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString;
    int count1 = 0;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    CustomTextViewBold txtactionbartitle;
    LinearLayout layoutspinnerofclass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            coordinatorLayout = inflater.inflate(R.layout.tab_given_test, container, false);

            setUI();

//            recyclerView = (RecyclerView) coordinatorLayout.findViewById(R.id.recyclerView);
//            spinnerForClass = (Spinner) coordinatorLayout.findViewById(R.id.spinnerForClass);
//            swipeRefreshLayout = (SwipeRefreshLayout) coordinatorLayout.findViewById(R.id.swipeRefreshLayout);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

//            if (AppUtility.IsAdvertiesVisible) {
//                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), coordinatorLayout, R.id.adView);
//                advertiesClass.ShowAdver();
//            }

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {


                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return coordinatorLayout;
    }

    private void setUI() {
        layoutspinnerofclass = coordinatorLayout.findViewById(R.id.layoutspinnerofclass);
        recyclerView = coordinatorLayout.findViewById(R.id.recyclerView);
        spinnerForClass = layoutspinnerofclass.findViewById(R.id.spinnerForClass);
        swipeRefreshLayout = coordinatorLayout.findViewById(R.id.swipeRefreshLayout);

        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.given_mcq_exam_fragment_title));

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


    public void CheckForUserType() {
        try {
            NetWorkCallForClass();
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
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "CheckForUserType", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "ShowAdvertisement", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    private void displayExamList() {
        try {
            recyclerView.setAdapter(null);
            if (!(AppUtility.KEY_CLASSCODE.equals("0") || AppUtility.KEY_CLASSCODE == null)) {
                //TODO Testing Remaining
                CreateJsonForGivenTestList();
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.GetGivenTestListFromServer(AppUtility.GIVEN_TEST_LIST_URL, JSONArrayStringForGivenTestList);
            } else {
                Snackbar.make(coordinatorLayout, "Select Class Name ", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "displayExamList", e);
            pc.showCatchException();
        }
    }

    private void CreateJsonForGivenTestList() {
        try {
            Gson gson = new Gson();
            User user = new User();
            user.setUsername(AppUtility.KEY_USERNAME);
            user.setPassword(AppUtility.KEY_PASSWORD);
            user.setClassCode(AppUtility.KEY_CLASSCODE);
            String jsonString = gson.toJson(user);
            JSONArrayStringForGivenTestList = "[" + jsonString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "CreateJsonForGivenTestList", e);
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
                case 260:
                    givenTestListResponses = (List<GivenTestListResponse>) data;
                    String Result = givenTestListResponses.get(0).getReturnValue();
                    String ResultCode = givenTestListResponses.get(0).getResultCode();
                    if (ResultCode.equals("1")) {
                        adapter = new GivenTestListAdapter(getContext(), givenTestListResponses, AppUtility.KEY_USERNAME);
                        recyclerView.setAdapter(adapter);
                        toast = Toast.makeText(getContext(), "Total: " + String.valueOf(givenTestListResponses.size()), Toast.LENGTH_LONG);
                        toast.show();
                    } else if (ResultCode.equals("0")) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "No test submitted yet...plz attend test first", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AppUtility.IsFirstTimeHome = false;
                                        Intent intent = new Intent(getContext(), MaterialDesignMainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });

                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "" + Result, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    break;


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
                                    String spinner1 = parent.getItemAtPosition(position).toString();
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
                                    // TODO Auto-generated method stub
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
            }

        } catch (Exception ex) {
            Snackbar.make(coordinatorLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(coordinatorLayout, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "SubmittedExamListFragmentActivity", "CreateJsonForGivenTestList", e);
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
            if (givenTestListResponses != null) {
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

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "TabGivenTest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (givenTestListResponses != null) {
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

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), coordinatorLayout, "TabGivenTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }

}

