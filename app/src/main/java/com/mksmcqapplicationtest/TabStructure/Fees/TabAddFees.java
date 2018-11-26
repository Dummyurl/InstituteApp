package com.mksmcqapplicationtest.TabStructure.Fees;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.Calendar;
import java.util.List;

public class TabAddFees extends Fragment implements View.OnClickListener, ResponseListener {
    List<Class> classResponse;
    List<Student> studentResponse;
    List<Response> responses;
    View parentLayout;
    Spinner spinnerForClass;
    Spinner spinnerForStudent;
    Spinner spinnerForEducationalYear;
    String[] UserNameDropDownClass = new String[1];
    String[] UserNameDropDownStudent = new String[1];
    String[] UserNameDropDownEducationalYear = new String[1];
    String AddFeesAmount, StudentName;
    int j = 0, count,  yearCount;
    TextView txtAddFees;
    EditText edtAddFees;
    CustomButton btnAddFees;
    String ClassCodeForNetworkCall, StudentCodeForNetworkCall = "0", EducationalYearForNetworkCall = "0",
            ClassNameJSONString, StudentNameArrayString, FeesArrayString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.layout_add_fees, container, false);
        try {

            setUI();
            if (AppUtility.IsDemoApplication) {
                btnAddFees.setClickable(false);
                btnAddFees.setEnabled(false);
            } else {
                btnAddFees.setClickable(true);
                btnAddFees.setEnabled(true);
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            UserNameDropDownEducationalYear = new String[5];
            int j = 0;
            UserNameDropDownEducationalYear[j] = "Select Educational Year";

            for (int i = year - 2; i < year; i++) {
                j++;
                UserNameDropDownEducationalYear[j] = String.valueOf(i + "_" + (i + 1));
            }
            j = 3;
            UserNameDropDownEducationalYear[j] = String.valueOf(year + "_" + (year + 1));
            for (int i = year + 1; i < year + 2; i++) {
                j++;
                UserNameDropDownEducationalYear[j] = String.valueOf(i + "_" + (i + 1));
            }
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCall();
            }

            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                advertiesClass.ShowAdver();

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.add_fees_fragment_title));

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

        spinnerForClass = parentLayout.findViewById(R.id.spinnerForClass);
        spinnerForClass.setAdapter(null);
        spinnerForStudent = parentLayout.findViewById(R.id.spinnerForStudentName);
        spinnerForStudent.setAdapter(null);
        spinnerForEducationalYear = parentLayout.findViewById(R.id.spinnerForEducationalYear);
        spinnerForEducationalYear.setAdapter(null);
        txtAddFees = parentLayout.findViewById(R.id.txtAddFees);
        edtAddFees = parentLayout.findViewById(R.id.edtAddFees);
        btnAddFees = parentLayout.findViewById(R.id.btnAddFees);
        btnAddFees.setOnClickListener(this);
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
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "NetworkCallForClass", e);
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
                    classResponse = (List<Class>) data;
                    ShowErrorPopUp showErrorPopUp;
                    String classresultstring = classResponse.get(0).getResult();
                    switch (classresultstring) {
                        case "Suessfull":
                            AppUtility.CLASS_RESPONSE = classResponse;

                            UserNameDropDownClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j = 0;
                            UserNameDropDownClass[j] = "Select Class Name...";
                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
                                j = j + 1;
                                UserNameDropDownClass[j] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownClass);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForClass.setAdapter(Select_Category_Adapter);

                            spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    count = position; //this would give you the id of the selected item
                                    if (count != 0) {
                                        ClassCodeForNetworkCall = classResponse.get(count - 1).getClassCode();
                                        NetWorkCallForStudentList(ClassCodeForNetworkCall);
                                        edtAddFees.setText("");

                                    } else {

                                        ClassCodeForNetworkCall = "0";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            break;
                        case "No Data Found":
                            showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classresultstring);
                            break;
                        case "Something went wrong":
                            showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", classresultstring);
                            break;
                    }
                    break;
                case 180:
                    studentResponse = (List<Student>) data;
                    String studentresultstring = studentResponse.get(0).getResult();
                    switch (studentresultstring) {
                        case "Suessfull":
                            AppUtility.STUDENT_RESPONSE = studentResponse;

                            UserNameDropDownStudent = new String[AppUtility.STUDENT_RESPONSE.size() + 1];
                            j = 0;
                            UserNameDropDownStudent[j] = "Select Student Name...";
                            for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                                j = j + 1;
                                String Name = AppUtility.STUDENT_RESPONSE.get(i).getStudentName();
                                UserNameDropDownStudent[j] = Name;

                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownStudent);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForStudent.setAdapter(Select_Category_Adapter);


                            spinnerForStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    count = position;
                                    if (count != 0) {
                                        StudentCodeForNetworkCall = studentResponse.get(count - 1).getStudentCode();
                                        StudentName = studentResponse.get(count - 1).getStudentName();
                                        edtAddFees.setText("");
                                    } else {
                                        StudentCodeForNetworkCall = "0";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });
                            ArrayAdapter<String> Select_Educational_Year_Adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownEducationalYear);
                            Select_Educational_Year_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForEducationalYear.setAdapter(Select_Educational_Year_Adapter);
                            spinnerForEducationalYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    yearCount = position;
                                    if (yearCount != 0) {
                                        EducationalYearForNetworkCall = UserNameDropDownEducationalYear[yearCount];
                                        edtAddFees.setText("");
                                    } else {
                                        EducationalYearForNetworkCall = "0";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });
                            break;
                        case "No Data Found":

                            showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", studentresultstring);
                            break;
                        case "Something went wrong":
                            showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", studentresultstring);
                            break;
                    }
                    break;


                case 270:
                    try {
                        responses = (List<Response>) data;
                        String responseresultString = responses.get(0).getResult();
                        switch (responseresultString) {
                            case "Fees Added Suessfully":
                                Snackbar.make(parentLayout, "Fees Added Suessfully", Snackbar.LENGTH_LONG).show();
                                edtAddFees.setText("");
                                spinnerForClass.setSelection(0);
                                spinnerForStudent.setAdapter(null);
                                spinnerForEducationalYear.setAdapter(null);
                                break;
                            case "Something went wrong":
                                showErrorPopUp = new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(), "Error", responseresultString);
                                break;
                            case "Unable to add fees":
                                showErrorPopUp = new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(getContext(), "Error", responseresultString);
                                break;
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

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCallForStudentList(String classCode) {
        try {
            spinnerForStudent.setAdapter(null);
            StudentNameArrayString = new CreateJsonFunction().CreateJSONForStudentList(classCode, "active");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getStudentNameList(StudentNameArrayString, AppUtility.GET_STUDENT_NAME_LIST);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "NetWorkCallForStudentList", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnAddFees:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnAddFees);
                    bounce_button_class1.BounceMethod();

                    AddFeesAmount = edtAddFees.getText().toString();
                    if (ClassCodeForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please select class Name", Snackbar.LENGTH_LONG).show();
                    } else if (StudentCodeForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please select Student Name", Snackbar.LENGTH_LONG).show();
                    } else if (EducationalYearForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please select Educational Year", Snackbar.LENGTH_LONG).show();
                    } else if (AddFeesAmount.equals("") || AddFeesAmount == null) {
                        Snackbar.make(parentLayout, "Please Enter Amount", Snackbar.LENGTH_LONG).show();
                    } else {
                        btnAddFees.setEnabled(false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(false);
                        builder.setIcon(R.mipmap.ic_launcher_logo_c);
                        builder.setTitle(getResources().getString(R.string.Titile_Confirmation_pop_up));
                        builder.setMessage("Are you sure? You want to add  RS. " + AddFeesAmount + " for " + StudentName);
                        builder.setPositiveButton(getResources().getString(R.string.okbtntext_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnAddFees.setEnabled(true);
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton(getResources().getString(R.string.canclebtntext_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btnAddFees.setEnabled(true);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                    break;

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "OnClick", e);
            pc.showCatchException();
        }
    }

    private void AddFeesList() {
        try {
            CreateJSONForFees();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getAddFees(FeesArrayString, AppUtility.ADD_FEES_URL);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "AddFeesList", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForFees() {
        try {
            Gson gson = new Gson();
            Student studentResponse = new Student();
            studentResponse.setStudentCode(StudentCodeForNetworkCall);
            studentResponse.setClassCode(ClassCodeForNetworkCall);
            studentResponse.setAmount(AddFeesAmount);
            studentResponse.setEducationalYear(EducationalYearForNetworkCall);
            studentResponse.setUserName(AppUtility.KEY_USERNAME);
            String StudentResponseString = gson.toJson(studentResponse);
            FeesArrayString = "[" + StudentResponseString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "AddFees", "CreateJSONForFees", e);
            pc.showCatchException();
        }
    }


}

