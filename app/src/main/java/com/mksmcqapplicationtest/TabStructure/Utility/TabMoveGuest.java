package com.mksmcqapplicationtest.TabStructure.Utility;

import android.app.Dialog;
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
import com.mksmcqapplicationtest.MoveStudent.MoveGuestAdapter;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.SignUpGuest;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabMoveGuest extends Fragment implements View.OnClickListener, ResponseListener, SearchView.OnQueryTextListener {

    RecyclerView recyclerViewOfMoveData;
    CustomButton btnMoveClass;
    private List<Student> students;
    View view;
    List<Class> classResponse;
    Spinner spinnerFormClass, spinnerToClass;
    String[] UserNameDropDownFormClass = new String[1];
    String[] UserNameDropDownToClass = new String[1];
    String ClassNameJSONString, ClassCodeFormNetworkcall, ClassNameFormNetworkCall, ClassCodeTo, ClassNameTo;
    int count1 = 0;
    int j1 = 0;
    int ToCount = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    Toast toast;
    String TestListJSONString;
    private List<SignUpGuest> signUpGuests;
    MoveGuestAdapter moveGuestAdapter;
    android.support.v7.widget.SearchView searchViewAndroidActionBar;
    CheckBox chkSelectAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_move_guest, container, false);
        try {
            setUI();

            if (AppUtility.IsDemoApplication) {
                btnMoveClass.setClickable(false);
                btnMoveClass.setEnabled(false);
            } else {
                btnMoveClass.setClickable(true);
                btnMoveClass.setEnabled(true);
            }


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfMoveData.setLayoutManager(layoutManager);
            recyclerViewOfMoveData.setItemAnimator(new DefaultItemAnimator());

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCallForClass();
            }
            btnMoveClass.setOnClickListener(this);
            chkSelectAll = (CheckBox) view.findViewById(R.id.chkSelectAll);
            chkSelectAll.setVisibility(View.GONE);

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (signUpGuests.size() > 0) {
                            moveGuestAdapter.selectAll();
                        } else {
                            Snackbar.make(view, "No Students Available For Enable", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        if (signUpGuests.size() > 0) {
                            moveGuestAdapter.deselectAll();
                            moveGuestAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, "No Students Available For Disable", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        ClassCodeFormNetworkcall = classResponse.get(count1 - 1).getClassCode();
                        ClassNameFormNetworkCall = classResponse.get(count1 - 1).getClassName();
                        recyclerViewOfMoveData.setAdapter(null);
                        NetworkCallForStudentList();

                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        swipeRefreshLayout =  view.findViewById(R.id.swipeRefreshLayout);
        spinnerFormClass =  view.findViewById(R.id.spinnerForClass);
        spinnerToClass =  view.findViewById(R.id.spinnerToClass);
        spinnerToClass.setEnabled(false);
        recyclerViewOfMoveData =  view.findViewById(R.id.recyclerViewOfMoveData);
        btnMoveClass =  view.findViewById(R.id.btnMoveClass);

        CustomTextViewBold txtactionbartitle =  getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Move_Guest));

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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

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

                            //From Class Adapter & Item Selected Listner
                            UserNameDropDownFormClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j1 = 0;
                            UserNameDropDownFormClass[j1] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownFormClass[j1] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownFormClass);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerFormClass.setAdapter(Select_Category_Adapter);

                            spinnerFormClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {

                                        spinnerToClass.setEnabled(true);
                                        ClassCodeFormNetworkcall = classResponse.get(count1 - 1).getClassCode();
                                        ClassNameFormNetworkCall = classResponse.get(count1 - 1).getClassName();
                                        recyclerViewOfMoveData.setAdapter(null);
                                        NetworkCallForStudentList();

                                    } else {
                                        spinnerToClass.setEnabled(false);
                                        recyclerViewOfMoveData.setAdapter(null);
                                        ClassCodeFormNetworkcall = "0";
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


                            //To Class Adapter & Item Selected Listner
                            UserNameDropDownToClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j1 = 0;
                            UserNameDropDownToClass[j1] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownToClass[j1] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }

                            ArrayAdapter<String> Select_ToClass_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownToClass);
                            Select_ToClass_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerToClass.setAdapter(Select_ToClass_Adapter);

                            spinnerToClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int ToPosition, long l) {
                                    ToCount = ToPosition;
                                    if (ToCount != 0) {

                                        ClassCodeTo = classResponse.get(ToCount - 1).getClassCode();
                                        ClassNameTo = classResponse.get(ToCount - 1).getClassName();

                                    } else {
                                        ClassCodeTo = "0";
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

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

                case 570:
                    try {
                        signUpGuests = (List<SignUpGuest>) data;


                        if (signUpGuests.get(0).getResultCode().equals("1")) {
                            chkSelectAll.setVisibility(View.VISIBLE);
                            moveGuestAdapter = new MoveGuestAdapter(getContext(), recyclerViewOfMoveData, signUpGuests);
                            recyclerViewOfMoveData.setAdapter(moveGuestAdapter);
                            toast = Toast.makeText(getContext(), "Total: " + String.valueOf(signUpGuests.size()), Toast.LENGTH_LONG);
                            toast.show();


                        } else if (signUpGuests.get(0).getResultCode().equals("0")) {

                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",signUpGuests.get(0).getResult().toString());

                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
                    }

                    break;
                case 600:
                    students = (List<Student>) data;
                    try {
                        String msg = students.get(0).getResult().toString();
                        if (msg.equals("Student class change successfully")) {
                            chkSelectAll.setChecked(false);
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Sucessful!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText("Student Move From " + ClassNameFormNetworkCall + "Guest Class to " + ClassNameTo + "Class Sucessfully");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("OK");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    getActivity().finish();
                                    int j = -1;
                                    for (int i = 0; i < AppUtility.childResponse.size(); i++) {
                                        if (AppUtility.childResponse.get(i).getGroupMenuCode().equals("9")) {
                                            j++;
                                            if (AppUtility.childResponse.get(i).getChildMenuName().equals("Move Guest")) {
                                                AppUtility.TabMoveGuest = j;
                                                break;
                                            } else {
                                                AppUtility.TabMoveGuest = 100000;
                                            }
                                        } else {
                                            AppUtility.TabMoveGuest = 100000;
                                        }
                                    }

                                    Intent intent8 = new Intent(getContext(), TabUtilityActivity.class);
                                    intent8.putExtra("SelectedTabPosition", AppUtility.TabMoveGuest);
                                    intent8.putExtra("GroupCode", "9");
                                    startActivity(intent8);
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("No");
                            CancledialogButton.setVisibility(View.GONE);
                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        } else if (msg.equals("Student mark as guest successfully")) {
                            chkSelectAll.setChecked(false);
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Sucessful!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText("Student Move From " + ClassNameFormNetworkCall + "Guest Class to " + ClassNameTo + "Class Sucessfully");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("OK");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    getActivity().finish();
                                    int j = -1;
                                    for (int i = 0; i < AppUtility.childResponse.size(); i++) {
                                        if (AppUtility.childResponse.get(i).getGroupMenuCode().equals("9")) {
                                            j++;
                                            if (AppUtility.childResponse.get(i).getChildMenuName().equals("Move Guest")) {
                                                AppUtility.TabMoveGuest = j;
                                                break;
                                            } else {
                                                AppUtility.TabMoveGuest = 100000;
                                            }
                                        } else {
                                            AppUtility.TabMoveGuest = 100000;
                                        }
                                    }

                                    Intent intent8 = new Intent(getContext(), TabUtilityActivity.class);
                                    intent8.putExtra("SelectedTabPosition", AppUtility.TabMoveGuest);
                                    intent8.putExtra("GroupCode", "9");
                                    startActivity(intent8);
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("No");
                            CancledialogButton.setVisibility(View.GONE);
                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", students.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "Error GCD3!! No Data Found", Snackbar.LENGTH_LONG).show();

                    }
                    if (AppUtility.IsAdvertiesVisible) {
                        InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
                        interstitialAdvertiesClass.showInterstitialAdver();
                    }
                    break;

            }
        } catch (
                Exception e)

        {
            Snackbar.make(view, "Something went wrong please try again later", Snackbar.LENGTH_LONG).show();
        }

    }

    private void NetworkCallForStudentList() {
        try {
            chkSelectAll.setVisibility(View.GONE);
            chkSelectAll.setChecked(false);
            CreateJSonForTestList();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getAllGuestList(AppUtility.GET_ALL_GUEST_FOR_ENABLE, TestListJSONString);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "NetworkCallForStudentList", e);
            pc.showCatchException();
        }
    }

    private void CreateJSonForTestList() {
        try {
            User user = new User();
            Gson gson = new Gson();
            user.setClassCode(ClassCodeFormNetworkcall);
            user.setWhichList("Active");
            user.setStartIndex("0");
            String jso = gson.toJson(user);
            TestListJSONString = "[" + jso + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "CreateJSonForTestList", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "noResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnMoveClass:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMoveClass);
                    bounce_button_class1.BounceMethod();
                    if (ClassCodeFormNetworkcall.equals("0")) {
                        Snackbar.make(view, "Please Select From Class Name", Snackbar.LENGTH_LONG).show();
                    } else if (ClassCodeTo == "0") {
                        Snackbar.make(view, "Please Select To Class Name", Snackbar.LENGTH_LONG).show();
                    } else {
                        DialogShowForMove();
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "onClick", e);
            pc.showCatchException();
        }
    }

    private void DialogShowForMove() {
        try {
            btnMoveClass.setEnabled(false);
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Confirmation!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Are you sure? You want to move student");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("Yes");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class1.BounceMethod();
                    btnMoveClass.setEnabled(true);
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
                    btnMoveClass.setEnabled(true);
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "DialogShowForMove", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableTest() {
        try {
            if (signUpGuests.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < signUpGuests.size(); i++) {
                    String abc = signUpGuests.get(i).getIsSelected();
                    if (abc != null) {
                        if (abc.equals("Y")) {
                            JSONObject student1 = new JSONObject();
                            try {

                                student1.put("UserName", AppUtility.KEY_USERNAME);
                                student1.put("PreviousClassCode", ClassCodeFormNetworkcall);
                                student1.put("ClassCode", ClassCodeTo);
                                student1.put("StudentCode", signUpGuests.get(i).getStudentCode());
                                student1.put("IsGuest", "0");
                            } catch (JSONException e) {
                                PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "CreateJSONForEnableDisableTest", e);
                                pc.showCatchException();
                            }
                            jsonArray.put(student1);
                        }

                    }

                }
                if (jsonArray.length() > 0) {
                    DataAccess dataAcess = new DataAccess(getContext(), this);
                    dataAcess.MoveStudent(String.valueOf(jsonArray), AppUtility.MOVE_Student);
                } else {
                    Snackbar.make(view, "Please select at least one student to move", Snackbar.LENGTH_LONG).show();
                }

            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "MoveStudentGuestToClass", "CreateJSONForEnableDisableTest", e);
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
            if (signUpGuests != null) {
                newText = newText.toLowerCase();
                List<SignUpGuest> newTestList = new ArrayList<>();
                for (SignUpGuest data : signUpGuests) {
                    if (data.getStudentName() != null && data.getStudentName() != null) {
                        String name = data.getStudentName().toLowerCase();
                        String name1 = data.getStudentName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getStudentName() == null) {
                        String name1 = data.getStudentName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getStudentName() == null) {
                        String name = data.getStudentName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                moveGuestAdapter.setFilter(newTestList);
                return false;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabMoveGuest", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (signUpGuests != null) {
                newText = newText.toLowerCase();
                List<SignUpGuest> newTestList = new ArrayList<>();
                for (SignUpGuest data : signUpGuests) {
                    if (data.getStudentName() != null && data.getStudentName() != null) {
                        String name = data.getStudentName().toLowerCase();
                        String name1 = data.getStudentName().toLowerCase();

                        if (name.contains(newText) || name1.contains(newText)) {
                            newTestList.add(data);
                        }

                    } else if (data.getStudentName() == null) {
                        String name1 = data.getStudentName().toLowerCase();

                        if (name1.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else if (data.getStudentName() == null) {
                        String name = data.getStudentName().toLowerCase();

                        if (name.contains(newText)) {
                            newTestList.add(data);
                        }
                    } else {
                        return false;
                    }
                }
                moveGuestAdapter.setFilter(newTestList);
                return true;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabMoveGuest", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

