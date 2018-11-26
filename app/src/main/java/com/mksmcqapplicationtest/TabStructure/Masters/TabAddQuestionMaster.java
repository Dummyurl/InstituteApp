package com.mksmcqapplicationtest.TabStructure.Masters;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Master;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TabAddQuestionMaster extends Fragment implements View.OnClickListener, ResponseListener, CompoundButton.OnCheckedChangeListener {

    View view;
    CustomEditText txtQueHint, txtQuestion, txtAnsA, txtAnsB, txtAnsC, txtAnsD, txtAnsE, txtAnsF,
            txtMarks, txtAnsDescription;
    CustomButton btnAddQuestion;
    RadioButton rdbA, rdbB, rdbC, rdbD, rdbE, rdbF;
    String strtxtAnsA, strtxtAnsB, strtxtAnsC, strtxtAnsD, strtxtAnsE, strtxtAnsF,
            strCorrectOption, strCorrectAns, AddquestionString,CategoryString,CategoryCode,CategoryName;
    List<Master> masterResponse;
    Spinner spinnerForCategory;
    String[] UserNameDropDownForCategory = new String[1];
    int count1;
    int j1 = 0;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_add_quetion_master, container, false);
        try {
            setUI();
            txtQueHint =  view.findViewById(R.id.txtQueHint);
            txtQuestion = view.findViewById(R.id.txtQuestion);
            txtAnsA = view.findViewById(R.id.txtAnsA);
            txtAnsB = view.findViewById(R.id.txtAnsB);
            txtAnsC = view.findViewById(R.id.txtAnsC);
            txtAnsD = view.findViewById(R.id.txtAnsD);
            txtAnsE = view.findViewById(R.id.txtAnsE);
            txtAnsF = view.findViewById(R.id.txtAnsF);
            txtMarks = view.findViewById(R.id.txtMarks);
            txtAnsDescription = view.findViewById(R.id.txtAnsDescription);
            spinnerForCategory = view.findViewById(R.id.spinnerForCategory);
            btnAddQuestion =  view.findViewById(R.id.btnAddQuestion);
            btnAddQuestion.setOnClickListener(this);

            rdbA = (RadioButton) view.findViewById(R.id.rdbA);
            rdbA.setOnCheckedChangeListener(this);
            rdbB = (RadioButton) view.findViewById(R.id.rdbB);
            rdbB.setOnCheckedChangeListener(this);
            rdbC = (RadioButton) view.findViewById(R.id.rdbC);
            rdbC.setOnCheckedChangeListener(this);
            rdbD = (RadioButton) view.findViewById(R.id.rdbD);
            rdbD.setOnCheckedChangeListener(this);
            rdbE = (RadioButton) view.findViewById(R.id.rdbE);
            rdbE.setOnCheckedChangeListener(this);
            rdbF = (RadioButton) view.findViewById(R.id.rdbF);
            rdbF.setOnCheckedChangeListener(this);

            NetworkCallForGetCategory();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onCreateView", e);
            pc.showCatchException();
        }
        return view;
    }


        private void setUI() {
            CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
            txtactionbartitle.setText(getResources().getString(R.string.Add_Question));

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
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnAddQuestion:
                    strtxtAnsA = txtAnsA.getText().toString();
                    strtxtAnsB = txtAnsB.getText().toString();
                    strtxtAnsC = txtAnsC.getText().toString();
                    strtxtAnsD = txtAnsD.getText().toString();
                    strtxtAnsE = txtAnsE.getText().toString();
                    strtxtAnsF = txtAnsF.getText().toString();
                    checkAllFilds();
                    break;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onClick", e);
            pc.showCatchException();
        }
    }

    private void checkAllFilds() {
        try {
            if (txtQueHint.getText().toString().trim().equals("")) {
                Snackbar.make(view, "Please enter Question Hint", Snackbar.LENGTH_LONG).show();
            } else if (txtQuestion.getText().toString().trim().equals("")) {
                Snackbar.make(view, "Please enter Question ", Snackbar.LENGTH_LONG).show();
            } else if (txtMarks.getText().toString().trim().equals("")) {
                Snackbar.make(view, "Please enter Marks ", Snackbar.LENGTH_LONG).show();
            } else if (count1==0) {
                Snackbar.make(view, "Please Select Question Category ", Snackbar.LENGTH_LONG).show();
            } else if (rdbA.isChecked() && strtxtAnsA.equals("") || rdbB.isChecked() && strtxtAnsB.equals("") ||
                    rdbC.isChecked() && strtxtAnsC.equals("") || rdbD.isChecked() && strtxtAnsD.equals("") ||
                    rdbE.isChecked() && strtxtAnsE.equals("") || rdbF.isChecked() && strtxtAnsF.equals("")) {
                Snackbar.make(view, "Invalid Correct Answer", Snackbar.LENGTH_LONG).show();
            } else {
                List<String> lst = Arrays.asList(strtxtAnsA, strtxtAnsB,
                        strtxtAnsC.equals("") ? "C_AnsAahe" : strtxtAnsC,
                        strtxtAnsD.equals("") ? "D_AnsAahe" : strtxtAnsD,
                        strtxtAnsE.equals("") ? "E_AnsAahe" : strtxtAnsE,
                        strtxtAnsF.equals("") ? "F_AnsAahe" : strtxtAnsF);
                Set<String> allTextBoxes = new HashSet<String>(lst);
                if (allTextBoxes.size() != lst.size()) {
                    Snackbar.make(view, "All answer should be different", Snackbar.LENGTH_LONG).show();
                } else {
                    getCorrectOption();

                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "checkAllFilds", e);
            pc.showCatchException();
        }
    }

    private void getCorrectOption() {
        try {

            if (rdbA.isChecked()) {
                strCorrectOption = "a";
                strCorrectAns = txtAnsA.getText().toString();
            } else if (rdbB.isChecked()) {
                strCorrectOption = "b";
                strCorrectAns = txtAnsB.getText().toString();
            } else if (rdbC.isChecked()) {
                strCorrectOption = "c";
                strCorrectAns = txtAnsC.getText().toString();
            } else if (rdbD.isChecked()) {
                strCorrectOption = "d";
                strCorrectAns = txtAnsD.getText().toString();
            } else if (rdbE.isChecked()) {
                strCorrectOption = "e";
                strCorrectAns = txtAnsE.getText().toString();
            } else if (rdbF.isChecked()) {
                strCorrectOption = "f";
                strCorrectAns = txtAnsF.getText().toString();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "getCorrectOption", e);
            pc.showCatchException();
        }
    }

    private void clearAll() {
        try {

            txtQueHint.setText("");
            txtQuestion.setText("");
            txtAnsA.setText("");
            txtAnsB.setText("");
            txtAnsC.setText("");
            txtAnsD.setText("");
            txtAnsE.setText("");
            txtAnsF.setText("");
            rdbA.setChecked(true);
            rdbB.setChecked(false);
            rdbC.setChecked(false);
            rdbD.setChecked(false);
            rdbE.setChecked(false);
            rdbF.setChecked(false);
            txtMarks.setText("");
            txtAnsDescription.setText("");
            spinnerForCategory.setSelection(0);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "clearAll", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallAddquestion() {
        try {
            CreateJSON();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getMasterAddQuestion(AddquestionString, AppUtility.MASTER_ADD_QUESTION);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "NetworkCallAddquestion", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            strtxtAnsA = txtAnsA.getText().toString();
            strtxtAnsB = txtAnsB.getText().toString();
            strtxtAnsC = txtAnsC.getText().toString();
            strtxtAnsD = txtAnsD.getText().toString();
            strtxtAnsE = txtAnsE.getText().toString();
            strtxtAnsF = txtAnsF.getText().toString();

            Master master = new Master();
            Gson gson = new Gson();
            master.setQuestionHint(txtQueHint.getText().toString());
            master.setQuestion(txtQuestion.getText().toString());
            master.setAnsA(strtxtAnsA);
            master.setAnsB(strtxtAnsB);
            master.setAnsC(strtxtAnsC);
            master.setAnsD(strtxtAnsD);
            master.setAnsE(strtxtAnsE);
            master.setAnsF(strtxtAnsF);
            master.setCorrectOption(strCorrectOption);
            master.setCorrectAns(strCorrectAns);
            master.setMarks(txtMarks.getText().toString());
            master.setAnswerDescription(txtAnsDescription.getText().toString());
            master.setCategoryCode(CategoryCode);
            master.setUserName(AppUtility.KEY_USERNAME);
            String SubmitAdJson = gson.toJson(master);
            AddquestionString = "[" + SubmitAdJson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "CreateJSON", e);
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
                case 890:
                    masterResponse = (List<Master>) data;
                    try {
                        if (masterResponse.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, masterResponse.get(0).getResult().toString(), Snackbar.LENGTH_LONG).show();
                            clearAll();
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponse.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;

                case 900:
                    masterResponse = (List<Master>) data;
                    try {
                        if (masterResponse.get(0).getResultCode().equals("1")) {
                            UserNameDropDownForCategory = new String[masterResponse.size() + 1];
                            j1 = 0;
                            UserNameDropDownForCategory[j1] = "Select Category Name...";
                            for (int i = 0; i < masterResponse.size(); i++) {
                                j1 = j1 + 1;
                                UserNameDropDownForCategory[j1] = masterResponse.get(i).getCategoryName();
                            }
                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, UserNameDropDownForCategory);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForCategory.setAdapter(Select_Category_Adapter);
                            spinnerForCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        CategoryCode = masterResponse.get(count1 - 1).getCategoryCode();
                                        CategoryName = masterResponse.get(count1 - 1).getCategoryName();
                                    } else {

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponse.get(0).getResult().toString());
                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onResponseWithRequestCode", ex);
                        pc.showCatchException();
                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }


    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(view, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "noResponse", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            boolean checked = ((RadioButton) buttonView).isChecked();
            switch (buttonView.getId()) {
                case R.id.rdbA:
                    if (checked) {
                        UncheckRemaining(rdbB, rdbC, rdbD, rdbE, rdbF);
                    }
                    break;

                case R.id.rdbB:
                    if (checked) {
                        UncheckRemaining(rdbA, rdbC, rdbD, rdbE, rdbF);
                    }
                    break;

                case R.id.rdbC:
                    if (checked) {
                        UncheckRemaining(rdbA, rdbB, rdbD, rdbE, rdbF);
                    }
                    break;

                case R.id.rdbD:
                    if (checked) {
                        UncheckRemaining(rdbA, rdbB, rdbC, rdbE, rdbF);
                    }
                    break;

                case R.id.rdbE:
                    if (checked) {
                        UncheckRemaining(rdbA, rdbB, rdbC, rdbD, rdbF);
                    }
                    break;

                case R.id.rdbF:
                    if (checked) {
                        UncheckRemaining(rdbA, rdbB, rdbC, rdbD, rdbE);
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "onCheckedChanged", e);
            pc.showCatchException();
        }
    }

    private void UncheckRemaining(RadioButton rdb1, RadioButton rdb2, RadioButton rdb3, RadioButton rdb4, RadioButton rdb5) {
        try {
            rdb1.setChecked(false);
            rdb2.setChecked(false);
            rdb3.setChecked(false);
            rdb4.setChecked(false);
            rdb5.setChecked(false);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabAddQuestionMaster", "UncheckRemaining", e);
            pc.showCatchException();
        }

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

}

