package com.mksmcqapplicationtest.TabStructure.TabNotification;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.NotificationListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabShowNotification extends Fragment implements ResponseListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerViewAllData;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall, NotificationJSONString;
    int count1 = 0;
    int j1 = 0;
    NotificationListAdapter adapter;
    Context context;
    View view;
    List<SendNotification> showNotification;
    Toast toast;
    CheckBox chkShowGuestNotification;
    String isnotificationforguest = "0";
    android.support.v7.widget.SearchView searchViewAndroidActionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_show_notification, container, false);
        try {
            this.context = getContext().getApplicationContext();
            setUI();

            recyclerViewAllData.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewAllData.setLayoutManager(layoutManager);
            recyclerViewAllData.setItemAnimator(new DefaultItemAnimator());
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }

            chkShowGuestNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        isnotificationforguest = "1";
                    } else {
                        isnotificationforguest = "0";
                    }
                }
            });
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        recyclerViewAllData.setAdapter(null);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerViewAllData.setAdapter(null);
                    }
                }
            });


            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), view, R.id.adView);
                advertiesClass.ShowAdver();

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        spinnerForClass =  view.findViewById(R.id.spinnerForClass);
        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewAllData =  view.findViewById(R.id.recyclerViewAllData);
        chkShowGuestNotification =  view.findViewById(R.id.chkSendToGuest);
        CustomTextViewBold txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Show_Notification));

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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    public void NetworkCallForNotificaion() {
        try {
            if (AppUtility.IsTeacher.equals("T")) {
                CreateJSONForNotificationForTeacher();
            } else if (AppUtility.IsTeacher.equals("A")) {
                CreateJSONForNotificationForTeacher();
            } else {
                CreateJSONForNotificationForStudGuest();
            }
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.showNotification(NotificationJSONString, AppUtility.POST_SHOW_NOTIFICATION_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "NetworkCallForNotificaion", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForNotificationForTeacher() {
        try {
            Student classResponse = new Student();
            Gson gson = new Gson();
            classResponse.setClassCode(ClassCodeForNetworkcall);
            classResponse.setStudentCode("");
            classResponse.setNotificationStatus("All");
            classResponse.setIsNotificationForGuest(isnotificationforguest);
            classResponse.setUserName(AppUtility.KEY_USERNAME);
            String classResponseJSON = gson.toJson(classResponse);
            NotificationJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "CreateJSONForNotificationForTeacher", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForNotificationForStudGuest() {
        try {
            String isnotificationforguest = "0";
            if (AppUtility.IsTeacher.equals("G")) {
                isnotificationforguest = "1";
            }
            Student classResponse = new Student();
            Gson gson = new Gson();
            classResponse.setClassCode(ClassCodeForNetworkcall);
            classResponse.setStudentCode("");
            classResponse.setNotificationStatus("All");
            classResponse.setUserName(AppUtility.KEY_USERNAME);
            classResponse.setIsNotificationForGuest(isnotificationforguest);
            String classResponseJSON = gson.toJson(classResponse);
            NotificationJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "CreateJSONForNotificationForStudGuest", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

        swipeRefreshLayout.setRefreshing(false);
        recyclerViewAllData.setVisibility(View.VISIBLE);

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
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                                        recyclerViewAllData.setAdapter(null);

                                        if (toast != null) {
                                            toast.cancel();
                                        }
                                        NetworkCallForNotificaion();

                                    } else {
                                        recyclerViewAllData.setAdapter(null);
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });


                        } else if (classResponse.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",classResponse.get(0).getResult().toString());
                        } else if (classResponse.get(0).getResult().equals("Something went wrong")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",classResponse.get(0).getResult().toString());
                        }
                    } else {
                        Snackbar.make(view, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                    }
                    break;

                case 460:
                    showNotification = (List<SendNotification>) data;
                    try {

                        for (int i = 0; i < showNotification.size(); i++) {
                            if (showNotification.get(0).getResultCode().equals("0")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(),"Error","No notification available");
                            } else {
                                recyclerViewAllData.setVisibility(View.VISIBLE);
                                adapter = new NotificationListAdapter(getContext(), showNotification);
                                recyclerViewAllData.setAdapter(adapter);


                            }
                        }
                        if (showNotification.get(0).getResultCode().equals("0")) {
                            if (toast != null) {
                                toast.cancel();
                            }
                        } else {
                            toast = Toast.makeText(context, "Total: " + showNotification.size(), Toast.LENGTH_LONG);
                            toast.show();
                        }

                    } catch (Exception ex) {
                        Log.d("Exception", "" + ex);
                    }
                    break;

            }
        } catch (Exception ex) {
            Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "noResponse", e);
            pc.showCatchException();
        }
    }


    public void CheckForUserType() {
        try {
            NetWorkCallForClass();
            if (AppUtility.IsTeacher.equals("T")) {
                chkShowGuestNotification.setVisibility(View.VISIBLE);
            } else if (AppUtility.IsTeacher.equals("A")) {
                chkShowGuestNotification.setVisibility(View.VISIBLE);
            } else {
                chkShowGuestNotification.setVisibility(View.GONE);
            }
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "CheckForUserType", e);
            pc.showCatchException();
        }
    }

    private void ShowAdvertisement() {
        try {
//            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//            interstitialAdvertiesClass.showInterstitialAdver();

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), view, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), view, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ShowNotificationActivity", "ShowAdvertisement", e);
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
            if (showNotification != null) {
                newText = newText.toLowerCase();
                List<SendNotification> newTestList = new ArrayList<>();
                for (SendNotification data : showNotification) {
                    if (data.getNotificationTitle() != null && data.getNotificationTitle() != null) {
                        String name = data.getNotificationTitle().toLowerCase();
                        String name1 = data.getNotificationTitle().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getNotificationTitle() == null) {
                        String name1 = data.getNotificationTitle().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getNotificationTitle() == null) {
                        String name = data.getNotificationTitle().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                adapter.setFilter(newTestList);
                return false;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabShowNotification", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (showNotification != null) {
                newText = newText.toLowerCase();
                List<SendNotification> newTestList = new ArrayList<>();
                for (SendNotification data : showNotification) {
                    if (data.getNotificationTitle() != null && data.getNotificationTitle() != null) {
                        String name = data.getNotificationTitle().toLowerCase();
                        String name1 = data.getNotificationTitle().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getNotificationTitle() == null) {
                        String name1 = data.getNotificationTitle().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getNotificationTitle() == null) {
                        String name = data.getNotificationTitle().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                adapter.setFilter(newTestList);
                return true;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabShowNotification", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

