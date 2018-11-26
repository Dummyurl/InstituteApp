package com.mksmcqapplicationtest.TabStructure.TabNotification;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Class;
import com.mksmcqapplicationtest.beans.InterstitialAdvertiesClass;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabSendNotification extends Fragment implements View.OnClickListener, ResponseListener {

    Button btnSendNotification;
    EditText etdNotificationText, etdNotificationTitle;
    Spinner spinnerForClassName;
    List<Student> StudentResponse;
    String ClassNameJSONString, ClassCodeForNetworkcall, sendNotificationJSONString, StudentNameArrayString;
    List<Class> classResponse;
    String[] UserNameDropDownForClass = new String[1];
    int count1, j1;
    View view;
    List<SendNotification> sendNotifications;
    CheckBox chkSendSMS, chkSentToGuest;
    Boolean isCheckBoxCheck = false;
    String title, text, isnotificationforguest = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_send_notification, container, false);
        try {
           setUI();
            if (AppUtility.IsDemoApplication) {
                btnSendNotification.setClickable(false);
                btnSendNotification.setEnabled(false);
            } else {
                btnSendNotification.setClickable(true);
                btnSendNotification.setEnabled(true);
            }



            btnSendNotification.setOnClickListener(this);
            chkSendSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    isCheckBoxCheck = b;
                }
            });
            chkSentToGuest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        isnotificationforguest = "1";
                    } else {
                        isnotificationforguest = "0";
                    }
                }
            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "OnCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        btnSendNotification = view.findViewById(R.id.btnSendNotification);
        etdNotificationText =  view.findViewById(R.id.etdNotificationText);
        etdNotificationTitle =  view.findViewById(R.id.etdNotificationTitle);
        spinnerForClassName =  view.findViewById(R.id.spinnerForClass);
        chkSendSMS = view.findViewById(R.id.chkSendSms);
        chkSentToGuest = view.findViewById(R.id.chkSendToGuest);

      CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Send_Notification));

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
                            spinnerForClassName.setAdapter(Select_Category_Adapter);

                            spinnerForClassName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count1 = position; //this would give you the id of the selected item
                                    if (count1 != 0) {
                                        ClassCodeForNetworkcall = classResponse.get(count1 - 1).getClassCode();
                                    } else {

                                        // recyclerViewStudent.setAdapter(null);
                                        ClassCodeForNetworkcall = "0";
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
                        Snackbar.make(view, "No Data Found..Please check internet connection and try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                    }
                    break;
                case 450:
                    try {
                        sendNotifications = (List<SendNotification>) data;
                        if (sendNotifications.get(0).getResultCode().equals("1")) {
                            Snackbar.make(view, "Notification send sucessfully", Snackbar.LENGTH_LONG).show();
                            spinnerForClassName.setSelection(0);
                            ClearAll();
                        } else if (sendNotifications.get(0).getResultCode().equals("0")) {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",sendNotifications.get(0).getResult().toString());
                        }
                        sendSmsMethod();
                    } catch (Exception ex) {
                        Snackbar.make(view, "Something went wrong please try again", Snackbar.LENGTH_LONG)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                    }
                    if (AppUtility.IsAdvertiesVisible) {
                        InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
                        interstitialAdvertiesClass.showInterstitialAdver();
                    }
                    break;

                case 180:
                    StudentResponse = (List<Student>) data;
                    try {

                        AppUtility.IndividualListResponse.clear();
                        AppUtility.IndividualListResponse = new ArrayList<>();
                        for (int i = 0; i < StudentResponse.size(); i++) {
                            if (StudentResponse.get(i).getResult().trim().equals("Suessfull")) {

                                Student AttendanceListResponse = new Student();
                                AttendanceListResponse.setStudentName(StudentResponse.get(i).getStudentName());
                                AttendanceListResponse.setResult(StudentResponse.get(i).getResult());
                                AttendanceListResponse.setStudentCode(StudentResponse.get(i).getStudentCode());
                                AttendanceListResponse.setMobileNumber(StudentResponse.get(i).getMobileNumber());
                                AppUtility.IndividualListResponse.add(AttendanceListResponse);
                            }
                        }

                    } catch (Exception ex) {
                    }
                    break;

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "noResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnSendNotification:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(getContext(), btnSendNotification);
                    bounce_button_class1.BounceMethod();

                    if ((etdNotificationText.getText().toString().equals("")) || (etdNotificationText.getText().toString().equals(null))) {
                        Snackbar.make(view, "Add Text For Notification", Snackbar.LENGTH_LONG).show();
                    } else if ((etdNotificationTitle.getText().toString().equals("")) || (etdNotificationTitle.getText().toString().equals(null))) {
                        Snackbar.make(view, "Add Title For Notification", Snackbar.LENGTH_LONG).show();
                    } else if (ClassCodeForNetworkcall.equals("0")) {
                        Snackbar.make(view, "Select Class", Snackbar.LENGTH_LONG).show();
                    } else {
//                    if (isCheckBoxCheck) {
                        title = etdNotificationTitle.getText().toString();
                        text = etdNotificationText.getText().toString();
//                    }
                        btnSendNotification.setEnabled(false);
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText(etdNotificationTitle.getText().toString());
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText("Are you sure ? You want to send " + etdNotificationText.getText().toString() + " this notification?");
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Yes");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                                bounce_button_class.BounceMethod();
                                btnSendNotification.setEnabled(true);

                                dialog.dismiss();
                            }
                        });
                        final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                        CancledialogButton.setText("No");
                        CancledialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                                bounce_button_class.BounceMethod();
                                btnSendNotification.setEnabled(true);
                                dialog.dismiss();
                                sendSmsMethod();
                            }
                        });

                        dialog.show();


                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "onClick", e);
            pc.showCatchException();
        }
    }



    public void ClearAll() {
        try {
            etdNotificationText.setText("");
            etdNotificationTitle.setText("");
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "ClearAll", e);
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
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "onRequestPermissionsResult", e);
            pc.showCatchException();
        }
    }

    public void SendSMSDialog() {
        try {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sms_dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText(title);
            final TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Are you sure ? You want to send " + text + " this SMS?");
//        txtMessage.setText(text);
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class.BounceMethod();

                    for (int i = 0; i < AppUtility.IndividualListResponse.size(); i++) {
                        String abcc = title + '\n' + text;
                        Boolean result = sendSMS(AppUtility.IndividualListResponse.get(i).getMobileNumber(), abcc);
                        if (result == false) {
                            SMSSendConfirmation("Message Not Sent");
                        }
                    }
                    SMSSendConfirmation("Message Sent Sucessfully");
                    dialog.dismiss();

                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "SendSMSDialog", e);
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

                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "SendNotificationFromTeacher", "SMSSendConfirmation", e);
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


    public void sendSmsMethod() {
        if (isSMSPermissionGranted()) {
            SendSMSDialog();
        }
    }

}

