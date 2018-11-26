package com.mksmcqapplicationtest.TabStructure.Communication;

import android.app.Dialog;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomButton;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.User;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabChangePassword extends Fragment implements View.OnClickListener {

    private List<User> tests = null;
    CustomEditText txtOldPassword, txtNewPassword, txtReeneterPassword;
    String password, newpassword, reenterpassword, respose, title = "", ChangePasswordArrayString;
    CustomButton btnSave;
    View parentLayout;
    SharedPreferences sharedPreferences;
    int hide1 = 0, hide2 = 0, hide3 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_change_password, container, false);
        try {
            setUI();
            title = " Change Password";
            txtOldPassword = parentLayout.findViewById(R.id.etdcurrentpassword);
            txtNewPassword =  parentLayout.findViewById(R.id.etdnewpassword);
            txtReeneterPassword =  parentLayout.findViewById(R.id.etdconfirmpassword);
            btnSave =  parentLayout.findViewById(R.id.btnchangepassword);
            if (AppUtility.IsDemoApplication) {
                btnSave.setClickable(false);
                btnSave.setEnabled(false);
            } else {
                btnSave.setEnabled(true);
                btnSave.setClickable(true);
            }

            btnSave.setOnClickListener(this);
            sharedPreferences = getContext().getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
            txtOldPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int DRAWABLE_LEFT = 0;
                    int DRAWABLE_TOP = 1;
                    int DRAWABLE_RIGHT = 2;
                    int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (txtOldPassword.getRight() - txtOldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (hide1 == 0) {
                                txtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                hide1 = 1;
                                txtOldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            } else {
                                txtOldPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                                txtOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                hide1 = 0;
                            }

                        }
                    }

                    return false;
                }
            });


            txtNewPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int DRAWABLE_LEFT = 0;
                    int DRAWABLE_TOP = 1;
                    int DRAWABLE_RIGHT = 2;
                    int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (txtNewPassword.getRight() - txtNewPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (hide2 == 0) {
                                txtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                hide2 = 1;
                                txtNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            } else {
                                txtNewPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                                txtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                hide2 = 0;
                            }

                        }
                    }

                    return false;
                }
            });

            txtReeneterPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int DRAWABLE_LEFT = 0;
                    int DRAWABLE_TOP = 1;
                    int DRAWABLE_RIGHT = 2;
                    int DRAWABLE_BOTTOM = 3;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (txtReeneterPassword.getRight() - txtReeneterPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (hide3 == 0) {
                                txtReeneterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                hide3 = 1;
                                txtReeneterPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            } else {
                                txtReeneterPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                                txtReeneterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                hide3 = 0;
                            }

                        }
                    }

                    return false;
                }
            });




            if (AppUtility.IsTeacher.equals("G")) {
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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MaterialDesignChangePasswordActivity", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
      CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.changepassword_btn_text));

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
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MaterialDesignChangePasswordActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnchangepassword:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnSave);
                    bounce_button_class1.BounceMethod();

                    password = txtOldPassword.getText().toString().trim();
                    newpassword = txtNewPassword.getText().toString().trim();
                    reenterpassword = txtReeneterPassword.getText().toString().trim();
                    if (password.isEmpty() || newpassword.isEmpty() || reenterpassword.isEmpty()) {
                        Snackbar.make(parentLayout, "Please Enter All Password", Snackbar.LENGTH_LONG).show();
                    } else if (password.equals(AppUtility.KEY_PASSWORD)) {
                        if (newpassword.equals(reenterpassword)) {
                            AppUtility.KEY_NEW_PASSWORD = reenterpassword;
                            btnSave.setEnabled(false);


                        } else {
                            Snackbar.make(parentLayout, "New Password and Re-enter Password are not same", Snackbar.LENGTH_LONG).show();
                        }

                    } else {
                        Snackbar.make(parentLayout, "Old Password is not correct", Snackbar.LENGTH_LONG).show();
                    }


                    break;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MaterialDesignChangePasswordActivity", "onClick", e);
            pc.showCatchException();
        }

    }


}

