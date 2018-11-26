package com.mksmcqapplicationtest.AnswerSheetUpload;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mksmcqapplicationtest.AboutUs;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.ImageSelectActivity;
import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AnswerSheetUploadActivity extends AppCompatActivity implements ResponseListener, View.OnClickListener {

    View parentLayout;
    Spinner spinnerForStudent;
    List<Student> studentResponse;
    String GetDataJSONString, StudentNameArrayString, DataUploadResponseJSONString;
    String[] UserNameDropDown = new String[1];
    int j = 0, count;
    String StrStudentName;
    Button btnbrowseImage, btnUploadAnswerSheet;
    Bitmap bmp;
    ImageView imageView;
    String StudentCodeForNetworkCall;
    Bundle bundle;
    String DataCodeForNetworkCall, ClassCodeForNetworkCall;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet_upload1);
        try {

            bundle = getIntent().getExtras();
            if (bundle != null) {
                DataCodeForNetworkCall = bundle.getString("DataCode");
            }
            sharedPreferences = getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
            ClassCodeForNetworkCall = sharedPreferences.getString("ClassCodeForAnswersheet", null);
            parentLayout = findViewById(android.R.id.content);
            spinnerForStudent = (Spinner) findViewById(R.id.spinnerForStudentName);
            spinnerForStudent.setAdapter(null);

            btnbrowseImage = (Button) findViewById(R.id.btnbrowseImage);
            btnbrowseImage.setOnClickListener(this);
            btnUploadAnswerSheet = (Button) findViewById(R.id.btnUploadAnswerSheet);
            btnUploadAnswerSheet.setOnClickListener(this);
            imageView = (ImageView) findViewById(R.id.dataimage);
            NetWorkCallForStudentList(ClassCodeForNetworkCall);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Upload Anwser Sheet");
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_logo_c);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "OnCreate", e);
            pc.showCatchException();
        }

    }


    private void NetWorkCallForStudentList(String classCode) {
        try {
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "NetWorkCallForStudentList", e);
            pc.showCatchException();
        }
    }


    private void NetworkCallForUploadAnwserSheet() {
        try {
            CreateJSONForUploadAnwserSheet();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "NetworkCallForUploadAnwserSheet", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForUploadAnwserSheet() {
        try {
            String ImageStringForJsonCreation = getStringImage(bmp);
            Gson gson = new Gson();
            Data dataresponse = new Data();
            dataresponse.setUserName(AppUtility.KEY_USERNAME);
            dataresponse.setStudentCode(StudentCodeForNetworkCall);
            dataresponse.setDataCode(DataCodeForNetworkCall);
            dataresponse.setAnswerSheet(ImageStringForJsonCreation);
            String jsonData = gson.toJson(dataresponse);
            DataUploadResponseJSONString = "[" + jsonData + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "CreateJSONForUploadAnwserSheet", e);
            pc.showCatchException();
        }
    }


    public String getStringImage(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        } else {
            return "";
        }
    }

    @Override
    public void onResponse(final Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {

                case 180:
                    try {
                        studentResponse = (List<Student>) data;
                        if (studentResponse.get(0).getResult().equals("Suessfull")) {
                            AppUtility.STUDENT_RESPONSE = studentResponse;
                            UserNameDropDown = new String[AppUtility.STUDENT_RESPONSE.size() + 1];
                            j = 0;
                            UserNameDropDown[j] = "Select Student Name...";
                            for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                                j = j + 1;
                                String Name = AppUtility.STUDENT_RESPONSE.get(i).getStudentName();
                                UserNameDropDown[j] = Name;
                            }

                            ArrayAdapter<String> Select_Category_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, UserNameDropDown);
                            Select_Category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerForStudent.setAdapter(Select_Category_Adapter);

                            spinnerForStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String spinner1 = parent.getItemAtPosition(position).toString();
                                    count = position;
                                    if (count != 0) {
                                        StudentCodeForNetworkCall = AppUtility.STUDENT_RESPONSE.get(count - 1).getStudentCode();
                                        StrStudentName = AppUtility.STUDENT_RESPONSE.get(count - 1).getStudentName();
                                    } else {
                                        StudentCodeForNetworkCall = "0";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

                        } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                            Snackbar.make(parentLayout, "No Data Found In Student", Snackbar.LENGTH_LONG).show();
                        } else if (studentResponse.get(0).getResult().equals("Something went wrong")) {
                            Snackbar.make(parentLayout, "Something went wrong", Snackbar.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "onResponseWithRequestCode", e);
                        pc.showCatchException();
                    }
                    break;

                case 620:
                    try {
                        studentResponse = (List<Student>) data;
                        if (studentResponse.get(0).getResult().equals("1")) {


                            final Dialog dialog = new Dialog(AnswerSheetUploadActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Sucessful!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText("AnswerSheet send to " + StrStudentName + " sucessfully");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("OK");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(AnswerSheetUploadActivity.this, dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    spinnerForStudent.setSelection(0);
                                    StudentCodeForNetworkCall = "0";
                                    StrStudentName = "";
                                    imageView.setImageBitmap(null);
                                    bmp = null;
                                    dialog.dismiss();
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("No");
                            CancledialogButton.setVisibility(View.GONE);
                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(AnswerSheetUploadActivity.this, dialogButton);
                                    bounce_button_class1.BounceMethod();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                            Snackbar.make(parentLayout, "No Data Found", Snackbar.LENGTH_LONG).show();

                        } else if (studentResponse.get(0).getResult().equals("0")) {
                            String Msg = studentResponse.get(0).getMessage().toString();
                            Snackbar.make(parentLayout, "" + Msg, Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "onResponseWithRequestCode", e);
                        pc.showCatchException();
                    }
                    break;
            }

        } catch (Exception ex) {

        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,AnswerSheetUploadActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnbrowseImage:
                    Intent intent = new Intent(this, ImageSelectActivity.class);
                    startActivityForResult(intent, 1);
                    break;

                case R.id.btnUploadAnswerSheet:
                    if (ClassCodeForNetworkCall.equals("0")) {
                        Snackbar.make(parentLayout, "Please Select Class Name", Snackbar.LENGTH_LONG).show();
                    } else if (StudentCodeForNetworkCall.equals("0")) {

                        Snackbar.make(view, "Please Select Student Name", Snackbar.LENGTH_LONG).show();
                    } else if (DataCodeForNetworkCall.equals("0")) {
                        Snackbar.make(view, "Please Select Data Name", Snackbar.LENGTH_LONG).show();
                    } else if (bmp == null) {
                        Snackbar.make(view, "Please Select Image to Upload", Snackbar.LENGTH_LONG).show();
                    } else {
                        NetworkCallForUploadAnwserSheet();

                    }

                    break;

            }
        } catch (Exception ex) {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    bmp = data.getParcelableExtra("Bitmap");
                    imageView.setImageBitmap(bmp);
                }
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(AnswerSheetUploadActivity.this,parentLayout, "AnswerSheetUploadActivity", "onActivityResult", e);
            pc.showCatchException();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}


