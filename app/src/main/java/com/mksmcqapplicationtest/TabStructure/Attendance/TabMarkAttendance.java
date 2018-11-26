package com.mksmcqapplicationtest.TabStructure.Attendance;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.AttendanceListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TabMarkAttendance extends Fragment implements View.OnClickListener,ResponseListener  {

    List<Class> classResponse;
    SharedPreferences sharedPreferences;
    View parentLayout;
    RecyclerView recyclerViewAttendanceList;
    List<Student> studentResponse;
    Button btnMarkAttendance;
    RadioButton radioButtonPresenty, radioButtonAbsenty, radioButtonHoliday, radioButtonCredit;
    CheckBox chkSelectAll;
    String SelectedDate, ClassCodeForNetworkCall, ClassNameJSONString, StudentNameArrayString;
    JSONArray jsonArray = new JSONArray();
    List<Response> responses;
    AttendanceListAdapter attendanceListAdapter;
    String[] UserNameDropDownForClass = new String[1];
    int j = 0, count;
    Spinner spinner;
    String ClassCodeForDataBaseResponse;
    int btnClickCount = 0;
    CustomTextViewBold txtactionbartitle;
    Toast toast;
    public static final String PREFS_NAME = "MyPrefsFile";
    ProgressDialogShow progressDialogClickClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_mark_attendance, container, false);

        try {
            setUI();
            sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, 0);
            btnClickCount = sharedPreferences.getInt("btnClick", 0);
            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
            if (AppUtility.IsDemoApplication) {
                btnMarkAttendance.setClickable(false);
                btnMarkAttendance.setEnabled(false);
            } else {
                btnMarkAttendance.setClickable(true);
                btnMarkAttendance.setEnabled(true);
            }

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCall();
            }


            radioButtonHoliday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        radioButtonPresenty.setChecked(false);
                        radioButtonAbsenty.setChecked(false);

                        AppUtility.KEY_ATTENDANCE = "H";
                    }
                }
            });


            radioButtonAbsenty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        radioButtonPresenty.setChecked(false);
                        radioButtonHoliday.setChecked(false);
                        AppUtility.KEY_ATTENDANCE = "A";
                    }
                }
            });

            radioButtonPresenty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        radioButtonHoliday.setChecked(false);
                        radioButtonAbsenty.setChecked(false);
                        AppUtility.KEY_ATTENDANCE = "P";
                    }
                }
            });

            chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                        AppUtility.SELECT_ALL_IS_CHECKED = "Y";
                        if (AppUtility.STUDENT_RESPONSE.size() > 0) {
                            attendanceListAdapter.selectAll();
                        } else {
                            Snackbar.make(parentLayout, "No Student For Mark Attendance", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        AppUtility.SELECT_ALL_IS_CHECKED = "N";
                        if (AppUtility.STUDENT_RESPONSE.size() > 0) {
                            attendanceListAdapter.deselectAll();
                            attendanceListAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(parentLayout, "No Student For Mark Attendance", Snackbar.LENGTH_LONG).show();
                        }

                    }
                }
            });


            btnMarkAttendance.setOnClickListener(this);


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "MarkAttendance", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        spinner = (Spinner) parentLayout.findViewById(R.id.spinnerForClass);
        spinner.setAdapter(null);
        radioButtonPresenty = (RadioButton) parentLayout.findViewById(R.id.rdbPresenty);
        radioButtonAbsenty = (RadioButton) parentLayout.findViewById(R.id.rdbAbsenty);
        radioButtonHoliday = (RadioButton) parentLayout.findViewById(R.id.rdbHoliday);
        chkSelectAll = (CheckBox) parentLayout.findViewById(R.id.chkSelectAll);
        chkSelectAll.setVisibility(View.GONE);
        recyclerViewAttendanceList = (RecyclerView) parentLayout.findViewById(R.id.recyclerViewOfAttendance);
        btnMarkAttendance = (Button) parentLayout.findViewById(R.id.btnMarkAttendance);
        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Mark_Attendance));

        ImageButton imageadd = getActivity().findViewById(R.id.imageadd);
        imageadd.setVisibility(View.GONE);

        ImageButton imageButtonBackPress = getActivity().findViewById(R.id.imagviewbackpress);
        imageButtonBackPress.setVisibility(View.VISIBLE);
        imageButtonBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    private void NetWorkCall() {
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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MarkAttendance", "NetWorkCall", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onClick(View view) {
        try {
            Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnMarkAttendance);
            bounce_button_class1.BounceMethod();

            if (ClassCodeForNetworkCall.equals("0")) {
                Snackbar.make(view, "Select Atleast One Class Name ", Snackbar.LENGTH_LONG).show();
            } else if (!(radioButtonHoliday.isChecked() || radioButtonPresenty.isChecked() || radioButtonAbsenty.isChecked())) {
                Snackbar.make(view, "Select atleast Presenty/Absenty/ Holiday", Snackbar.LENGTH_LONG).show();
            } else if (radioButtonHoliday.isChecked() || radioButtonPresenty.isChecked() || radioButtonAbsenty.isChecked()) {
                if (AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT > 0) {
                    btnMarkAttendance.setEnabled(false);
                    datePicker();

                } else {
                    Snackbar.make(view, "Select Atleast One Student Name ", Snackbar.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "MarkAttendance", "onClick", e);
            pc.showCatchException();
        }
    }

    private void datePicker() {
        try {
            btnMarkAttendance.setEnabled(true);
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            btnClickCount++;
                            SelectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("btnClick", btnClickCount);
                            editor.commit();
                            btnMarkAttendance.setClickable(false);
                            CreateJSON();
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

    private void CreateJSON() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                String abc = AppUtility.STUDENT_RESPONSE.get(i).getCheck();
                if (abc != null) {
                    if (abc.equals("Y")) {
                        String userId = AppUtility.STUDENT_RESPONSE.get(i).getStudentCode();
                        JSONObject student1 = new JSONObject();
                        try {
                            student1.put("StudentCode", userId);
                            student1.put("SelectedDate", SelectedDate);
                            student1.put("AttendanceStatus", AppUtility.KEY_ATTENDANCE);
                            student1.put("ClassCode", ClassCodeForDataBaseResponse);
                        } catch (JSONException e) {
                            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "MarkAttendance", "CreateJSON", e);
                            pc.showCatchException();
                        }
                        jsonArray.put(student1);
                    }
                }
            }
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCall(jsonArray);
            } else {
                DataAccess dataAccess = new DataAccess(getContext(), this);
                dataAccess.saveAttendanceToDisk(String.valueOf(btnClickCount), jsonArray);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "MarkAttendance", "CreateJSON", e);
            pc.showCatchException();
        }
    }
    private void NetworkCall(JSONArray jsonArray1) {
        try {
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.MarkAttendance(String.valueOf(jsonArray1), AppUtility.MARK_ATTENDANCE);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MarkAttendance", "NetworkCall", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        btnMarkAttendance.setClickable(true);
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            btnMarkAttendance.setClickable(true);
            switch (requestCode) {
                case 170:
                    classResponse = (List<Class>) data;
                    if (classResponse != null) {
                        if (classResponse.get(0).getResult().equals("Suessfull")) {
                            AppUtility.CLASS_RESPONSE = classResponse;

                            UserNameDropDownForClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j = 0;
                            UserNameDropDownForClass[j] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j = j + 1;
                                UserNameDropDownForClass[j] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForClass);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(Select_Category_Adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count = position; //this would give you the id of the selected item
                                    if (count != 0) {
                                        AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                                        String classCode = classResponse.get(count - 1).getClassCode();
                                        recyclerViewAttendanceList.setAdapter(null);
                                        ClassCodeForDataBaseResponse = classCode;
                                        NetWorkCallForStudentList(classCode);
                                        chkSelectAll.setChecked(false);
                                        ClassCodeForNetworkCall = "1";

                                    } else {
                                        recyclerViewAttendanceList.setAdapter(null);
                                        chkSelectAll.setVisibility(View.GONE);
                                        chkSelectAll.setChecked(false);
                                        ClassCodeForNetworkCall = "0";
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
                        Snackbar.make(parentLayout, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCall();
                                    }
                                }).show();
                    }
                    break;
                case 180:
                    studentResponse = (List<Student>) data;
                    if (studentResponse != null) {
                        chkSelectAll.setVisibility(View.VISIBLE);
                        if (studentResponse.get(0).getResult().equals("Suessfull")) {
                            AppUtility.STUDENT_RESPONSE = studentResponse;
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerViewAttendanceList.setLayoutManager(layoutManager);
                            recyclerViewAttendanceList.setItemAnimator(new DefaultItemAnimator());
                            attendanceListAdapter = new AttendanceListAdapter(getContext(), recyclerViewAttendanceList, AppUtility.STUDENT_RESPONSE);
                            recyclerViewAttendanceList.setAdapter(attendanceListAdapter);
                            toast = Toast.makeText(getContext(), "Total: " + String.valueOf(studentResponse.size()), Toast.LENGTH_LONG);
                            toast.show();
                        } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());
                        } else if (studentResponse.get(0).getResult().equals("Something went wrong")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());
                        }
                    } else {
                        Snackbar.make(parentLayout, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCall();
                                    }
                                }).show();
                    }
                    break;

                case 500:
                    progressDialogClickClass.dismiss();
                    try {
                        studentResponse = (List<Student>) data;
                        if (studentResponse != null) {
                            chkSelectAll.setVisibility(View.VISIBLE);
                            if (studentResponse.get(0).getResult().equals("Suessfull")) {
                                List<Student> studentResponsee = new ArrayList<>();
                                for (int i = 0; i < studentResponse.size(); i++) {
                                    if (studentResponse.get(i).getClassCode().equals(ClassCodeForDataBaseResponse)) {
                                        Student s1 = new Student();
//                                s1.add(studentResponse);
                                        s1.setStudentName(studentResponse.get(i).getStudentName());
                                        s1.setStudentCode(studentResponse.get(i).getStudentCode());
                                        s1.setClassCode(studentResponse.get(i).getClassCode());
                                        s1.setProfile(studentResponsee.get(i).getProfile());
                                        studentResponsee.add(s1);
                                    } else {
                                        Log.d("Exception", "Error");
                                    }


                                }
                                AppUtility.STUDENT_RESPONSE = studentResponsee;
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerViewAttendanceList.setLayoutManager(layoutManager);
                                recyclerViewAttendanceList.setItemAnimator(new DefaultItemAnimator());
                                attendanceListAdapter = new AttendanceListAdapter(getContext(), recyclerViewAttendanceList, AppUtility.STUDENT_RESPONSE);
                                recyclerViewAttendanceList.setAdapter(attendanceListAdapter);

                            } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());
                            } else if (studentResponse.get(0).getResult().equals("Something went wrong")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());

                            }
                        } else {
                            Snackbar.make(parentLayout, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            NetWorkCall();
                                        }
                                    }).show();
                        }
                    } catch (Exception ex) {
                        Log.d("Exception", "" + ex);
                    }
                    break;


                case 1900:
                    try {
                        responses = (List<Response>) data;
                        if (responses.get(0).getResultCode().equals("1")) {
                            Snackbar.make(parentLayout, "Attendance Save Sucessfully", Snackbar.LENGTH_LONG).show();
                            radioButtonPresenty.setChecked(false);
                            radioButtonAbsenty.setChecked(false);
                            radioButtonHoliday.setChecked(false);
                            chkSelectAll.setChecked(false);
                            attendanceListAdapter.deselectAll();
                            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                        } else if (responses.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",responses.get(0).getResult().toString());
                        }

                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "" + ex, Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case 190:
                    try {
                        responses = (List<Response>) data;
                        if (responses.get(0).getResultCode().equals("1")) {
                            Snackbar.make(parentLayout, "Attendance Mark Sucessfully", Snackbar.LENGTH_LONG).show();
                            radioButtonPresenty.setChecked(false);
                            radioButtonAbsenty.setChecked(false);
                            radioButtonHoliday.setChecked(false);
                            chkSelectAll.setChecked(false);
                            attendanceListAdapter.deselectAll();
                            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                        } else if (responses.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",responses.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "" + ex, Snackbar.LENGTH_LONG).show();
                    }
                    if (AppUtility.IsAdvertiesVisible) {
//                        InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//                        interstitialAdvertiesClass.showInterstitialAdver();
                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MarkAttendance", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            btnMarkAttendance.setClickable(true);
            if (progressDialogClickClass != null) {
                progressDialogClickClass.dismiss();
            }
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "AddFees", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCallForStudentList(String classCode) {
        try {
            StudentNameArrayString = new CreateJsonFunction().CreateJSONForStudentList(classCode, "active");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getStudentNameList(StudentNameArrayString, AppUtility.GET_STUDENT_NAME_LIST);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "AddFees", "NetWorkCallForStudentList", e);
            pc.showCatchException();
        }

    }


}

