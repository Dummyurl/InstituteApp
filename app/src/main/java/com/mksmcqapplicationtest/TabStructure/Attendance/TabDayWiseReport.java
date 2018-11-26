package com.mksmcqapplicationtest.TabStructure.Attendance;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.IndividualReportListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TabDayWiseReport extends Fragment implements View.OnClickListener,ResponseListener {

    Spinner spinnerSelectStatusForReport, spinner;
    Button btnShowReport, btnSendSMS;
    RecyclerView recyclerViewUserNameList;
    int count, j = 0, count1;
    List<Class> classResponse;
    String[] UserNameDropDown = new String[4];
    String SelectedStatus, StatusName, SelectedDate, AttendanceReportArrayString, ClassNameJSONString;
    List<Response> response;
    boolean isOkayClicked = false;
    Context context;
    View parentLayout;
    String[] UserNameDropDownForClass = new String[1];
    String ClassCode, ClassName;
    List<Student> StudentResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_day_wise_report, container, false);
        try {
            this.context = getContext().getApplicationContext();
            setUI();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerViewUserNameList.setLayoutManager(layoutManager);
            recyclerViewUserNameList.setItemAnimator(new DefaultItemAnimator());
            spinnerSelectStatusForReport.setAdapter(null);

            UserNameDropDown[0] = "Select Status";
            UserNameDropDown[1] = "Present";
            UserNameDropDown[2] = "Absent";
            UserNameDropDown[3] = "Holiday";


            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDown);
            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSelectStatusForReport.setAdapter(Select_Category_Adapter);

            btnShowReport.setOnClickListener(this);
            btnSendSMS.setOnClickListener(this);
            spinnerSelectStatusForReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String spinner1 = parent.getItemAtPosition(position).toString();
                    count = position; //this would give you the id of the selected item
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCall();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        spinner = (Spinner) parentLayout.findViewById(R.id.spinnerForClass);
        spinnerSelectStatusForReport = (Spinner) parentLayout.findViewById(R.id.spinnerSelectUserNameForReport1);
        btnShowReport = (Button) parentLayout.findViewById(R.id.btnSeeReport1);
        btnSendSMS = (Button) parentLayout.findViewById(R.id.btnSendSMS);
        btnSendSMS.setVisibility(View.GONE);
        recyclerViewUserNameList = (RecyclerView) parentLayout.findViewById(R.id.recyclerViewOfAttendance1);
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Day_Wise_Report));

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

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnSeeReport1:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnShowReport);
                    bounce_button_class1.BounceMethod();

                    btnSendSMS.setVisibility(View.GONE);
                    recyclerViewUserNameList.setAdapter(null);
                    SelectedStatus = spinnerSelectStatusForReport.getSelectedItem().toString();
                    if (count1 != 0) {
                        if (SelectedStatus.equals("Select Status")) {
                            Snackbar.make(parentLayout, "Select Status For Report", Snackbar.LENGTH_LONG).show();
                        } else {
                            if (count != 0) {

                                if (count == 1) {
                                    SelectedStatus = "P";
                                    StatusName = "Present";
                                } else if (count == 2) {
                                    SelectedStatus = "A";
                                    StatusName = "Absent";
                                } else if (count == 3) {
                                    SelectedStatus = "H";
                                    StatusName = "Holiday";
                                }
                                btnShowReport.setEnabled(false);
                                datePicker();
                            }
                        }
                    } else {
                        Snackbar.make(parentLayout, "Select Class For Report", Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btnSendSMS:
                    Bounce_Button_Class bounce_button_class2 = new Bounce_Button_Class(getContext(), btnSendSMS);
                    bounce_button_class2.BounceMethod();

                    try {
                        if (isSMSPermissionGranted()) {
                            btnSendSMS.setEnabled(false);
                            SendSMSDialog();
                        }
                    } catch (Exception ex) {
                        Log.d("SMS Excpetion", "" + ex);
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "OnClick", e);
            pc.showCatchException();
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
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            switch (requestCode) {
                case 0: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        SendSMSDialog();
                    } else {
                    }
                    return;
                }

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "onRequestPermissionsResult", e);
            pc.showCatchException();
        }
    }

    public void SendSMSDialog() {
        try {
            btnSendSMS.setEnabled(true);
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sms_dialog_daywise);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("SMS");
            TextView txtClassName = (TextView) dialog.findViewById(R.id.claaa);
            txtClassName.setText(getResources().getString(R.string.app_name));
            final EditText txtMessage = (EditText) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("\"" + "Name Of Student" + "\"");
            SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat Stringformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = Dateformat.parse(SelectedDate);
                SelectedDate = Stringformat.format(date);
            } catch (ParseException e) {
                PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "SendSMSDialog", e);
                pc.showCatchException();
            }

            txtMessage.setText(" Dear Parents," + '\n' + '\n' + "Your Son/Daughter <Name> is " + StatusName + " For " + "\"" + ClassName + "\"" + " On " + SelectedDate + "." + '\n' + '\n' + "Regards," + '\n' + AppUtility.App_Name + ".");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("SEND");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class1.BounceMethod();


                    for (int i = 0; i < AppUtility.IndividualListResponse.size(); i++) {
//                    String abcc=" Dear Parents," + '\n' + '\n' + "Your Son/Daughter " + "\"" + AppUtility.IndividualListResponse.get(i).getStudentName() + "\"" + " is " + '\n' + "" + StatusName + " For " + "\"" + ClassName + "\"" + " On " + SelectedDate+"." + '\n' + '\n' + "Regards," + '\n' + AppUtility.App_Name+"."+"";
                        String abcc = txtMessage.getText().toString();
                        String abc = abcc.replace("<Name>", AppUtility.IndividualListResponse.get(i).getStudentName());
                        Boolean result = sendSMS(AppUtility.IndividualListResponse.get(i).getMobileNumber(), abc);
                        if (result == false) {
                            SMSSendConfirmation("Message Not Sent");
                        }
                    }
                    SMSSendConfirmation("Message Sent Sucessfully");
                    dialog.dismiss();

                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("CANCEL");
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class1.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "SendSMSDialog", e);
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
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class1.BounceMethod();
                    dialog.dismiss();
                }
            });
            Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "SMSSendConfirmation", e);
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

    private void datePicker() {
        try {
            btnShowReport.setEnabled(true);
            int mYear, mMonth, mDay;
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    if (isOkayClicked) {
                        SelectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        if (AppUtility.IsTeacher.equals("G")) {
                            if (AppUtility.IsAdvertiesVisibleForGuest) {
//                                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//                                interstitialAdvertiesClass.showInterstitialAdver();

                                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                                advertiesClass.ShowAdver();
                            } else if (AppUtility.IsAdvertiesVisible) {
//                                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//                                interstitialAdvertiesClass.showInterstitialAdver();

                                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                                advertiesClass.ShowAdver();
                            }
                        } else {
                            if (AppUtility.IsAdvertiesVisible) {
//                                InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//                                interstitialAdvertiesClass.showInterstitialAdver();

                                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                                advertiesClass.ShowAdver();
                            }
                        }
                        NetworkCall();
                    }
                    isOkayClicked = false;
                }
            };
            final DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), datePickerListener,
                    mYear, mMonth, mDay);

            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                dialog.cancel();
                                isOkayClicked = false;
                            }
                        }
                    });

            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                isOkayClicked = true;
                                DatePicker datePicker = datePickerDialog
                                        .getDatePicker();
                                datePickerListener.onDateSet(datePicker,
                                        datePicker.getYear(),
                                        datePicker.getMonth(),
                                        datePicker.getDayOfMonth());
                            }
                        }
                    });
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.setCancelable(false);
            datePickerDialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "DayWiseReport", "datePicker", e);
            pc.showCatchException();
        }
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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "DayWiseReport", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }


    private void NetworkCall() {
        try {
            recyclerViewUserNameList.setAdapter(null);
            CreateJSON();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getIndividualStatusReport(AttendanceReportArrayString, AppUtility.INDIVIDUAL_REPORT_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "DayWiseReport", "NetworkCall", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            Student Stauts = new Student();
            Gson gson = new Gson();
            Stauts.setStatus(SelectedStatus);
            Stauts.setSelectedDate(SelectedDate);
            Stauts.setClassCode(ClassCode);
            String SubmitAdJson = gson.toJson(Stauts);
            AttendanceReportArrayString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "DayWiseReport", "CreateJSON", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 440:
                    StudentResponse = (List<Student>) data;
                    try {
                        AppUtility.IndividualListResponse.clear();
                        AppUtility.IndividualListResponse = new ArrayList<>();
                        for (int i = 0; i < StudentResponse.size(); i++) {
                            if (StudentResponse.get(i).getResult().trim().equals("Data Found")) {
                                btnSendSMS.setVisibility(View.VISIBLE);
                                Student AttendanceListResponse = new Student();
                                AttendanceListResponse.setStudentName(StudentResponse.get(i).getStudentName());
                                AttendanceListResponse.setResult(StudentResponse.get(i).getResult());
                                AttendanceListResponse.setStudentCode(StudentResponse.get(i).getStudentCode());
                                AttendanceListResponse.setMobileNumber(StudentResponse.get(i).getMobileNumber());
                                AppUtility.IndividualListResponse.add(AttendanceListResponse);
                            }
                        }
                        if (AppUtility.IndividualListResponse.size() > 0) {
                            recyclerViewUserNameList.setVisibility(View.VISIBLE);
                            IndividualReportListAdapter attendanceListAdapter = new IndividualReportListAdapter(getContext(), recyclerViewUserNameList, AppUtility.IndividualListResponse);
                            recyclerViewUserNameList.setAdapter(attendanceListAdapter);
                            Toast.makeText(getContext(), "Total: " + String.valueOf(AppUtility.IndividualListResponse.size()), Toast.LENGTH_LONG).show();
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No Data Found");
//                            Snackbar.make(parentLayout, "No Data Found", Snackbar.LENGTH_LONG).show();
                            recyclerViewUserNameList.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception ex) {
                    }
                    break;

                case 170:
                    classResponse = (List<Class>) data;
                    if (classResponse != null) {
                        btnSendSMS.setVisibility(View.GONE);
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
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {

                                        ClassCode = AppUtility.CLASS_RESPONSE.get(position - 1).getClassCode();
                                        ClassName = AppUtility.CLASS_RESPONSE.get(position - 1).getClassName();
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
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "DayWiseReport", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "DayWiseReport", "noResponse", e);
            pc.showCatchException();
        }
    }

}

