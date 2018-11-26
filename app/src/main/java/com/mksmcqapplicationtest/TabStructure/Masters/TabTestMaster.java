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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Master;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.TestListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabTestMaster extends Fragment implements View.OnClickListener, ResponseListener {

    Spinner spinnerForNegativeMark;
    CustomEditText txtTestName, txtTestTime;
    View view;
    CustomButton btnSave;

    String[] NegativeMarkingDropDown = new String[7];
    String SpinnerPosition = "0";
    int Count;
    String TestName, TestTime,NegativeMarking, AddTestString;
    List<Master> masterResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            setUI();
            view = inflater.inflate(R.layout.tab_test_master, container, false);
            txtTestName =  view.findViewById(R.id.txtTestName);
            txtTestTime = view.findViewById(R.id.txtTestTime);
            spinnerForNegativeMark = (Spinner) view.findViewById(R.id.spinnerForNegativeMark);
            btnSave =  view.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(this);

            spinnerForNegativeMark.setAdapter(null);
            NegativeMarkingDropDown[0] = "Select";
            NegativeMarkingDropDown[1] = "No Negative Marking";
            NegativeMarkingDropDown[2] = "100% (1:1)";
            NegativeMarkingDropDown[3] = "50% (2:1)";
            NegativeMarkingDropDown[4] = "33.33% (3:1)";
            NegativeMarkingDropDown[5] = "25% (4:1)";
            NegativeMarkingDropDown[6] = "20% (5:1)";

            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, NegativeMarkingDropDown);
            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerForNegativeMark.setAdapter(Select_Category_Adapter);
            spinnerForNegativeMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String spinner1 = parent.getItemAtPosition(position).toString();
                    Count = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onCreateView", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Add_Test));

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
                case R.id.btnSave:
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    TestName = txtTestName.getText().toString().trim();
                    TestTime = txtTestTime.getText().toString().trim();
                    if (TestName.equals("")) {
                        Snackbar.make(view, "Please enter Test Name", Snackbar.LENGTH_LONG).show();
                    } else if (TestTime.equals("")) {
                        Snackbar.make(view, "Please enter Test Time", Snackbar.LENGTH_LONG).show();
                    } else if (Count==0) {
                        Snackbar.make(view, "Please Select Negative Marking For Test", Snackbar.LENGTH_LONG).show();
                    } else {
                        calculteNegativeMarking();
                        NetworkCallForAddTest();

                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "onClick", e);
            pc.showCatchException();
        }
    }

    private void calculteNegativeMarking() {
        try {
            if (Count == 1) {
                NegativeMarking = "0";
            } else if (Count == 2) {
                NegativeMarking = "1";
            } else if (Count == 3) {
                NegativeMarking = "0.50";
            } else if (Count == 4) {
                NegativeMarking = "0.33";
            } else if (Count == 5) {
                NegativeMarking = "0.25";
            } else if (Count == 6) {
                NegativeMarking = "0.20";
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "calculteNegativeMarking", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForAddTest() {
        try {
            CreateJSON();
            DataAccess dataAcess = new DataAccess(getContext(), this);
            dataAcess.getMasterAddTest(AddTestString, AppUtility.MASTER_ADD_TEST);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "NetworkCallForAddTest", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            Master master = new Master();
            Gson gson = new Gson();
            master.setTestName(TestName);
            master.setTime(TestTime);
            master.setNegativeMarks(NegativeMarking);
            master.setUserName(AppUtility.KEY_USERNAME);
            String SubmitAdJson = gson.toJson(master);
            AddTestString = "[" + SubmitAdJson + "]";
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
                case 880:
                    masterResponse = (List<Master>) data;
                    try {
                        if (masterResponse.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, masterResponse.get(0).getResult().toString(), Snackbar.LENGTH_LONG).show();
                            txtTestName.setText("");
                            txtTestTime.setText("");
                            spinnerForNegativeMark.setSelection(0);
                            Count=0;
                        } else {
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(), "Error", masterResponse.get(0).getResult().toString());
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
        try {
            VollyResponse vollyResponse = new VollyResponse(view, getContext(), getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(), view, "TabTestMaster", "noResponse", e);
            pc.showCatchException();
        }
    }
}

