package com.mksmcqapplicationtest.TabStructure.Masters;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import com.mksmcqapplicationtest.CreateJsonFunction;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Master;
import com.mksmcqapplicationtest.beans.Test;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.MasterGetquestionAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TabPrepareTestMaster extends Fragment implements ResponseListener, View.OnClickListener {

    View view;
    CheckBox chkSelectAll;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerViewOfQuestion;
    Button btnSave;
    Spinner spinnerForTest, spinnerForClass, spinnerForCategory;
    String GetListOfQuestionString, ClassNameJSONString, TestCode, CategoryString, CategoryCode, Question = "", PrepareTestString;
    List<Master> masterResponse;
    List<Master> masterResponseTest;
    List<Master> masterResponsecategory;
    List<Master> masterResponsePrepareTest;
    MasterGetquestionAdapter adapter;

    List<Class> classResponse;
    String[] UserNameDropDownForClass = new String[1];
    int count1;
    int j1 = 0;

    String[] UserNameDropDownForTestName = new String[1];
    int countTest;
    int j2 = 0;

    String[] UserNameDropDownForCategory = new String[1];
    int countCategory;
    int j3 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_prepare_test_master, container, false);
setUI();
        chkSelectAll = (CheckBox) view.findViewById(R.id.chkSelectAll);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        recyclerViewOfQuestion = (RecyclerView) view.findViewById(R.id.recyclerViewOfQuestion);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOfQuestion.setLayoutManager(layoutManager);
        recyclerViewOfQuestion.setItemAnimator(new DefaultItemAnimator());

        spinnerForTest = (Spinner) view.findViewById(R.id.spinnerForTest);
        spinnerForClass = (Spinner) view.findViewById(R.id.spinnerForClass);
        spinnerForCategory = (Spinner) view.findViewById(R.id.spinnerForCategory);

        NetworkCallForTestName();
        NetWorkCallForClass();
        NetworkCallForGetCategory();

        chkSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (masterResponse.size() > 0) {
                        adapter.selectAll();
                    } else {
                        Snackbar.make(view, "No MCQ Test Available For Enable", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    if (masterResponse.size() > 0) {
                        adapter.deselectAll();
                        adapter.notifyDataSetChanged();
                    } else {
                        Snackbar.make(view, "No MCQ Test For Disable", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.preparetest));

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

    private void NetworkCallForGetCategory() {
        try {
            CreateJSONForCategory();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getMasterCategory(CategoryString, AppUtility.MASTER_GET_CATEGORY);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "NetworkCallForAddTest", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForCategory() {
        try {
            Master master = new Master();
            Gson gson = new Gson();
            master.setUserName(AppUtility.KEY_USERNAME);
            String SubmitAdJson = gson.toJson(master);
            CategoryString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "CreateJSON", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(), view, "ExamListFragemtActivity", "NetWorkCallForClass", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallMasterAllQuestion() {
        try {
            recyclerViewOfQuestion.setAdapter(null);
            CreateJSONAllQuestion();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getMasterQuestion(GetListOfQuestionString, AppUtility.MASTER_GET_QUESTION);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "NetworkCallForAddTest", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONAllQuestion() {
        try {
            Master master = new Master();
            Gson gson = new Gson();
            master.setUserName(AppUtility.KEY_USERNAME);
            master.setCategoryCode(CategoryCode);
            String SubmitAdJson = gson.toJson(master);
            GetListOfQuestionString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "CreateJSON", e);
            pc.showCatchException();
        }
    }


    private void NetworkCallForTestName() {
        try {
            CreateJSONTestName();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getMasterGetTestName(GetListOfQuestionString, AppUtility.MASTER_GET_TESTNAME);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "NetworkCallForAddTest", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONTestName() {
        try {
            Master master = new Master();
            Gson gson = new Gson();
            master.setUserName(AppUtility.KEY_USERNAME);
            String SubmitAdJson = gson.toJson(master);
            GetListOfQuestionString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "CreateJSON", e);
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
                                        AppUtility.KEY_CLASSCODE = classResponse.get(count1 - 1).getClassCode();

                                    } else {
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
                        Snackbar.make(view, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NetWorkCallForClass();
                                    }
                                }).show();
                    }
                    break;


                case 910:
                    masterResponse = (List<Master>) data;
                    try {
                        if (masterResponse.get(0).getResultCode().equals("1")) {
                            adapter = new MasterGetquestionAdapter(getContext(), recyclerViewOfQuestion, masterResponse);
                            recyclerViewOfQuestion.setAdapter(adapter);
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponse.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;
                case 920:
                    masterResponseTest = (List<Master>) data;
                    try {
                        if (masterResponseTest.get(0).getResultCode().equals("1")) {
                            UserNameDropDownForTestName = new String[masterResponseTest.size() + 1];
                            j2 = 0;
                            UserNameDropDownForTestName[j2] = "Select Test Name...";
                            for (int i = 0; i < masterResponseTest.size(); i++) {
                                j2 = j2 + 1;
                                UserNameDropDownForTestName[j2] = masterResponseTest.get(i).getTest();
                            }
                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForTestName);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForTest.setAdapter(Select_Category_Adapter);
                            spinnerForTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    countTest = position; //this would give you the id of the selected item
                                    if (countTest != 0) {
                                        TestCode = masterResponseTest.get(countTest - 1).getTestCode();
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponseTest.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;

                case 900:
                    masterResponsecategory = (List<Master>) data;
                    try {
                        if (masterResponsecategory.get(0).getResultCode().equals("1")) {
                            UserNameDropDownForCategory = new String[masterResponsecategory.size() + 1];
                            j3 = 0;
                            UserNameDropDownForCategory[j3] = "Select Category Name...";
                            for (int i = 0; i < masterResponsecategory.size(); i++) {
                                j3 = j3 + 1;
                                UserNameDropDownForCategory[j3] = masterResponsecategory.get(i).getCategoryName();
                            }
                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForCategory);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForCategory.setAdapter(Select_Category_Adapter);
                            spinnerForCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    countCategory = position; //this would give you the id of the selected item
                                    if (countCategory != 0) {
                                        CategoryCode = masterResponsecategory.get(countCategory - 1).getCategoryCode();
                                        NetworkCallMasterAllQuestion();
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponsecategory.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;

                case 930:
                    masterResponsePrepareTest = (List<Master>) data;
                    try {
                        if (masterResponsePrepareTest.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, masterResponsePrepareTest.get(0).getResult().toString(), Snackbar.LENGTH_LONG).show();
                            recyclerViewOfQuestion.setAdapter(null);
                            spinnerForTest.setSelection(0);
                            spinnerForCategory.setSelection(0);
                            spinnerForClass.setSelection(0);
                            chkSelectAll.setChecked(false);
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponsePrepareTest.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {

    }


    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnSave:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnSave);
                    bounce_button_class1.BounceMethod();
                    if (countTest == 0) {
                        Snackbar.make(view, "Please Select Test Name", Snackbar.LENGTH_LONG).show();
                    } else if (count1 == 0) {
                        Snackbar.make(view, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else if (countCategory == 0) {
                        Snackbar.make(view, "Please Select Category Name", Snackbar.LENGTH_LONG).show();
                    } else {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Confirmation!!");
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure? You want to Prepare this MCQ test");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class1.BounceMethod();
                                CreateJSONForPrepareTest();
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
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onClick", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForPrepareTest() {
        try {
            if (masterResponse.size() > 0) {
                Question = "";
                for (int i = 0; i < masterResponse.size(); i++) {
                    String abc = masterResponse.get(i).getAcFlag();
                    if (abc != null) {
                        try {

                            if (abc.equals("Y")) {
                                String que = masterResponse.get(i).getQuestionCode();
                                Question = Question + que + ",";
                            }
                        } catch (Exception e) {
                            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "CreateJSONForEnableDisableTest", e);
                            pc.showCatchException();
                        }
                    }

                }

                if (Question.equals("") || Question == null) {
                    Snackbar.make(view, "Please select questions", Snackbar.LENGTH_LONG).show();
                } else {
                    Question = Question.substring(0, Question.length() - 1);
                    CreateJsonForPrepareTest();

                }

            } else {
                Snackbar.make(view, "Something went wrong", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "EnableTest", "CreateJSONForEnableDisableTest", e);
            pc.showCatchException();
        }
    }

    private void CreateJsonForPrepareTest() {
        try {
            Master master = new Master();
            Gson gson = new Gson();
            master.setTestCode(TestCode);
            master.setClassCode(AppUtility.KEY_CLASSCODE);
            master.setQuestion(Question);
            master.setMarks("20");
            master.setUserName(AppUtility.KEY_USERNAME);
            String SubmitAdJson = gson.toJson(master);
            PrepareTestString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "CreateJSON", e);
            pc.showCatchException();
        }
    }
}

