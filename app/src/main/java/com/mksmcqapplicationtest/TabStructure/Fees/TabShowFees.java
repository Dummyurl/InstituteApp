package com.mksmcqapplicationtest.TabStructure.Fees;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Fees;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ShowFeesAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.Calendar;
import java.util.List;

public class TabShowFees extends Fragment implements ResponseListener {


    RecyclerView recyclerView;
    ShowFeesAdapter showFeesAdapter;
    View parentLayout;
    private List<Fees> fees;
    Spinner spinnerForClass, spinnerForStudent;
    List<Class> classResponse;
    String[] UserNameDropDownClass = new String[1];
    int j = 0, k = 0, count, yearcount;
    String ClassNameJSONString;
    String ClassCodeForNetworkCall, FeesArrayString;
    CustomTextView txtTotalFeesOfClass;
    Context context;
    Toast toast;
    Spinner spinnerForEducationalYear;
    String[] UserNameDropDownEducationalYear = new String[1];
    String EducationalYearForNetworkCall;
    String[] UserNameDropDown = new String[1];
    String StudentCodeForNetworkCall, StrStudentName;
    CustomTextViewBold txtactionbartitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_show_fees, container, false);
        try {
            this.context = getContext().getApplicationContext();
            setUI();

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.GONE);

            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                advertiesClass.ShowAdver();

            }
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            UserNameDropDownEducationalYear = new String[5];
            int j1 = 0;
            UserNameDropDownEducationalYear[j] = "Select Educational Year";

            for (int i = year - 2; i < year; i++) {
                j1++;
                UserNameDropDownEducationalYear[j1] = String.valueOf(i + "_" + (i + 1));
            }
            j1 = 3;
            UserNameDropDownEducationalYear[j1] = String.valueOf(year + "_" + (year + 1));
            for (int i = year + 1; i < year + 2; i++) {
                j1++;
                UserNameDropDownEducationalYear[j1] = String.valueOf(i + "_" + (i + 1));
            }
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        recyclerView = parentLayout.findViewById(R.id.recyclerView);
        spinnerForClass = parentLayout.findViewById(R.id.spinnerForClass);
        txtTotalFeesOfClass = parentLayout.findViewById(R.id.txtTotalFeesOfClass);
        txtTotalFeesOfClass.setVisibility(View.GONE);
        spinnerForEducationalYear = parentLayout.findViewById(R.id.spinnerForEducationalYear);
        spinnerForStudent = parentLayout.findViewById(R.id.spinnerForStudentName);
//        relativeLayout = parentLayout.findViewById(R.id.linear2);
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

    private void NetworkCall() {
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
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "NetworkCall", e);
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
                case 170:
                    recyclerView.setAdapter(null);
                    spinnerForEducationalYear.setAdapter(null);
                    recyclerView.setAdapter(null);
                    final ArrayAdapter<String> Select_Educational_Year_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownEducationalYear);
                    Select_Educational_Year_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    classResponse = (List<Class>) data;
                    if (classResponse.get(0).getResult().equals("Suessfull")) {
                        AppUtility.CLASS_RESPONSE = classResponse;

                        UserNameDropDownClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                        j = 0;
                        UserNameDropDownClass[j] = "Select Class Name...";
                        for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                            j = j + 1;
                            UserNameDropDownClass[j] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                        }

                        ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownClass);
                        Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerForClass.setAdapter(Select_Category_Adapter);

                        spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count = position; //this would give you the id of the selected item
                                    if (count != 0) {
                                        spinnerForEducationalYear.setAdapter(Select_Educational_Year_Adapter);
                                        ClassCodeForNetworkCall = classResponse.get(count - 1).getClassCode();
                                        if (!(AppUtility.IsTeacher.equals("T") || AppUtility.IsTeacher.equals("A"))) {
                                            UserNameDropDown[k] = AppUtility.StudentName[position - 1];
                                            StudentCodeForNetworkCall = AppUtility.StudentCode[position - 1];
                                            StrStudentName = AppUtility.StudentName[position - 1];
                                            ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDown);
                                            Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinnerForStudent.setAdapter(Select_Sudent_Adapter);

                                        }
                                    } else {
                                        spinnerForEducationalYear.setAdapter(Select_Educational_Year_Adapter);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                    if (toast != null) {
                                        toast.cancel();
                                    } else {
                                        Log.d("Toast", String.valueOf(toast));
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }


                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                spinnerForEducationalYear.setVisibility(View.GONE);
                            }
                        });
                        try {
                            // spinnerForEducationalYear.setAdapter(Select_Educational_Year_Adapter);
                            spinnerForEducationalYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    yearcount = position;
                                    if (yearcount != 0) {
                                        EducationalYearForNetworkCall = UserNameDropDownEducationalYear[yearcount];
                                        if (count != 0) {
                                            AppUtility.ClassTotalFees = Integer.parseInt(classResponse.get(count - 1).getTotalFees());
                                            txtTotalFeesOfClass.setVisibility(View.VISIBLE);
                                            txtTotalFeesOfClass.setText("Total Fees : " + classResponse.get(count - 1).getTotalFees());
                                            recyclerView.setAdapter(null);
                                            if (AppUtility.IsTeacher.equals("T")) {
                                                displayFeesListForTeacher();
                                            } else if (AppUtility.IsTeacher.equals("A")) {
                                                displayFeesListForTeacher();
                                            } else {
                                                displayFeesListForStudent();
                                            }
                                        } else {
                                            Snackbar.make(parentLayout, "Select Class Name", Snackbar.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        EducationalYearForNetworkCall = "0";
                                        recyclerView.setAdapter(null);
                                        txtTotalFeesOfClass.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });
                        } catch (Exception ex) {

                        }

                    } else if (classResponse.get(0).getResult().equals("No Data Found")) {
                        ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                    } else if (classResponse.get(0).getResult().equals("Something went wrong")) {
                        ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(), "Error", classResponse.get(0).getResult().toString());
                    }
                    break;
                case 220:
//                    txtTotalFeesOfClass.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    try {

                        fees = (List<Fees>) data;
                        if (fees.get(0).getResult().equals("Suessfull")) {
                            if (fees != null) {
                                showFeesAdapter = new ShowFeesAdapter(getContext(), fees, ClassCodeForNetworkCall, EducationalYearForNetworkCall);
                                recyclerView.setAdapter(showFeesAdapter);
                                toast = Toast.makeText(getContext(), "Total: " + String.valueOf(fees.size()), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } else if (fees.get(0).getResult().equals("No Data Found")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", fees.get(0).getResult().toString());
                        } else if (fees.get(0).getResult().equals("Something Went Wrong Please Try Again")) {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", fees.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "" + ex, Snackbar.LENGTH_LONG).show();
                    }


                    break;


            }
        } catch (Exception ex) {
            Snackbar.make(parentLayout, "Something went wrong Try again later", Snackbar.LENGTH_LONG).show();

        }
    }

    private void displayFeesListForTeacher() {
        try {
            CreateJSONForFeesTeacher();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getListOfFees(FeesArrayString, AppUtility.FEES_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "displayFeesListForTeacher", e);
            pc.showCatchException();
        }

    }

    private void CreateJSONForFeesTeacher() {
        try {
            Gson gson = new Gson();
            Student studentResponse = new Student();
            studentResponse.setEducationalYear(EducationalYearForNetworkCall);
            studentResponse.setUserName(AppUtility.KEY_USERNAME);
            studentResponse.setClassCode(ClassCodeForNetworkCall);
            String StudentResponseString = gson.toJson(studentResponse);
            FeesArrayString = "[" + StudentResponseString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "CreateJSONForFeesTeacher", e);
            pc.showCatchException();
        }
    }

    private void displayFeesListForStudent() {
        try {
            CreateJSONForFeesStudent();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getListOfFees(FeesArrayString, AppUtility.FEES_FOR_STUDENT_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "displayFeesListForStudent", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForFeesStudent() {
        try {
            Gson gson = new Gson();
            Student studentResponse = new Student();
            studentResponse.setStudentCode(StudentCodeForNetworkCall);
            studentResponse.setClassCode(ClassCodeForNetworkCall);
            studentResponse.setEducationalYear(EducationalYearForNetworkCall);
            studentResponse.setUserName(AppUtility.KEY_USERNAME);
            String StudentResponseString = gson.toJson(studentResponse);
            FeesArrayString = "[" + StudentResponseString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "CreateJSONForFeesStudent", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "noResponse", e);
            pc.showCatchException();
        }
    }


    public void CheckForUserType() {
        try {
            NetworkCall();

            if (AppUtility.IsTeacher.equals("T")) {
                spinnerForStudent.setVisibility(View.GONE);
            } else if (AppUtility.IsTeacher.equals("A")) {
                spinnerForStudent.setVisibility(View.GONE);
            } else {
                spinnerForStudent.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "FeesActivity", "CheckForUserType", e);
            pc.showCatchException();
        }
    }

}

