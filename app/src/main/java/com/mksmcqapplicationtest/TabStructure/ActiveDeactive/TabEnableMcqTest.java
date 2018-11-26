package com.mksmcqapplicationtest.TabStructure.ActiveDeactive;

import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.TestEnableAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabEnableMcqTest extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener,ResponseListener {

    RecyclerView recyclerViewOfEnableTest;
    CustomButton btnMakeClassEnableDisable;
    String ClassNameJSONString, ClassCodeForNetworkcall, ClassNameForNetworkCall, TestListJSONString, sendNotificationJSONString;
    String[] UserNameDropDownForClass = new String[1];
    List<Class> classResponse;
    Spinner spinnerForClass;
    List<Test> test;
    View view;
    int count1;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    TestEnableAdapter adapter;
    List<SendNotification> sendNotifications;
    CheckBox chkSelectAll;
    CustomTextViewBold txtactionbartitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_theory_test, container, false);
        try {
            setUI();

            if (AppUtility.IsDemoApplication) {
                btnMakeClassEnableDisable.setClickable(false);
                btnMakeClassEnableDisable.setEnabled(false);
            } else {
                btnMakeClassEnableDisable.setClickable(true);
                btnMakeClassEnableDisable.setEnabled(true);
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfEnableTest.setLayoutManager(layoutManager);
            recyclerViewOfEnableTest.setItemAnimator(new DefaultItemAnimator());
            btnMakeClassEnableDisable.setOnClickListener(this);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCallForAllClass();
            }

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        recyclerViewOfEnableTest.setAdapter(null);
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (test.size() > 0) {
                            adapter.selectAll();
                        } else {
                            Snackbar.make(view, "No MCQ Test Available For Enable", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        if (test.size() > 0) {
                            adapter.deselectAll();
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, "No MCQ Test For Disable", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        chkSelectAll = view.findViewById(R.id.chkSelectAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewOfEnableTest = view.findViewById(R.id.recyclerViewOfEnable);
        btnMakeClassEnableDisable = view.findViewById(R.id.btnMakeEnable);
        spinnerForClass = view.findViewById(R.id.spinnerForClass);
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
    private void NetworkCallForAllClass() {
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableTest", "NetworkCallForAllClass", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            swipeRefreshLayout.setRefreshing(false);
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
                                        ClassNameForNetworkCall = classResponse.get(count1 - 1).getClassName();
                                        recyclerViewOfEnableTest.setAdapter(null);
                                        NetworkCallForTestList();
                                    } else {
                                        recyclerViewOfEnableTest.setAdapter(null);
                                        ClassCodeForNetworkcall = "0";
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
                                        NetworkCallForAllClass();
                                    }
                                }).show();
                    }
                    break;

                case 370:
                    test = (List<Test>) data;
                    try {

                        if (test.get(0).getResult().equals("Suessfull")) {
                            Snackbar.make(view, "Active/Deactive Operation Sucessfull", Snackbar.LENGTH_LONG).show();
                            chkSelectAll.setChecked(false);
                            adapter.deselectAll();
                            NetworkCallForTestList();
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Confirmation!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText("You Want To Send Notification?");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("Yes");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    NetWorkCallForSendNotification();
                                    dialog.dismiss();
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("No");
                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        } else if (test.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No Data Found");
                        } else if (test.get(0).getResult().equals("Something Went Wrong Please Try Again")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","Something Went Wrong Please Try Again");
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Error GCD3!! No Data Found", Snackbar.LENGTH_LONG).show();

                    }
                    break;
                case 670:
                    test = (List<Test>) data;
                    try {

                        if (test.get(0).getResultCode().equals("1")) {
                            adapter = new TestEnableAdapter(getContext(), recyclerViewOfEnableTest, test);
                            recyclerViewOfEnableTest.setAdapter(adapter);
                        } else {
//                            Snackbar snackbar = Snackbar.make(view, "No test are available..", Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            NetworkCallForTestList();
//                                        }
//                                    });
//
//                            snackbar.show();
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No test are available..");

                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case 450:
                    try {
                        sendNotifications = (List<SendNotification>) data;
                        if (sendNotifications.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, "Notification send sucessfully", Snackbar.LENGTH_LONG).show();
                            NetworkCallForTestList();
                        } else if (sendNotifications.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",sendNotifications.get(0).getResult().toString());

//                            Snackbar.make(view, sendNotifications.get(0).getResult().toString(), Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            NetWorkCallForSendNotification();
//                                        }
//                                    }).show();
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Something went wrong please try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCallForSendNotification();
                                    }
                                }).show();
                    }
                    break;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableTest", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForTestList() {
        try {
            if (!(ClassCodeForNetworkcall.equals("0") || ClassCodeForNetworkcall == null)) {
                TestListJSONString = new CreateJsonFunction().CreateJsonForGetTestList(ClassCodeForNetworkcall, "all");
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getAllTestList(AppUtility.GET_ALL_TEST_LIST, TestListJSONString);

            } else {
                Snackbar.make(view, "Select Class Name ", Snackbar.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableTest", "NetworkCallForTestList", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableTest", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMakeEnable:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMakeClassEnableDisable);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeForNetworkcall == "0") {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else {
                        btnMakeClassEnableDisable.setEnabled(false);
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Confirmation!!");
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure? You want to active/deactive MCQ test");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class1.BounceMethod();
                                btnMakeClassEnableDisable.setEnabled(true);
                                CreateJSONForEnableDisableTest();
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
                                btnMakeClassEnableDisable.setEnabled(true);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                    break;
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "onClick", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableTest() {
        try {
            if (test.size() > 0) {

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < test.size(); i++) {
                    String abc = test.get(i).getAcFlag();
                    if (abc != null) {
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("TestCode", test.get(i).getTestCode());
                            student1.put("AcFlag", test.get(i).getAcFlag());

                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "CreateJSONForEnableDisableTest", e);
                            pc.showCatchException();
                        }
                        jsonArray.put(student1);
                    }

                }


            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "CreateJSONForEnableDisableTest", e);
            pc.showCatchException();
        }
    }
    private void NetWorkCallForSendNotification() {
        try {
            String NotificatioTitle = "New MCQ Test";
            String NotivationText = "For " + ClassNameForNetworkCall + " New MCQ Theory Test Is Live"+ '\n' + "Regards," + '\n' + AppUtility.App_Name+"."+"";
            sendNotificationJSONString = new CreateJsonFunction().CreateJsonForSendNotification(ClassCodeForNetworkcall, "0", NotivationText, NotificatioTitle, "");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.sendNotification(sendNotificationJSONString, AppUtility.SEND_NOTIFICATION_FROM_TEACHER);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableTest", "NetWorkCallForSendNotification", e);
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
            if (test != null) {
                newText = newText.toLowerCase();
                List<Test> newTestList = new ArrayList<>();
                for (Test data : test) {
                    if (data.getTestName() != null && data.getTestName() != null) {
                        String name = data.getTestName().toLowerCase();
                        String name1 = data.getTestName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getTestName() == null) {
                        String name1 = data.getTestName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getTestName() == null) {
                        String name = data.getTestName().toLowerCase();

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
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabEnableMcqTest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (test != null) {
                newText = newText.toLowerCase();
                List<Test> newTestList = new ArrayList<>();
                for (Test data : test) {
                    if (data.getTestName() != null && data.getTestName() != null) {
                        String name = data.getTestName().toLowerCase();
                        String name1 = data.getTestName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getTestName() == null) {
                        String name1 = data.getTestName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getTestName() == null) {
                        String name = data.getTestName().toLowerCase();

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
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabEnableMcqTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

