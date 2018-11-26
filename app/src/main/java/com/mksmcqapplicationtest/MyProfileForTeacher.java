package com.mksmcqapplicationtest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.mksmcqapplicationtest.util.Utility;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileForTeacher extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    Context context;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String userChoosenTask;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Bitmap thumbnail;
    String JSONForProfile;
    ProgressDialogShow progressDialogClickClass;
    List<Student> students;
    View view;
    ImageView image;
    Bitmap bmp;
    String JSONForUpdate;
    RecyclerView recyclerViewProfileUsername, recyclerViewProfilePassword;
    String password;
    CustomTextViewBold txtactionbartitle;
    ImageButton imageButtonLogout;
    LinearLayout linearLayoutProfile, linearLayoutCommunication, linearLayoutAboutUsDevelopBy, linearLayoutManual;
    CircleImageView profileImageIcon;
    CircleImageView imageButtonImageSelector;
    CustomTextViewBold txtNameOfStudent;
    TextView txtMobileNumber, txtEmailAddress, txtQueries, txtChangePassword, txtChangePasswordForAll,
            txtAboutUs, txtDevelopeBy, txtUserManual, txtVideoManual;
    LinearLayout linearQueries, linearChangePassword, linearChangePasswordForAll, linearAboutUs,
            linearDevelopBy, linearUserManual, linearVideoManual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.context = getApplicationContext();
        try {

            setUI();

            NetWorkCallForProfile();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "OnCreate", e);
            pc.showCatchException();
        }
    }

    private void setUI() {
        view = findViewById(android.R.id.content);
        linearLayoutProfile = (LinearLayout) findViewById(R.id.layoutProfile);
        linearLayoutCommunication = (LinearLayout) findViewById(R.id.layoutCommunication);
        linearLayoutAboutUsDevelopBy = (LinearLayout) findViewById(R.id.layoutAboutUsDevelopBy);
        linearLayoutManual = (LinearLayout) findViewById(R.id.layoutManual);

        ProfileDetails();
        CommunicationDetails();
        AboutUsDevelopByDetails();
        ManualDetails();

        txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.profile_title));
        imageButtonLogout = (ImageButton) findViewById(R.id.imageButtonLogout);
        imageButtonLogout.setVisibility(View.VISIBLE);
        imageButtonLogout.setOnClickListener(this);
    }


    private void ProfileDetails() {
        profileImageIcon = linearLayoutProfile.findViewById(R.id.profileImageIcon);
        imageButtonImageSelector = linearLayoutProfile.findViewById(R.id.imageButtonImageSelector);
        txtNameOfStudent = linearLayoutProfile.findViewById(R.id.txtNameOfStudent);
        txtMobileNumber = linearLayoutProfile.findViewById(R.id.txtMobileNumber);
        txtEmailAddress = linearLayoutProfile.findViewById(R.id.txtEmailAddress);
        imageButtonImageSelector.setOnClickListener(this);
    }

    private void CommunicationDetails() {
        linearQueries = linearLayoutCommunication.findViewById(R.id.linearQueries);
        linearChangePassword = linearLayoutCommunication.findViewById(R.id.linearChangePassword);
        linearChangePasswordForAll = linearLayoutCommunication.findViewById(R.id.linearChangePasswordForAll);

        txtQueries = linearLayoutCommunication.findViewById(R.id.txtQueries);
        txtChangePassword = linearLayoutCommunication.findViewById(R.id.txtChangePassword);
        txtChangePasswordForAll = linearLayoutCommunication.findViewById(R.id.txtChangePasswordForAll);

        linearQueries.setOnClickListener(this);
        linearChangePassword.setOnClickListener(this);
        linearChangePasswordForAll.setOnClickListener(this);
    }

    private void AboutUsDevelopByDetails() {
        linearAboutUs = linearLayoutAboutUsDevelopBy.findViewById(R.id.linearAboutUs);
        linearDevelopBy = linearLayoutAboutUsDevelopBy.findViewById(R.id.linearDevelopBy);

        txtAboutUs = linearLayoutAboutUsDevelopBy.findViewById(R.id.txtAboutUs);
        txtDevelopeBy = linearLayoutAboutUsDevelopBy.findViewById(R.id.txtDevelopeBy);

        linearAboutUs.setOnClickListener(this);
        linearDevelopBy.setOnClickListener(this);
    }

    private void ManualDetails() {
        linearUserManual = linearLayoutManual.findViewById(R.id.linearUserManual);
        linearVideoManual = linearLayoutManual.findViewById(R.id.linearVideoManual);

        txtUserManual = linearLayoutManual.findViewById(R.id.txtUserManual);
        txtVideoManual = linearLayoutManual.findViewById(R.id.txtVideoManual);

        linearUserManual.setOnClickListener(this);
        linearVideoManual.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                this.finish();
                break;
//            case R.id.action_profile_edit:
//                Intent intent = new Intent(MyProfileForTeacher.this, ImageSelectActivity.class);
//                startActivityForResult(intent, 1);
//                break;
            default:
                break;
        }
        return true;

    }

    private void NetWorkCallForProfile() {
        try {
            String Msg = "Loading Profile Please Wait";
            progressDialogClickClass = new ProgressDialogShow(MyProfileForTeacher.this, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, Msg);
            progressDialogClickClass.show();

            CreayeJSonForProfile();
            DataAccess dataAccess = new DataAccess(this, this);
            dataAccess.getProfile(JSONForProfile, AppUtility.GET_PROFILE);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "NetWorkCallForProfile", e);
            pc.showCatchException();
        }
    }

    private void CreayeJSonForProfile() {
        try {
            Student student = new Student();
            Gson gson = new Gson();
            student.setStudentCode(AppUtility.KEY_STUDENTCODE);
            student.setClassCode(AppUtility.KEY_CLASSCODE);
            student.setWhichProfile("StudentProfile");
            String profilejson = gson.toJson(student);
            JSONForProfile = "[" + profilejson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "CreayeJSonForProfile", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {
        try {
            progressDialogClickClass.dismiss();
            String Message = "Please Check internet Connection and retry";
            Snackbar.make(view, Message, Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NetWorkCallForProfile();
                        }
                    }).show();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "onResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        switch (requestCode) {
            case 480:
                progressDialogClickClass.dismiss();
                students = (List<Student>) data;
                try {
                    String result = students.get(0).getResult();
                    String Message;
                    switch (result) {
                        case "Suessfull":
                            txtNameOfStudent.setText(AppUtility.KEY_STUDENTNAME);
                            txtMobileNumber.setText(students.get(0).getMobileNumber());
                            txtEmailAddress.setText(students.get(0).getEmailAddress());
                            loadImage(profileImageIcon, students.get(0).getProfile());
                            break;
                        case "No Data Found":
                            Message = "No Data Found";
                            Snackbar.make(view, Message, Snackbar.LENGTH_LONG).show();
                            txtNameOfStudent.setText(AppUtility.KEY_STUDENTNAME);
                            txtMobileNumber.setText(AppUtility.KEY_USERNAME);
                            break;
                        case "Something went wrong":
                            Message = "Something went wrong";
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(MyProfileForTeacher.this, "Error", Message);
                            break;

                    }
                } catch (Exception ex) {
                    String Message = "Something went wrong";
                    Snackbar.make(view, Message, Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    NetWorkCallForProfile();
                                }
                            }).show();
                }
                break;
            case 490:
                students = (List<Student>) data;
                try {
                    String result = students.get(0).getResult();
                    switch (result) {
                        case "Image Save Successfully":
                            Snackbar.make(view, "Image Updated Successfully..", Snackbar.LENGTH_LONG).show();
                            break;
                        case "Unable To Save User Image":
                            ShowErrorPopUp showErrorPopUp = new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(MyProfileForTeacher.this, "Error", "Fail to update profile..");
                            break;
                    }
                } catch (Exception ex) {
                    Log.d("Exception", "" + ex);
                }
                break;
        }

    }

    private void loadImage(CircleImageView image, String profile) {
        try {
            if (profile != null && profile.equals("")) {
                String imageString = AppUtility.baseUrl + profile;
                Picasso.with(this)
                        .load(imageString).into(image);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "onResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            if (progressDialogClickClass != null) {
                progressDialogClickClass.dismiss();
            }
            VollyResponse vollyResponse = new VollyResponse(view, MyProfileForTeacher.this, getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
            txtNameOfStudent.setText(AppUtility.KEY_STUDENTNAME);
            txtMobileNumber.setText(AppUtility.KEY_USERNAME);

//            profileAdapter = new ProfileAdapter(this, "Username", AppUtility.KEY_USERNAME, R.string.fa_User);
//            recyclerViewProfileUsername.setAdapter(profileAdapter);
//            if (AppUtility.KEY_PASSWORD.length() > 2) {
//                String FirstTwoCharacter = AppUtility.KEY_PASSWORD.substring(0, 2);
//                String LastTeoCharacter = AppUtility.KEY_PASSWORD.substring(AppUtility.KEY_PASSWORD.length() - 2, AppUtility.KEY_PASSWORD.length());
//                password = FirstTwoCharacter + "******" + LastTeoCharacter;
//            } else {
//                password = AppUtility.KEY_PASSWORD;
//            }
//
//            profileAdapter = new ProfileAdapter(this, "Password", password, R.string.fa_password);
//            recyclerViewProfilePassword.setAdapter(profileAdapter);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "noResponse", e);
            pc.showCatchException();
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == 1) {
//                if (resultCode == RESULT_OK) {
//                    bmp = data.getParcelableExtra("Bitmap");
//                    image.setImageBitmap(bmp);
//                    NetworkcallUpdate();
//                }
//            }
//        } catch (Exception e) {
//            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "onActivityResult", e);
//            pc.showCatchException();
//        }
//    }


    private void NetworkcallUpdate() {
        try {
            CreayeJSonForUpdate();
            DataAccess dataAccess = new DataAccess(this, this);
            dataAccess.UpdateProfile(JSONForUpdate, AppUtility.UPDATE_PROFILE);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "NetworkcallUpdate", e);
            pc.showCatchException();
        }

    }

    private void CreayeJSonForUpdate() {
        try {
            String ImageStringForJsonCreation = getStringImage(bmp);
            Student student = new Student();
            Gson gson = new Gson();
            student.setClassCode("0");
            student.setStudentCode(AppUtility.KEY_STUDENTCODE);
            student.setStudentProfile(ImageStringForJsonCreation);
            student.setWhoseImage("Student");
            String profilejson = gson.toJson(student);
            JSONForUpdate = "[" + profilejson + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(MyProfileForTeacher.this, view, "MyProfileForTeacher", "CreayeJSonForUpdate", e);
            pc.showCatchException();
        }
    }

    public String getStringImage(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } else {
            return "";
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu_file, menu);
        Drawable drawable = menu.getItem(0).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageButtonLogout:
                break;
            case R.id.imageButtonImageSelector:
                selectImage();
                break;
            case R.id.linearQueries:
                break;
            case R.id.linearChangePassword:
                break;
            case R.id.linearChangePasswordForAll:
                break;
            case R.id.linearAboutUs:
                intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
            case R.id.linearDevelopBy:
                intent = new Intent(this, DevelopeBy.class);
                startActivity(intent);
                break;
            case R.id.linearUserManual:
                break;
            case R.id.linearVideoManual:
                break;


        }
    }

    private void selectImage() {
        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library",
                    "Cancel"};
            if (Build.VERSION.SDK_INT >= 23) {
                if (!checkPermission()) {
                    requestPermission();
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setIcon(R.mipmap.ic_launcher_logo_c);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(MyProfileForTeacher.this);

                    if (items[item].equals("Take Photo")) {
                        userChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();

                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask = "Choose from Library";
                        if (result)
                            galleryIntent();


                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();

                    }
                }
            });

            builder.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void galleryIntent() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    image.setImageBitmap(thumbnail);
                    NetworkcallUpdate();
                }

                //   onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA)
                    onCaptureImageResult(data);
            } else {
                this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onCaptureImageResult(Intent data) {
        try {
            thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}
