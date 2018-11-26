package com.mksmcqapplicationtest.TabStructure.Communication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabChangePasswordForAll extends Fragment implements View.OnClickListener {

    String StudentNameArrayString, ChangePasswordArrayString;
    Spinner spinn_for_student, spinn_for_class;
    CustomButton btnsubmitforchangepass;
    EditText edttxtconfirmpass, edttxtnewpass;
    int REQUEST_PASSWORD_CHANGE = 140;
    private List<User> tests = null;
    String conpassword, newpassword;
    String ClassNameJSONString;
    List<Student> studentResponse;
    List<Class> classResponse;
    String[] UserNameDropDownforclass = new String[1];
    String[] UserNameDropDownforstudent = new String[1];
    View parentLayout;
    int j = 0, count;
    String classCode, studentCode, UserName;
    int hide1 = 0, hide2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_change_password_for_all, container, false);
        try {
            setUI();
            if (AppUtility.IsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
                advertiesClass.ShowAdver();
            }

            btnsubmitforchangepass.setOnClickListener(this);
            if (AppUtility.IsDemoApplication) {
                btnsubmitforchangepass.setClickable(false);
                btnsubmitforchangepass.setEnabled(false);

            } else {
                btnsubmitforchangepass.setClickable(true);
                btnsubmitforchangepass.setEnabled(true);
            }

            edttxtconfirmpass.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int DRAWABLE_LEFT = 0;
                    int DRAWABLE_TOP = 1;
                    int DRAWABLE_RIGHT = 2;
                    int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (edttxtconfirmpass.getRight() - edttxtconfirmpass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (hide1 == 0) {
                                edttxtconfirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                hide1 = 1;
                                edttxtconfirmpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            } else {
                                edttxtconfirmpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                                edttxtconfirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                hide1 = 0;
                            }

                        }
                    }

                    return false;
                }
            });


            edttxtnewpass.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int DRAWABLE_LEFT = 0;
                    int DRAWABLE_TOP = 1;
                    int DRAWABLE_RIGHT = 2;
                    int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (edttxtnewpass.getRight() - edttxtnewpass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (hide2 == 0) {
                                edttxtnewpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                hide2 = 1;
                                edttxtnewpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            } else {
                                edttxtnewpass.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                                edttxtnewpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                hide2 = 0;
                            }

                        }
                    }

                    return false;
                }
            });


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ChangePasswordByTeacher", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        spinn_for_class =  parentLayout.findViewById(R.id.spinnerForClass);
        spinn_for_student = parentLayout.findViewById(R.id.spinnerForStudentName);
        spinn_for_class.setAdapter(null);
        spinn_for_student.setAdapter(null);
        edttxtconfirmpass =  parentLayout.findViewById(R.id.etdconfirmpassword);
        edttxtnewpass = parentLayout.findViewById(R.id.etdnewpassword);
        btnsubmitforchangepass =  parentLayout.findViewById(R.id.btnchangepassword);

        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.changepassword_for_all_hint));

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
                case R.id.btnchangepassword:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnsubmitforchangepass);
                    bounce_button_class1.BounceMethod();

                    newpassword = edttxtnewpass.getText().toString().trim();
                    conpassword = edttxtconfirmpass.getText().toString().trim();

                    if (newpassword.isEmpty() && newpassword.isEmpty()) {
                        Snackbar.make(parentLayout, "Please Enter All Password", Snackbar.LENGTH_LONG).show();
                    } else if (!((classCode == "0") || (studentCode == "0"))) {
                        if (newpassword.equals(conpassword)) {
                            btnsubmitforchangepass.setEnabled(false);

                        } else {
                            Snackbar.make(parentLayout, "New Password and Confirm Password are not same", Snackbar.LENGTH_LONG).show();
                        }
                    } else if (classCode == "0") {
                        Snackbar.make(parentLayout, "Please Select Class Name", Snackbar.LENGTH_LONG).show();

                    } else if (studentCode == "0") {
                        Snackbar.make(parentLayout, "Please Select Student Name", Snackbar.LENGTH_LONG).show();

                    } else if (newpassword.isEmpty() || newpassword.equals("") || newpassword == null) {
                        Snackbar.make(parentLayout, "Please Enter New Password", Snackbar.LENGTH_LONG).show();
                    } else if (conpassword.isEmpty() || conpassword.equals("") || conpassword == null) {
                        Snackbar.make(parentLayout, "Please Enter CofirmPassword", Snackbar.LENGTH_LONG).show();

                    }
                    break;


            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "ChangePasswordByTeacher", "onClick", e);
            pc.showCatchException();
        }

    }

}

