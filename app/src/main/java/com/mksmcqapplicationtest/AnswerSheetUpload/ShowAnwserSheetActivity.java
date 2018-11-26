package com.mksmcqapplicationtest.AnswerSheetUpload;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.MaterialDesignLoginActivity;
import com.mksmcqapplicationtest.PrintCatchException;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.Student;

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowAnwserSheetActivity extends AppCompatActivity implements ResponseListener {

    View parentLayout;
    Bundle bundle;
    String DataCodeForNetworkCall, ClassCodeForNetworkCall, DataSendResponseJSONString;
    SharedPreferences sharedPreferences;
    ImageView dataimage;
    PhotoViewAttacher mAttacher;
    List<Student> studentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_anwser_sheet);
        try {
            dataimage = (ImageView) findViewById(R.id.dataimage);
            bundle = getIntent().getExtras();
            try {
                if (bundle != null) {
                    DataCodeForNetworkCall = bundle.getString("DataCode");
                }
                sharedPreferences = getSharedPreferences(MaterialDesignLoginActivity.PREFS_NAME, 0);
                ClassCodeForNetworkCall = sharedPreferences.getString("ClassCodeForShowAnswersheet", null);
            } catch (Exception e) {

            }

            parentLayout = findViewById(android.R.id.content);

            NetworkCallForShowAnwserSheet();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" Show Anwser Sheet");
            getSupportActionBar().setIcon(R.mipmap.ic_launcher_logo_c);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ShowAnwserSheetActivity.this,parentLayout, "ShowAnwserSheetActivity", "OnCreate", e);
            pc.showCatchException();
        }
    }


    private void NetworkCallForShowAnwserSheet() {
        try {
            CreateJSONForUploadAnwserSheet();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ShowAnwserSheetActivity.this,parentLayout, "ShowAnwserSheetActivity", "NetworkCallForShowAnwserSheet", e);
            pc.showCatchException();
        }
    }

    private void CreateJSONForUploadAnwserSheet() {
        try {
            Gson gson = new Gson();
            Data dataresponse = new Data();
            dataresponse.setUserName(AppUtility.KEY_USERNAME);
            dataresponse.setDataCode(DataCodeForNetworkCall);
            dataresponse.setStudentCode(AppUtility.KEY_STUDENTCODE);
            String jsonData = gson.toJson(dataresponse);
            DataSendResponseJSONString = "[" + jsonData + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ShowAnwserSheetActivity.this,parentLayout, "ShowAnwserSheetActivity", "CreateJSONForUploadAnwserSheet", e);
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

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 610:
                    studentResponse = (List<Student>) data;
                    if (studentResponse.get(0).getResult().equals("1")) {
                        if (studentResponse.get(0).getAnswerSheet().equals("")) {
                            Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                        } else if (studentResponse.get(0).getAnswerSheet().equals(null)) {
                            Snackbar.make(parentLayout, "No Data Found", Snackbar.LENGTH_LONG).show();
                        } else {
                            String AnswerSheetPath = studentResponse.get(0).getAnswerSheet();
                            loadimage(AnswerSheetPath);
                            mAttacher.update();
//                            Snackbar.make(parentLayout, "OK", Snackbar.LENGTH_LONG).show();
                        }

                    } else if (studentResponse.get(0).getResult().equals("No Data Found")) {
                        Snackbar.make(parentLayout, "No Data Found", Snackbar.LENGTH_LONG).show();

                    } else if (studentResponse.get(0).getResult().equals("0")) {
                        String Msg = studentResponse.get(0).getMessage().toString();
                        Snackbar.make(parentLayout, "" + Msg, Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                    }
                    break;
            }
        } catch (Exception ex) {

        }
    }

    private void loadimage(final String answerSheetPath) {
        try {

            String Msg = "Loading Please Wait";
            String url = AppUtility.baseUrl + answerSheetPath;
            Picasso.with(this)
                    .load(url)
                    .resize(AppUtility.KEY_WIDTH, AppUtility.KEY_WIDTH)
//                    .centerInside()
                    .into(dataimage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    try {
                                        Snackbar.make(parentLayout, "Unable to load image", Snackbar.LENGTH_LONG)
                                                .setAction("Retry", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        loadimage(answerSheetPath);
                                                    }
                                                }).show();


                                    } catch (Exception ex) {
                                    }
                                }
                            }
                    );
            mAttacher = new PhotoViewAttacher(dataimage);

        } catch (Exception ex) {

        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,ShowAnwserSheetActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ShowAnwserSheetActivity.this,parentLayout, "ShowAnwserSheetActivity", "noResponse", e);
            pc.showCatchException();
        }
    }
}
