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
import android.widget.Toast;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.StudentEnableAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabEnableStudent extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener,ResponseListener {

    RecyclerView recyclerViewOfEnableTest;
    Button btnMakeClassEnableDisable;
    String ClassNameJSONString, ClassCodeForNetworkcall, TestListJSONString;
    String[] UserNameDropDownForClass = new String[1];
    List<Class> classResponse;
    Spinner spinnerForClass;
    List<Student> student;
    View view;
    int count1;
    private List<Student> students;
    int j1 = 0;
    SwipeRefreshLayout swipeRefreshLayout;
    StudentEnableAdapter adapter;
    Toast toast;
    CheckBox chkSelectAll;
    CustomTextViewBold txtactionbartitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_enable_theory_test, container, false);
        try {
            SetUI();
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


            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCallForAllClass();
            }


            btnMakeClassEnableDisable.setOnClickListener(this);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();
                        recyclerViewOfEnableTest.setAdapter(null);
                        ClassCodeForNetworkcall = "1";
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        if (students.size() > 0) {
                            adapter.selectAll();
                        } else {
                            Snackbar.make(view, "No Students Available For Enable", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        if (students.size() > 0) {
                            adapter.deselectAll();
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(view, "No Students Available For Disable", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableStudent", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void SetUI() {
        chkSelectAll = (CheckBox) view.findViewById(R.id.chkSelectAll);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewOfEnableTest = (RecyclerView) view.findViewById(R.id.recyclerViewOfEnable);
        btnMakeClassEnableDisable = (Button) view.findViewById(R.id.btnMakeEnable);
        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Student));
        spinnerForClass = (Spinner) view.findViewById(R.id.spinnerForClass);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableStudent", "NetworkCallForAllClass", e);
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
                                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();
                                        recyclerViewOfEnableTest.setAdapter(null);
                                        ClassCodeForNetworkcall = "1";
                                        NetworkCallForStudentList();
                                    } else {
                                        recyclerViewOfEnableTest.setAdapter(null);
                                        ClassCodeForNetworkcall = "0";
                                    }
                                    if (toast != null)
                                        toast.cancel();
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

                case 180:
                    try {
                        students = (List<Student>) data;
                        if (students != null && (students.size() != 0)) {
                            if (students.get(0).getResult().equals("No Data Found")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(),"Error",students.get(0).getResult().toString());
                            } else if (students.get(0).getResult().equals("Suessfull")) {
                                adapter = new StudentEnableAdapter(getContext(), recyclerViewOfEnableTest, students);
                                recyclerViewOfEnableTest.setAdapter(adapter);
                                toast = Toast.makeText(getContext(), "Total: " + String.valueOf(students.size()), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No students are available..");
                        }
                    } catch (Exception ex) {
                        Snackbar.make(view, "" + ex, Snackbar.LENGTH_LONG).show();
                    }

                    break;
                case 410:
                    student = (List<Student>) data;
                    try {

                        if (student.get(0).getResult().equals("Suessfull")) {
                            chkSelectAll.setChecked(false);
                            adapter.deselectAll();
                            NetworkCallForStudentList();
                            Snackbar.make(view, "Active/Deactive Operation Sucessfull", Snackbar.LENGTH_LONG).show();
                        } else if (student.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",student.get(0).getResult().toString());
                        } else if (student.get(0).getResult().equals("Something Went Wrong Please Try Again")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",student.get(0).getResult().toString());
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

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableStudent", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForStudentList() {
        try {
            if (!(AppUtility.KEY_CLASSCODE.equals("0") || AppUtility.KEY_CLASSCODE == null)) {
                TestListJSONString = new CreateJsonFunction().CreateJSONForStudentList(AppUtility.KEY_CLASSCODE, "all");
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.getStudentNameList(TestListJSONString, AppUtility.GET_STUDENT_NAME_LIST);

            } else {
                Snackbar.make(view, "Select Class Name ", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableStudent", "NetworkCallForStudentList", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "EnableStudent", "noResponse", e);
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
                        txtMessage.setText("Are you sure? You want to active/deactive student");
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
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableStudent", "onClick", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForEnableDisableTest() {
        try {
            if (students.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < students.size(); i++) {
                    String abc = students.get(i).getAcFlag();
                    if (abc != null) {
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("StudentCode", students.get(i).getStudentCode());
                            student1.put("AcFlag", students.get(i).getAcFlag());

                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableStudent", "CreateJSONForEnableDisableTest", e);
                            pc.showCatchException();
                        }
                        jsonArray.put(student1);
                    }

                }

            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableStudent", "CreateJSONForEnableDisableTest", ex);
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
            if (students != null) {
                newText = newText.toLowerCase();
                List<Student> newTestList = new ArrayList<>();
                for (Student data : students) {
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
                adapter.setFilter(newTestList);
                return false;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabEnableStudent", "onQueryTextSubmit", e);
            pc.showCatchException();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (students != null) {
                newText = newText.toLowerCase();
                List<Student> newTestList = new ArrayList<>();
                for (Student data : students) {
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
                adapter.setFilter(newTestList);
                return true;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabEnableStudent", "onQueryTextChange", e);
            pc.showCatchException();
        }
        return false;
    }
}

