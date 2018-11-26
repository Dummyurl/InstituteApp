package com.mksmcqapplicationtest.TabStructure.Exam;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AddTheoryTestActivity;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.SendMarksAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class TabSendMarks extends Fragment implements ResponseListener, View.OnClickListener {
    String SelectedDate = "";
    Spinner spinnerForClass, spinnerForTestList;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerViewOfStudentList;
    Button btnSendSMS;
    String ClassNameJSONString, ClassCodeForNetworkCall, ClassCodeForNotification, TestListJSONString, TestCodeForNetworkCall = "0", TestNameForNetworkCall, StudentNameArrayString, sendNotificationJSONString;
    View parentLayout;
    List<Class> classResponse;
    String[] UserNameDropDownForClass = new String[1];
    String[] UserNameDropDownForTest = new String[1];
    int count1, count2;
    int j1 = 0;
    private List<Data> tests;
    List<Student> studentResponseOnDataCode;
    List<Student> studentResponse;
    SendMarksAdapter sendMarksAdapter;
    List<SendNotification> sendNotifications;
    EditText editTextForTotalMarks;
    TextView txtSelectConductedDate;
    Typeface font;
    LinearLayout linearSelectDate;
    String totalMarks;
    CustomTextViewBold txtactionbartitle;
    LinearLayout layoutspinnerofclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_send_marks, container, false);
        try {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            setUI();
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCallForClass();
            }

            font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
            txtSelectConductedDate.setTypeface(font);
            txtSelectConductedDate.setText(R.string.fa_calender);
            SelectedDate = "";
            totalMarks = "";
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfStudentList.setLayoutManager(layoutManager);
            recyclerViewOfStudentList.setItemAnimator(new DefaultItemAnimator());


            btnSendSMS.setOnClickListener(this);
            linearSelectDate.setOnClickListener(this);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (count1 != 0) {
                        if (count2 != 0) {
                            recyclerViewOfStudentList.setAdapter(null);

                        } else {
                            recyclerViewOfStudentList.setAdapter(null);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        recyclerViewOfStudentList.setAdapter(null);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            });

            FloatingActionButton fab = (FloatingActionButton) parentLayout.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentGivenExamList = new Intent(getContext(), AddTheoryTestActivity.class);
                    startActivity(intentGivenExamList);
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        layoutspinnerofclass = parentLayout.findViewById(R.id.layoutspinnerofclass);
        spinnerForClass = (Spinner) parentLayout.findViewById(R.id.spinnerForClass);
        spinnerForTestList = (Spinner) parentLayout.findViewById(R.id.spinnerForTestList);
        swipeRefreshLayout = (SwipeRefreshLayout) parentLayout.findViewById(R.id.swipeRefreshLayout);
        recyclerViewOfStudentList = (RecyclerView) parentLayout.findViewById(R.id.recyclerViewOfStudentList);
        editTextForTotalMarks = (EditText) parentLayout.findViewById(R.id.editTextForTotalMarks);
        txtSelectConductedDate = (TextView) parentLayout.findViewById(R.id.txtSelectConductedDate);
        linearSelectDate = (LinearLayout) parentLayout.findViewById(R.id.linearSelectDate);
        btnSendSMS = (Button) parentLayout.findViewById(R.id.btnSendSMS);
        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.extra_theory_test_fragment_title));

        txtactionbartitle = (CustomTextViewBold) getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.send_marks_fragment_title));

        ImageButton imageButtonBackPress = (ImageButton) getActivity().findViewById(R.id.imagviewbackpress);
        imageButtonBackPress.setVisibility(View.VISIBLE);
        imageButtonBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ImageButton imageAdd = (ImageButton) getActivity().findViewById(R.id.imageadd);
        imageAdd.setVisibility(View.VISIBLE);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGivenExamList = new Intent(getContext(), AddTheoryTestActivity.class);
                startActivity(intentGivenExamList);
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
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "NetWorkCallForClass", e);
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
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        spinnerForTestList.setAdapter(null);
                                        ClassCodeForNetworkCall = classResponse.get(count1 - 1).getClassCode();
                                        ClassCodeForNotification = classResponse.get(count1 - 1).getClassCode();
                                        NetworkCallForTestList();
//                                    NetWorkCallForStudentList();
                                    } else {
                                        spinnerForTestList.setAdapter(null);
                                        ClassCodeForNetworkCall = "0";
                                        recyclerViewOfStudentList.setAdapter(null);
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
                        Snackbar.make(parentLayout, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCallForClass();
                                    }
                                }).show();
                    }
                    break;

                case 140:
                    tests = (List<Data>) data;
                    try {

                        if (tests.get(0).getResultCode().equals("1")) {
                            UserNameDropDownForTest = new String[tests.size() + 1];
                            j1 = 0;
                            UserNameDropDownForTest[j1] = "Select Theory Test Name...";
                            for (int i = 0; i < tests.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownForTest[j1] = tests.get(i).getDataName();
                            }
                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForTest);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForTestList.setAdapter(Select_Category_Adapter);
                            spinnerForTestList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    count2 = position; //this would give you the id of the selected item
                                    if (count2 != 0) {
                                        TestCodeForNetworkCall = tests.get(count2 - 1).getDataCode();
                                        TestNameForNetworkCall = tests.get(count2 - 1).getDataName();
                                        NetWorkCallForStudentListForSendMark();
                                    } else {
                                        TestCodeForNetworkCall = "0";
                                        recyclerViewOfStudentList.setAdapter(null);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", "No test are available..");
//                            Snackbar snackbar = Snackbar.make(parentLayout, "No test are available..", Snackbar.LENGTH_LONG)
//                                    .setAction("Retry", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            NetworkCallForTestList();
//                                        }
//                                    });
//                            snackbar.show();
                        }
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                    }
                    break;


                case 340:
                    studentResponseOnDataCode = (List<Student>) data;
                    if (studentResponseOnDataCode.get(0).getResultCode().equals("1")) {
                        AppUtility.arraylist = new String[studentResponseOnDataCode.size()];
                        AppUtility.arraylistMarksAfterChange = new String[studentResponseOnDataCode.size()];
                        AppUtility.arraylistChangeMarks = new String[studentResponseOnDataCode.size()];
                        for (int i = 0; i < studentResponseOnDataCode.size(); i++) {
                            AppUtility.arraylist[i] = studentResponseOnDataCode.get(i).getMarks().toString();
                            AppUtility.arraylistMarksAfterChange[i] = studentResponseOnDataCode.get(i).getMarks().toString();
                        }
                        sendMarksAdapter = new SendMarksAdapter(getContext(), recyclerViewOfStudentList, studentResponseOnDataCode);
                        recyclerViewOfStudentList.setAdapter(sendMarksAdapter);
                    } else {
                        ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(), "Error", studentResponseOnDataCode.get(0).getResult().toString());
                    }
                    break;

                case 710:
                    btnSendSMS.setEnabled(true);
                    studentResponse = (List<Student>) data;
                    if (studentResponse.get(0).getResultCode().equals("1")) {
                        spinnerForClass.setSelection(0);
                        spinnerForTestList.setAdapter(null);
                        recyclerViewOfStudentList.setAdapter(null);

                        SendNotificationMethod();
                        Snackbar.make(parentLayout, "" + studentResponse.get(0).getResult(), Snackbar.LENGTH_LONG).show();
                    } else if (studentResponse.get(0).getResultCode().equals("0")) {
                        ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(), "Error", studentResponse.get(0).getResult().toString());
                    } else {
                        ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(), "Error", studentResponse.get(0).getResult().toString());
                    }
                    break;

                case 450:
                    try {
                        sendNotifications = (List<SendNotification>) data;
                        if (sendNotifications.get(0).getResultCode().equals("1")) {
                            Snackbar.make(parentLayout, "Notification send sucessfully", Snackbar.LENGTH_LONG).show();
                        } else if (sendNotifications.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", sendNotifications.get(0).getResult().toString());
                        }
                        sendSmsMethod();
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "Something went wrong please try again", Snackbar.LENGTH_LONG).show();
                    }

                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }

    }

    private void NetworkCallForTestList() {
        try {
            spinnerForTestList.setAdapter(null);
            TestListJSONString = new CreateJsonFunction().CreateJSONForGetData(ClassCodeForNetworkCall, "all", "0");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getClassData(TestListJSONString, AppUtility.GET_DATA_OF_CLASS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "NetworkCallForTestList", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCallForStudentListForSendMark() {
        try {
            recyclerViewOfStudentList.setAdapter(null);
            CreateJSONForStudentList(ClassCodeForNetworkCall, TestCodeForNetworkCall);
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getStudentNameListonDataCode(StudentNameArrayString, AppUtility.GET_STUDENT_LIST_WITH_MARKS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "NetWorkCallForStudentListForSendMark", e);
            pc.showCatchException();
        }
    }

    public void CreateJSONForStudentList(String ClassCode, String Datacode) {
        try {
            Gson gson = new Gson();
            Class classResponse = new Class();
            classResponse.setUserName(AppUtility.KEY_USERNAME);
            classResponse.setClassCode(ClassCode);
            classResponse.setDataCode(Datacode);
            classResponse.setStudentCode("");
            classResponse.setWhichList("all");
            String ClassResponseString = gson.toJson(classResponse);
            StudentNameArrayString = "[" + ClassResponseString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "CreateJSONForStudentList", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            btnSendSMS.setEnabled(true);
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(parentLayout, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.linearSelectDate:
                    datePicker();
                    break;
                case R.id.btnSendSMS:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnSendSMS);
                    bounce_button_class1.BounceMethod();

                    totalMarks = editTextForTotalMarks.getText().toString();
                    if (ClassCodeForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please select class Name", Snackbar.LENGTH_LONG).show();
                    } else if (TestCodeForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please select Test Name", Snackbar.LENGTH_LONG).show();
                    } else if (totalMarks.equals("") || totalMarks.equals(null)) {
                        Snackbar.make(parentLayout, "Please Enter Total Marks", Snackbar.LENGTH_LONG).show();
                    } else if (SelectedDate.equals("") || SelectedDate.equals(null)) {
                        Snackbar.make(parentLayout, "Please Select Date", Snackbar.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < studentResponseOnDataCode.size(); i++) {
                            if (AppUtility.arraylist[i].equals(AppUtility.arraylistMarksAfterChange[i])) {

                            } else {
                                AppUtility.arraylistChangeMarks[i] = AppUtility.arraylist[i];
                                studentResponseOnDataCode.get(i).setMarks(AppUtility.arraylistChangeMarks[i]);
                            }
                        }
                        NetworkCallForSendTestMarks();
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "onClick", e);
            pc.showCatchException();
        }
    }

    private void datePicker() {
        try {
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            SelectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            txtSelectConductedDate.setText(SelectedDate);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    datePickerDialog.dismiss();

                }
            });
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
            datePickerDialog.setCancelable(false);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "MarkAttendance", "datePicker", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForSendTestMarks() {
        try {
            if (studentResponseOnDataCode.size() > 0) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < studentResponseOnDataCode.size(); i++) {
                    String abc = AppUtility.arraylistChangeMarks[i];
                    if (abc != null && !abc.equals("")) {
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("UserName", AppUtility.KEY_USERNAME);
                            student1.put("ClassCode", ClassCodeForNetworkCall);
                            student1.put("DataCode", TestCodeForNetworkCall);
                            student1.put("StudentCode", studentResponseOnDataCode.get(i).getStudentCode());
                            student1.put("Marks", studentResponseOnDataCode.get(i).getMarks());
                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "NetworkCallForSendTestMarks", e);
                            pc.showCatchException();
                        }
                        jsonArray.put(student1);

                    }
                }
                if ((String.valueOf(jsonArray).equals("[]")) || String.valueOf(jsonArray).equals(null)) {
                    Snackbar.make(parentLayout, "Please do some changes", Snackbar.LENGTH_LONG).show();
                } else {
                    btnSendSMS.setEnabled(false);
                    DataAccess dataAcess = new DataAccess(getContext(), this);
                    dataAcess.SendTheoryTestMarks(String.valueOf(jsonArray), AppUtility.SEND_THEORY_TEST_MARKS);
                }
            } else {
                Snackbar.make(parentLayout, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "NetworkCallForSendTestMarks", e);
            pc.showCatchException();
        }
    }

    private void SendNotificationMethod() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Confirmation");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Are you sure ? You want to send notification for " + TestNameForNetworkCall + " test result ?");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("Yes");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class.BounceMethod();
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < studentResponseOnDataCode.size(); i++) {
                        try {
                            String MarksGot = AppUtility.arraylistChangeMarks[i];
                            if ((MarksGot == null) || MarksGot.equals("")) {

                            } else {
                                String NotificatioTitle = "Result Of " + TestNameForNetworkCall + " ";
                                String NotivationText = "";
                                String studentMarks = AppUtility.arraylistChangeMarks[i];
                                if (studentMarks.equals("0000")) {
                                    NotivationText = "Dear student, " + studentResponseOnDataCode.get(i).getStudentName()
                                            + " is absent for " + TestNameForNetworkCall + " test conducted on " + SelectedDate + '\n' + "Regards," + '\n' + AppUtility.App_Name + "." + "";
                                } else {

                                    NotivationText = "Dear student, " + studentResponseOnDataCode.get(i).getStudentName()
                                            + " you got " + studentResponseOnDataCode.get(i).getMarks() + "/" + editTextForTotalMarks.getText() + " marks in " + TestNameForNetworkCall +
                                            " test conducted on " + SelectedDate + '\n' + "Regards," + '\n' + AppUtility.App_Name + "." + "";
                                }
                                String StudentCode = studentResponseOnDataCode.get(i).getStudentCode();
                                JSONObject student1 = new JSONObject();
                                try {
                                    student1.put("ClassCode", ClassCodeForNotification);
                                    student1.put("UserName", AppUtility.KEY_USERNAME);
                                    student1.put("IsNotificationForGuest", "0");
                                    student1.put("NotificationText", NotivationText);
                                    student1.put("NotificationTitle", NotificatioTitle);
                                    student1.put("StudentCode", StudentCode);
                                } catch (JSONException e) {
                                    PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "SendNotificationMethod", e);
                                    pc.showCatchException();
                                }
                                jsonArray.put(student1);
                            }
                        } catch (Exception e) {
                            Log.d("Exception", "" + e);
                        }

                    }
                    if ((String.valueOf(jsonArray).equals("[]")) || String.valueOf(jsonArray).equals(null)) {
                        Snackbar.make(parentLayout, "Please do some changes", Snackbar.LENGTH_LONG).show();
                    } else {

                    }
                    dialog.dismiss();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("No");
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();

                    sendSmsMethod();
                }
            });

            dialog.show();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "SendNotificationMethod", e);
            pc.showCatchException();
        }
    }


    public void sendSmsMethod() {
        if (isSMSPermissionGranted()) {
            SendSMSDialog();
        }
    }

    public void SendSMSDialog() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sms_dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Send Marks");
            final TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Are you sure ? You want to send marks through SMS ?");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class.BounceMethod();
                    for (int i = 0; i < studentResponseOnDataCode.size(); i++) {
                        try {
                            String MarksGot = AppUtility.arraylistChangeMarks[i];
                            if ((MarksGot == null) || MarksGot.equals("")) {

                            } else {
                                String marks = "";
                                String studentMarks = AppUtility.arraylistChangeMarks[i];
                                if (studentMarks.equals("0000")) {
                                    marks = "Dear parents, Your son/daughter " + studentResponseOnDataCode.get(i).getStudentName()
                                            + " is absent for " + TestNameForNetworkCall + " test conducted on " + SelectedDate + " regards, " + AppUtility.App_Name;
                                } else {

                                    marks = "Dear parents, Your son/daughter " + studentResponseOnDataCode.get(i).getStudentName()
                                            + " got " + studentResponseOnDataCode.get(i).getMarks() + "/" + editTextForTotalMarks.getText() + " marks in " + TestNameForNetworkCall +
                                            " test conducted on " + SelectedDate + " regards, " + AppUtility.App_Name;
                                }
//
                                Boolean result = sendSMS(studentResponseOnDataCode.get(i).getMobileNumber(), marks);
                                if (result == false) {
                                    SMSSendConfirmation("Message Not Sent");
                                }
                            }
                        } catch (Exception e) {
                            Log.d("Exception", "" + e);
                        }
                    }
                    SMSSendConfirmation("Message Sent Sucessfully");
                    editTextForTotalMarks.setText(null);
                    txtSelectConductedDate.setText(R.string.fa_calender);
                    SelectedDate = "";
                    totalMarks = "";
                    dialog.dismiss();

                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTextForTotalMarks.setText(null);

                    txtSelectConductedDate.setText(R.string.fa_calender);
                    SelectedDate = "";
                    totalMarks = "";
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });
            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "SendSMSDialog", e);
            pc.showCatchException();
        }
    }

    public void SMSSendConfirmation(String msg) {
        try {

            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Reply");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText(msg);
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "SendStudentTheoryTestMarks", "SMSSendConfirmation", e);
            pc.showCatchException();
        }
    }

    public Boolean sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isSMSPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 0);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
        final MenuItem item = menu.findItem(R.id.add);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add:
                Intent intentGivenExamList = new Intent(getContext(), AddTheoryTestActivity.class);
                startActivity(intentGivenExamList);
                break;


            default:
                break;
        }

        return true;
    }
}

