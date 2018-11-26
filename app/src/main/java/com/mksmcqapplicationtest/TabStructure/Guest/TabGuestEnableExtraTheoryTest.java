package com.mksmcqapplicationtest.TabStructure.Guest;

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
import android.widget.Toast;

import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.Guest.GuestDataEnableAdapter;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.SendNotification;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabGuestEnableExtraTheoryTest extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener,ResponseListener {

    RecyclerView recyclerViewOfEnableData;
    CustomButton btnMakeDataEnableDisable;
    String GetDataJSONString;
    List<Data> datas, dataa;
    View view;
    List<Class> classResponse;
    Spinner spinnerForClass;
    String[] UserNameDropDownForClass = new String[1];
    String ClassNameJSONString, ClassCodeForNetworkcall, ClassNameForNetworkCall;
    int count1 = 0;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    GuestDataEnableAdapter activeUserListAdapter;
    String sendNotificationJSONString;
    List<SendNotification> sendNotifications;
    Toast toast;
    CheckBox chkSelectAll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_theory_test, container, false);
        try {
            setUI();

            if (AppUtility.IsDemoApplication) {
                btnMakeDataEnableDisable.setClickable(false);
                btnMakeDataEnableDisable.setEnabled(false);
            } else {
                btnMakeDataEnableDisable.setClickable(true);
                btnMakeDataEnableDisable.setEnabled(true);
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfEnableData.setLayoutManager(layoutManager);
            recyclerViewOfEnableData.setItemAnimator(new DefaultItemAnimator());


            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCallForClass();
            }
            btnMakeDataEnableDisable.setOnClickListener(this);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        ClassNameForNetworkCall = classResponse.get(count1 - 1).getClassName();
                        recyclerViewOfEnableData.setAdapter(null);


                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (datas.size() > 0) {
                            activeUserListAdapter.selectAll();
                        } else {
                            Snackbar.make(view, "No Extra Theory Test Available For Enable", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        if (datas.size() > 0) {
                            activeUserListAdapter.deselectAll();
                            activeUserListAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, "No Extra Theory Test Available For Disable", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        chkSelectAll =  view.findViewById(R.id.chkSelectAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        spinnerForClass =  view.findViewById(R.id.spinnerForClass);
        recyclerViewOfEnableData = view.findViewById(R.id.recyclerViewOfEnable);
        btnMakeDataEnableDisable =  view.findViewById(R.id.btnMakeEnable);
        CustomTextViewBold txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Extra_Theory_Data_Exam));

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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForAllData() {
        try {
            CreateJsonForNetworkCallOfData();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.GetAllGuestData(AppUtility.Guest_GET_CLASS_DATA, GetDataJSONString);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "NetworkCallForAllData", e);
            pc.showCatchException();
        }
    }

    private void CreateJsonForNetworkCallOfData() {
        try {
            Gson gson = new Gson();
            Data dataresponse = new Data();
            dataresponse.setUserName(AppUtility.KEY_USERNAME);
            dataresponse.setClassCode(ClassCodeForNetworkcall);
            dataresponse.setExtra("1");
            String jsonData = gson.toJson(dataresponse);
            GetDataJSONString = "[" + jsonData + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "CreateJsonForNetworkCallOfData", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableData() {
        try {
            if (datas.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < datas.size(); i++) {
                    String abc = datas.get(i).getAcFlagGuest();
                    if (abc != null) {
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("DataCode", datas.get(i).getDataCode());
                            student1.put("AcFlagGuest", datas.get(i).getAcFlagGuest());
                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "CreateJSONForEnableDisableData", e);
                            pc.showCatchException();
                        }

                        jsonArray.put(student1);
                    }

                }


                DataAccess dataAcess = new DataAccess(getContext(), this);
                dataAcess.EnableDisableData(String.valueOf(jsonArray), AppUtility.ENABLE_DISABLE_DATA_Guest);
            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "CreateJSONForEnableDisableData", ex);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        swipeRefreshLayout.setRefreshing(false);
        datas = (List<Data>) data;
        try {

            if (datas.get(0).getResultCode().equals("1")) {
//                SharedPreferences setting = getContext().getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
//                SharedPreferences.Editor editor = setting.edit();
//                editor.putString("ClassCodeForAnswersheet", ClassCodeForNetworkcall);
//                editor.commit();
                activeUserListAdapter = new GuestDataEnableAdapter(getContext(), recyclerViewOfEnableData, datas);
                recyclerViewOfEnableData.setAdapter(activeUserListAdapter);
                toast = Toast.makeText(getContext(), "Total: " + String.valueOf(datas.size()), Toast.LENGTH_LONG);
                toast.show();

            } else if (datas.get(0).getResultCode().equals("0")) {
                Snackbar.make(view, datas.get(0).getResult().toString(), Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();

        }
    }

    private void NetWorkCallForSendNotification() {
        try {
            String NotificatioTitle = "New Extra Theory Test";
            String NotivationText = "For " + ClassNameForNetworkCall + " New Extra Theory Test Is Live"+ '\n' + "Regards," + '\n' + AppUtility.App_Name+"."+"";
            sendNotificationJSONString = new CreateJsonFunction().CreateJsonForSendNotification(ClassCodeForNetworkcall, "1", NotivationText, NotificatioTitle, "");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.sendNotification(sendNotificationJSONString, AppUtility.SEND_NOTIFICATION_FROM_TEACHER);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "NetWorkCallForSendNotification", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 540:
                    datas = (List<Data>) data;
                    try {

                        if (datas.get(0).getResultCode().equals("1")) {
//                            SharedPreferences setting = getContext().getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
//                            SharedPreferences.Editor editor = setting.edit();
//                            editor.putString("ClassCodeForAnswersheet", ClassCodeForNetworkcall);
//                            editor.commit();
                            activeUserListAdapter = new GuestDataEnableAdapter(getContext(), recyclerViewOfEnableData, datas);
                            recyclerViewOfEnableData.setAdapter(activeUserListAdapter);
                            toast = Toast.makeText(getContext(), "Total: " + String.valueOf(datas.size()), Toast.LENGTH_LONG);
                            toast.show();

                        } else if (datas.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",datas.get(0).getResult().toString());

                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();

                    }
                    break;
                case 360:
                    dataa = (List<Data>) data;
                    try {

                        if (dataa.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, "Active/Deactive Operation Sucessfull", Snackbar.LENGTH_LONG).show();
                            chkSelectAll.setChecked(false);
                            activeUserListAdapter.deselectAll();
                            NetworkCallForAllData();
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

                        } else if (dataa.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",dataa.get(0).getResult().toString());


                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Error GCD3!! No Data Found", Snackbar.LENGTH_LONG).show();

                    }
                    if (AppUtility.IsAdvertiesVisible) {
                        InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
                        interstitialAdvertiesClass.showInterstitialAdver();
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
                                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                                        ClassNameForNetworkCall = classResponse.get(count1 - 1).getClassName();
                                        recyclerViewOfEnableData.setAdapter(null);
                                        NetworkCallForAllData();

                                    } else {

                                        recyclerViewOfEnableData.setAdapter(null);
                                        ClassCodeForNetworkcall = "0";
                                    }
                                    if (toast != null) {
                                        toast.cancel();
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
                                        NetWorkCallForClass();
                                    }
                                }).show();
                    }
                    break;
                case 450:
                    try {
                        sendNotifications = (List<SendNotification>) data;
                        if (sendNotifications.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, "Notification send sucessfully", Snackbar.LENGTH_LONG).show();
                        } else if (sendNotifications.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",sendNotifications.get(0).getResult().toString());

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
            Snackbar.make(view, "Something went wrong please try again later", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMakeEnable:

                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMakeDataEnableDisable);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeForNetworkcall.equals("0")) {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else {

                        btnMakeDataEnableDisable.setEnabled(false);
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Confirmation!!");
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure? You want to active/deactive extra theory test");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class1.BounceMethod();
                                btnMakeDataEnableDisable.setEnabled(true);

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
                                btnMakeDataEnableDisable.setEnabled(true);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                    break;
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableExtraDataForGuest", "onClick", e);
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
            if (datas != null) {
                newText = newText.toLowerCase();
                List<Data> newTestList = new ArrayList<>();
                for (Data data : datas) {
                    if (data.getDataName() != null && data.getDataName() != null) {
                        String name = data.getDataName().toLowerCase();
                        String name1 = data.getDataName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getDataName() == null) {
                        String name1 = data.getDataName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getDataName() == null) {
                        String name = data.getDataName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                activeUserListAdapter.setFilter(newTestList);
                return false;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabGuestEnableExtraTheoryTest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (datas != null) {
                newText = newText.toLowerCase();
                List<Data> newTestList = new ArrayList<>();
                for (Data data : datas) {
                    if (data.getDataName() != null && data.getDataName() != null) {
                        String name = data.getDataName().toLowerCase();
                        String name1 = data.getDataName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getDataName() == null) {
                        String name1 = data.getDataName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getDataName() == null) {
                        String name = data.getDataName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                activeUserListAdapter.setFilter(newTestList);
                return true;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabGuestEnableExtraTheoryTest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

