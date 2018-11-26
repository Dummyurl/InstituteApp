package com.mksmcqapplicationtest.TabStructure.TabNotification;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ClearNotificationAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class TabClearNotification extends Fragment implements View.OnClickListener, ResponseListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerViewAllData;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall, NotificationJSONString;
    int count1 = 0;
    int j1 = 0;
    ClearNotificationAdapter adapter;
    Context context;
    View view;
    List<SendNotification> showNotification;
    Toast toast;
    CustomButton btnDeleteNotification;
    CheckBox chkSelectAll, chkSentToGuest;
    String isnotificationforguest = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_clear_notification, container, false);
        try {
            this.context = getContext().getApplicationContext();
          setUI();
            recyclerViewAllData.setVisibility(GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewAllData.setLayoutManager(layoutManager);
            recyclerViewAllData.setItemAnimator(new DefaultItemAnimator());

            btnDeleteNotification.setOnClickListener(this);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCallForClass();
            }

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                        AppUtility.SELECT_ALL_IS_CHECKED = "Y";
                        adapter.selectAll();
                    } else {
                        AppUtility.SELECT_ALL_IS_CHECKED = "N";
                        adapter.deselectAll();
                        adapter.notifyDataSetChanged();

                    }
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    chkSelectAll.setChecked(false);
                    if (count1 != 0) {
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        recyclerViewAllData.setAdapter(null);

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerViewAllData.setAdapter(null);
                    }
                }
            });

            chkSentToGuest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        isnotificationforguest = "1";
                    } else {
                        isnotificationforguest = "0";
                    }
                }
            });

            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), view, R.id.adView);
                advertiesClass.ShowAdver();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        spinnerForClass =  view.findViewById(R.id.spinnerForClass);
        chkSelectAll =  view.findViewById(R.id.chkSelectAll);
        chkSelectAll.setVisibility(GONE);
        chkSentToGuest =  view.findViewById(R.id.chkSendToGuest);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewAllData = view.findViewById(R.id.recyclerViewAllData);
        btnDeleteNotification =  view.findViewById(R.id.btnDeleteNotification);

        CustomTextViewBold txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Clear_Notification));

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


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnDeleteNotification:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnDeleteNotification);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeForNetworkcall.equals("0")) {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else {
                        if (AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT == 0) {
                            Snackbar.make(view, "Select at least one notification", Snackbar.LENGTH_SHORT).show();
                        } else {
                            btnDeleteNotification.setEnabled(false);
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Confirmation!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText("Are you sure? You want to clear notification");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("Yes");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    btnDeleteNotification.setEnabled(true);
                                    CreateJSONForEnableDisableData();
                                    dialog.dismiss();
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("No");
                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), CancledialogButton);
                                    bounce_button_class1.BounceMethod();
                                    btnDeleteNotification.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        }
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "OnClick", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }


    public void NetworkCallForNotificaion() {
        try {
            CreateJSONForNotification();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.showNotification(NotificationJSONString, AppUtility.POST_SHOW_NOTIFICATION_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "NetworkCallForNotificaion", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForNotification() {
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "CreateJSONForNotification", e);
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
//        chkSelectAll.setSelected(false);
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
                                        AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                                        chkSelectAll.setChecked(false);
                                        if (toast != null) {
                                            toast.cancel();
                                        }
                                        NetworkCallForNotificaion();

                                    } else {
//                                        chkSelectAll.setVisibility(View.GONE);
                                        ClassCodeForNetworkcall = "0";
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
                            String Message = showNotification.get(0).getResult().toString();
                            if (showNotification.get(0).getResultCode().equals("0")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(),"Error","No Data Found");
                                chkSelectAll.setVisibility(GONE);
                            } else {
                                recyclerViewAllData.setVisibility(View.VISIBLE);
                                adapter = new ClearNotificationAdapter(getContext(), showNotification);
                                recyclerViewAllData.setAdapter(adapter);
                                chkSelectAll.setVisibility(View.VISIBLE);
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
                case 630:
                    showNotification = (List<SendNotification>) data;
                    try {
                        if (showNotification.get(0).getResultCode().equals("1")) {
                            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                            chkSelectAll.setChecked(false);
                            recyclerViewAllData.setAdapter(null);
                            spinnerForClass.setSelection(0);
                            chkSelectAll.setVisibility(GONE);
                            Snackbar.make(view, showNotification.get(0).getResult(), Snackbar.LENGTH_SHORT).show();
//                            NetworkCallForNotificaion();
                        } else {
//                            Snackbar.make(view, showNotification.get(0).getResult(), Snackbar.LENGTH_SHORT).show();
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",showNotification.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        Log.d("ClearNotification", "" + ex);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableData() {
        try {
            if (showNotification.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < showNotification.size(); i++) {
                    String abc = showNotification.get(i).getCheck();
                    if (abc != null) {
                        if (abc.equals("Y")) {
                            JSONObject student1 = new JSONObject();
                            try {
                                student1.put("NotificationCode", showNotification.get(i).getNotificationId());
                                student1.put("UserName", AppUtility.KEY_USERNAME);
                                student1.put("ClassCode", ClassCodeForNetworkcall);
                            } catch (JSONException e) {
                                PrintCatchException pc = new PrintCatchException(getContext(),view, "ClearNotificationActivity", "CreateJSONForEnableDisableData", e);
                                pc.showCatchException();
                            }
                            jsonArray.put(student1);
                        }
                    }
                }
                DataAccess dataAcess = new DataAccess(getContext(), this);
                dataAcess.ClearNotification(String.valueOf(jsonArray), AppUtility.CLEAR_NOTIFICATON);
            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabClearNotification", "onQueryTextSubmit", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabClearNotification", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

