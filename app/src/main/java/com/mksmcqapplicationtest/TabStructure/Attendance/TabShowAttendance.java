package com.mksmcqapplicationtest.TabStructure.Attendance;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.MonthYearPicker;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.AttendanceResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class TabShowAttendance extends Fragment implements View.OnClickListener,ResponseListener {

    String TodaysDate;
    View parentLayout;
    List<Class> classResponse;
    Spinner spinner, spinnerForClass;
    List<Student> studentResponse;
    ArrayList<String> Absentdates = new ArrayList<String>();
    ArrayList<String> Holidaydates = new ArrayList<String>();
    ArrayList<String> Presentdates = new ArrayList<String>();
    ArrayList<String> Creditdates = new ArrayList<String>();
    MaterialCalendarView calendarView;
    Date holidayDay, PresentDay, AbsentDay, CreditDay;
    int numberOfDays;
    Button btnShowReport;
    String AttendanceReportArrayString, StudentNameArrayString;
    String SelectedDate, SelectedUsername;
    String SelectedYear, SelectedMonthOfYear;
    TextView txtNoDataFound;
    String[] UserNameDropDown = new String[1];
    String[] UserNameDropDownForClass = new String[1];
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    int count, count1;
    int j = 0, j1 = 0, k = 0;
    LinearLayout linearLayoutOfColorDeclaration, LinearlayoutclassStudentHide;
    TextView txtPresentDayCount, txtAbsentDayCount, txtHoliDayCount;
    String StudentCodeForNetworkCall, ClassNameJSONString;
    List<AttendanceResponse> response;
    private MonthYearPicker myp;
    RelativeLayout RelativeForHide;
    TextView txtClassName, txtStudentName;
    String StrClassName, StrStudentName;
    CustomTextViewBold txtactionbartitle;
    ProgressDialogShow progressDialogClickClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_show_attwndance, container, false);

        try {
            setUI();
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetWorkCall();
            }

            spinner.setAdapter(null);
            btnShowReport.setOnClickListener(this);
            myp = new MonthYearPicker(getActivity());
            myp.build(new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SelectedYear = String.valueOf(myp.getSelectedYear());
                    SelectedMonthOfYear = String.valueOf(myp.getSelectedMonth() + 1);
                    numberOfDays = numberOfDaysInMonth(myp.getSelectedMonth(), myp.getSelectedYear());
                    SelectedDate = myp.getSelectedMonth() + 1 + "/" + myp.getSelectedYear();

                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                pddialog.dismiss();
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        spinnerForClass = (Spinner) parentLayout.findViewById(R.id.spinnerForClass);
        spinnerForClass.setAdapter(null);

        spinner = (Spinner) parentLayout.findViewById(R.id.spinnerForStudentName);
        linearLayoutOfColorDeclaration = (LinearLayout) parentLayout.findViewById(R.id.layoutOfColorDeclaration);
//        LinearlayoutclassStudentHide = (LinearLayout) parentLayout.findViewById(R.id.LinearlayoutclassStudentHide);
        btnShowReport = (Button) parentLayout.findViewById(R.id.btnSeeReport);
        calendarView = (MaterialCalendarView) parentLayout.findViewById(R.id.calendarView);
        txtNoDataFound = (TextView) parentLayout.findViewById(R.id.txtnodatafound);
        txtPresentDayCount = (TextView) parentLayout.findViewById(R.id.txtPresentDayCount);
        txtAbsentDayCount = (TextView) parentLayout.findViewById(R.id.txtAbsentDayCount);
        txtHoliDayCount = (TextView) parentLayout.findViewById(R.id.txtHoliDayCount);
        txtClassName = (TextView) parentLayout.findViewById(R.id.txtClassName);
        txtStudentName = (TextView) parentLayout.findViewById(R.id.txtStudentName);

        txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Show_Fees));

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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowAttendance", "NetWorkCall", e);
            pc.showCatchException();
        }
    }


    public void show(View view) {
        try {
            btnShowReport.setEnabled(true);
            calendarView.removeDecorators();
            myp.show();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "show", e);
            pc.showCatchException();
        }

    }

    private void setCustomResourceForDates() {
        try {
            Calendar cal = Calendar.getInstance();
            String parts[] = SelectedDate.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            int day = 0;
            calendarView.setCurrentDate(calendar);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");

            Date date = new Date();

            for (int i = 0; i < Holidaydates.size(); i++) {
                String inputString2 = Holidaydates.get(i);
                String inputString1 = myFormat.format(date);


                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    holidayDay = date2;
                    long diff = date2.getTime() - date1.getTime();
                    long datee = diff / (1000 * 60 * 60 * 24);
                    day = (int) datee;
                } catch (ParseException e) {
                    PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "setCustomResourceForDates", e);
                    pc.showCatchException();
                }
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, day);
                calendarView.setCurrentDate(cal);
                Holidaycolors();

            }
            Date Adate = new Date();
            int Result = 31 - numberOfDays;
            int AbsentTotalCount = Absentdates.size() - Result;
            for (int i = 0; i < Absentdates.size(); i++) {
                String inputString2 = Absentdates.get(i);
                String inputString1 = myFormat.format(Adate);


                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    AbsentDay = date2;
                    long diff = date2.getTime() - date1.getTime();
                    long datee = diff / (1000 * 60 * 60 * 24);
                    day = (int) datee;
                } catch (ParseException e) {
                    PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "setCustomResourceForDates", e);
                    pc.showCatchException();
                }
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, day);
                calendarView.setCurrentDate(cal);
                Absentcolors();

            }

            Date Cdate = new Date();

            for (int i = 0; i < Creditdates.size(); i++) {
                String inputString2 = Creditdates.get(i);
                String inputString1 = myFormat.format(Cdate);
                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    CreditDay = date2;
                    long diff = date2.getTime() - date1.getTime();
                    long datee = diff / (1000 * 60 * 60 * 24);
                    day = (int) datee;
                } catch (ParseException e) {
                    PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "setCustomResourceForDates", e);
                    pc.showCatchException();
                }
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, day);
                calendarView.setCurrentDate(cal);
                Creditcolors();

            }

            Date Pdate = new Date();
            for (int i = 0; i < Presentdates.size(); i++) {
                String inputString2 = Presentdates.get(i);
                String inputString1 = myFormat.format(Pdate);


                try {
                    Date date1 = myFormat.parse(inputString1);
                    Date date2 = myFormat.parse(inputString2);
                    PresentDay = date2;
                    long diff = date2.getTime() - date1.getTime();
                    long datee = diff / (1000 * 60 * 60 * 24);
                    day = (int) datee;
                } catch (ParseException e) {
                    PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "setCustomResourceForDates", e);
                    pc.showCatchException();
                }
                cal = Calendar.getInstance();
                cal.add(Calendar.DATE, day);
                calendarView.setCurrentDate(cal);
                Presentcolors();

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "setCustomResourceForDates", e);
            pc.showCatchException();
        }

    }

    public void Holidaycolors() {
        try {
            if (calendarView != null) {
                calendarView.addDecorator(new OneDayDecorator(holidayDay, Color.parseColor("#5fb0c9")));
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "Holidaycolors", e);
            pc.showCatchException();
        }
    }

    public void Absentcolors() {
        try {
            if (calendarView != null) {
                calendarView.addDecorator(new OneDayDecorator(AbsentDay, Color.RED));
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "Absentcolors", e);
            pc.showCatchException();
        }
    }

    public void Presentcolors() {
        try {
            if (calendarView != null) {
                calendarView.addDecorator(new OneDayDecorator(PresentDay, Color.GREEN));
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "Presentcolors", e);
            pc.showCatchException();
        }
    }

    public void Creditcolors() {
        try {
            if (calendarView != null) {
                calendarView.addDecorator(new OneDayDecorator(CreditDay, Color.BLACK));
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "Creditcolors", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnShowReport);
            bounce_button_class1.BounceMethod();

            calendarView.removeDecorators();
            if (spinnerForClass.getSelectedItem() != null) {
                String ClasssName = spinnerForClass.getSelectedItem().toString();
                if (!(ClasssName.equals("Select Class Name..."))) {
                    if ((spinner.getSelectedItem() != null)) {
                        SelectedUsername = spinner.getSelectedItem().toString();
                        if (SelectedUsername.equals("Select Student Name...")) {
                            //TODO S
                            Snackbar.make(view, "Select Student Name For Attendance", Snackbar.LENGTH_LONG).show();
                        } else {
                            btnShowReport.setEnabled(false);
                            show(view);

                        }
                    } else {
                        Snackbar.make(view, "Select Student Name For Attendance", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "Select Class Name For Attendance", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(view, "Select Class Name For Attendance", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowAttendance", "onClick", ex);
            pc.showCatchException();
        }
    }


    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onResponse(Object data) {
        progressDialogClickClass.dismiss();
        Absentdates.clear();
        Holidaydates.clear();
        Presentdates.clear();
        Creditdates.clear();
        PresentDay = null;
        holidayDay = null;
        AbsentDay = null;
        CreditDay = null;

        response = (List<AttendanceResponse>) data;
        try {
            String ResponceMessage = response.get(0).getReturnResponce();
            if (ResponceMessage.equals("Successful")) {
                TodaysDate = response.get(0).getDate();
                String[] separateddate = TodaysDate.split("-");
                int todaydate = Integer.parseInt(separateddate[0]);
                int todaymonth = Integer.parseInt(separateddate[1]);
                int todayyear = Integer.parseInt(separateddate[2]);

                String attendance = response.get(0).getAttendance().trim();
                String abc = attendance.substring(0, attendance.length() - 0).trim();
                final String[] separated = abc.split(",");
                for (int i = 0; i < separated.length; i++) {

                    switch (separated[i].toString()) {
                        case "P":
                            if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                Presentdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                            } else {
                                Presentdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                            }
                            break;

                        case "p":
                            if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                Presentdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                            } else {
                                Presentdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                            }
                            break;

                        case "H":
                            if (todaymonth == Integer.parseInt(SelectedMonthOfYear) && todayyear == Integer.parseInt(SelectedYear)) {
                                if (i < todaydate) {
                                    if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                        Holidaydates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                    } else {
                                        Holidaydates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                    }
                                } else {
                                    if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                        Creditdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                    } else {
                                        Creditdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                    }
                                }
                            } else {
                                if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                    Holidaydates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                } else {
                                    Holidaydates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                }
                            }
                            break;
                        case "h":

                            if (todaymonth == Integer.parseInt(SelectedMonthOfYear) && todayyear == Integer.parseInt(SelectedYear)) {
                                if (i < todaydate) {
                                    if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                        Holidaydates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                    } else {
                                        Holidaydates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                    }
                                } else {
                                    if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                        Creditdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                    } else {
                                        Creditdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                    }
                                }
                            } else {
                                if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                    Holidaydates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                                } else {
                                    Holidaydates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                                }
                            }
                            break;
                        case "A":

                            if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                Absentdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                            } else {
                                Absentdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                            }
                            break;

                        case "a":

                            if (i < 10 || Integer.parseInt(SelectedMonthOfYear) < 10) {
                                Absentdates.add("0" + String.valueOf(i + 1) + "-" + "0" + SelectedMonthOfYear + "-" + SelectedYear);
                            } else {
                                Absentdates.add(String.valueOf(i + 1) + "-" + SelectedMonthOfYear + "-" + SelectedYear);
                            }
                            break;

                    }
                }


                calendarView.setVisibility(View.VISIBLE);
                txtNoDataFound.setVisibility(View.GONE);
                linearLayoutOfColorDeclaration.setVisibility(View.VISIBLE);
                LinearlayoutclassStudentHide.setVisibility(View.VISIBLE);
                txtPresentDayCount.setText("Present =" + Presentdates.size());
                int Result = 31 - numberOfDays;
                int AbsentTotalCount = Absentdates.size() - Result;
                txtAbsentDayCount.setText("Absent =" + Absentdates.size());
                txtHoliDayCount.setText("Holiday =" + Holidaydates.size());
//                txtClassName.setText( StrClassName);
//                txtStudentName.setText( StrStudentName);
                txtClassName.setText("Class =" + StrClassName);
                txtStudentName.setText("Student =" + StrStudentName);
                //txtCreditCount.setText("Credit =" + Creditdates.size());

                setCustomResourceForDates();

            } else {
                calendarView.setVisibility(View.GONE);
                linearLayoutOfColorDeclaration.setVisibility(View.INVISIBLE);
                LinearlayoutclassStudentHide.setVisibility(View.INVISIBLE);
                txtNoDataFound.setVisibility(View.VISIBLE);
                txtNoDataFound.setText(ResponceMessage);
                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                showErrorPopUp.ShowPopUp(getContext(),"Error",ResponceMessage);
                //Toast.makeText(getContext(), ResponceMessage, Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Snackbar.make(parentLayout, "Something Went Wrong ", Snackbar.LENGTH_LONG).show();

        }

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
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
                                        String classCode = classResponse.get(count1 - 1).getClassCode();
                                        StrClassName = classResponse.get(count1 - 1).getClassName();
                                        InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
                                        boolean availble = internetConnectionCheck.checkConnection();
                                        if (availble) {
                                            try {
                                                if (!(AppUtility.IsTeacher.equals("T")||(AppUtility.IsTeacher.equals("A")))) {

                                                    UserNameDropDown[k] = AppUtility.StudentName[position - 1];
                                                    StudentCodeForNetworkCall = AppUtility.StudentCode[position - 1];
                                                    StrStudentName = AppUtility.StudentName[position - 1];
                                                    ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDown);
                                                    Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spinner.setAdapter(Select_Sudent_Adapter);

                                                }
                                                else {
                                                    NetWorkCallForStudentList(classCode);
                                                }
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }

//                                        RelativeForHide.setVisibility(View.GONE);
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
                    if (studentResponse.get(0).getResult().equals("Suessfull")) {
                        AppUtility.STUDENT_RESPONSE = studentResponse;
                        UserNameDropDown = new String[AppUtility.STUDENT_RESPONSE.size() + 1];
                        j = 0;
                        UserNameDropDown[j] = "Select Student Name...";
                        for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                            j = j + 1;
                            String Name = AppUtility.STUDENT_RESPONSE.get(i).getStudentName();
//                            String Name = AppUtility.STUDENT_RESPONSE.get(i).getFName() + " " + AppUtility.STUDENT_RESPONSE.get(i).getMName() + " " + AppUtility.STUDENT_RESPONSE.get(i).getLName();
                            UserNameDropDown[j] = Name;
                        }

                        ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, UserNameDropDown);
                        Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(Select_Category_Adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String spinner1 = parent.getItemAtPosition(position).toString();
                                count = position;
                                if (count != 0) {
                                    StudentCodeForNetworkCall = AppUtility.STUDENT_RESPONSE.get(count - 1).getStudentCode();
                                    StrStudentName = AppUtility.STUDENT_RESPONSE.get(count - 1).getStudentName();
//                                    RelativeForHide.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }
                        });

                    } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());
                    } else if (studentResponse.get(0).getResult().equals("Something went wrong")) {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponse.get(0).getResult().toString());
                    }
                    break;

            }
        } catch (Exception ex) {
            Snackbar.make(parentLayout, "Something went wrong Try again later", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void noResponse(String error) {
        try {
            progressDialogClickClass.dismiss();
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowAttendance", "noResponse", e);
            pc.showCatchException();
        }
    }


    public class OneDayDecorator implements DayViewDecorator {

        private CalendarDay date = null;
        private int color;

        private Drawable drawable;

        public OneDayDecorator(Date date1, int color) {
            //   date = CalendarDay.today();
            this.date = CalendarDay.from(date1);
            this.color = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(color));
//            view.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.day_color));
        }

        public void setDate(Date date) {
            this.date = CalendarDay.from(date);
        }
    }
    private void NetWorkCallForStudentList(String classCode) {
        try {
            spinner.setAdapter(null);
            StudentNameArrayString = new CreateJsonFunction().CreateJSONForStudentList(classCode, "active");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getStudentNameList(StudentNameArrayString, AppUtility.GET_STUDENT_NAME_LIST);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowAttendance", "NetWorkCallForStudentList", e);
            pc.showCatchException();
        }
    }


}

