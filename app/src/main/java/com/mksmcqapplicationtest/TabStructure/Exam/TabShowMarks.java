package com.mksmcqapplicationtest.TabStructure.Exam;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.SendMarksAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import java.util.List;

public class TabShowMarks extends Fragment implements ResponseListener {

    Spinner spinnerForClass, spinnerForTestList, spinner;
    String ClassNameJSONString, ClassCodeForNetworkCall, TestListJSONString, TestCodeForNetworkCall, TestNameForNetworkCall, StudentNameArrayString;
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
    TextView txtTheoryMarks;
    String[] UserNameDropDown = new String[1];
    int j = 0;
    String StudentCodeForNetworkCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_show_marks, container, false);
        try {
            setUI();
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                CheckForUserType();
            }
//            NetWorkCallForClass();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                 NetWorkCallForStudentListForSendMark();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "ShowStudentTheoryTestMarks", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Show_Marks));

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
        spinnerForTestList = parentLayout.findViewById(R.id.spinnerForTestList);
        spinner = parentLayout.findViewById(R.id.spinnerForStudentName);
        txtTheoryMarks = parentLayout.findViewById(R.id.txtTheoryMarks);
    }

    public void CheckForUserType() {
        try {
            NetWorkCallForClass();
            if (AppUtility.IsTeacher.equals("G")) {

                //For Advertise
                if (AppUtility.IsAdvertiesVisibleForGuest) {
                    ShowAdvertisement();
                } else if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            } else {
                if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "TabShowMarks", "CheckForUserType", e);
            pc.showCatchException();
        }
    }

    private void ShowAdvertisement() {
        try {
//            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//            interstitialAdvertiesClass.showInterstitialAdver();

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), parentLayout, "TabShowMarks", "ShowAdvertisement", e);
            pc.showCatchException();
        }
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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "NetWorkCallForClass", e);
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
                    if (classResponse != null) {
                        if (classResponse.get(0).getResult().equals("Suessfull")) {
                            AppUtility.CLASS_RESPONSE = classResponse;
                            UserNameDropDownForClass = new String[AppUtility.CLASS_RESPONSE.size() + 1];
                            j1 = 0;
                            UserNameDropDownForClass[j1] = "Select Class Name...";
//                            for (int i = 0; i < AppUtility.CLASS_RESPONSE.size(); i++) {
//                                j1 = j1 + 1;
//                                UserNameDropDownForClass[j1] = AppUtility.CLASS_RESPONSE.get(i).getClassName();
//                            }
//                            j = 0;
//                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForClass);
//                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            spinnerForClass.setAdapter(Select_Category_Adapter);
//                            spinnerForClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    count1 = position; //this would give you the id of the selected item
//                                    if (count1 != 0) {
//                                        spinnerForTestList.setAdapter(null);
//                                        spinner.setAdapter(null);
//                                        InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
//                                        boolean availble = internetConnectionCheck.checkConnection();
//                                        if (availble) {
//                                            UserNameDropDown[j] = AppUtility.StudentName[position - 1];
//                                            StudentCodeForNetworkCall = AppUtility.StudentCode[position - 1];
//                                            ArrayAdapter<String> Select_Sudent_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDown);
//                                            Select_Sudent_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                            spinner.setAdapter(Select_Sudent_Adapter);
//
//                                            txtTheoryMarks.setText("");
//                                            ClassCodeForNetworkCall = classResponse.get(count1 - 1).getClassCode();
//                                            NetworkCallForTestList();
//                                        } else {
//
//                                        }
//                                    } else {
//                                        txtTheoryMarks.setText("");
//                                        spinnerForTestList.setAdapter(null);
//                                        spinner.setAdapter(null);
//                                        ClassCodeForNetworkCall = "0";
//                                    }
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> arg0) {
//                                }
//                            });
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
                            UserNameDropDownForTest[j1] = "Select Test Name...";
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
                                        txtTheoryMarks.setText("");
                                        TestCodeForNetworkCall = tests.get(count2 - 1).getDataCode();
                                        TestNameForNetworkCall = tests.get(count2 - 1).getDataName();
                                        NetWorkCallForStudentListForSendMark();
                                    } else {
                                        txtTheoryMarks.setText("");
                                        TestCodeForNetworkCall = "0";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error","No test are available..");
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
                        String MarksGot = studentResponseOnDataCode.get(0).getMarks();
                        if ((MarksGot == null) || MarksGot.equals("")) {

                        } else {
                            String marks = "";
                            if (MarksGot.equals("0000")) {

                                marks = "You are absent for " + TestNameForNetworkCall + " test.";
                            } else {
                                marks = "You got " + studentResponseOnDataCode.get(0).getMarks().toString() + " marks in " + TestNameForNetworkCall +
                                        " test.";
                            }
                            txtTheoryMarks.setText(marks);
                        }
                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",studentResponseOnDataCode.get(0).getResult().toString());
                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "noResponse", e);
            pc.showCatchException();
        }

    }

    private void NetworkCallForTestList() {
        try {
            spinnerForTestList.setAdapter(null);
//            spinner.setAdapter(null);
            TestListJSONString = new CreateJsonFunction().CreateJSONForGetData(ClassCodeForNetworkCall, "all", "0");
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getClassData(TestListJSONString, AppUtility.GET_DATA_OF_CLASS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "NetworkCallForTestList", e);
            pc.showCatchException();
        }
    }

    private void NetWorkCallForStudentListForSendMark() {
        try {
            CreateJSONForStudentList(ClassCodeForNetworkCall, TestCodeForNetworkCall);
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getStudentNameListonDataCode(StudentNameArrayString, AppUtility.GET_STUDENT_LIST_WITH_MARKS);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "NetWorkCallForStudentListForSendMark", e);
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
            classResponse.setStudentCode(StudentCodeForNetworkCall);
            classResponse.setWhichList("studentwise");
            String ClassResponseString = gson.toJson(classResponse);
            StudentNameArrayString = "[" + ClassResponseString + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ShowStudentTheoryTestMarks", "CreateJSONForStudentList", e);
            pc.showCatchException();
        }
    }

}

