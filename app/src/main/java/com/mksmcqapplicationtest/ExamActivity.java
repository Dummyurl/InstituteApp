package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.ProgressDialog.ProgressDialogShow;
import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.Synchronize;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.tour.MaterialShowcaseSequence;
import com.mksmcqapplicationtest.tour.ShowcaseConfig;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private List<Question> questions = new ArrayList<Question>();
    public List<Synchronize> synchronizes;
    private List<Response> responses;
    int questionAttempt1;
    TextView text_error;
    float marks = 0.0f;
    int correctAnsCount;
    int questionAttempt;
    int wrongAnsCount;
    public static TextView txtQuestion, textViewQuestionLable, txtTime, txtQuestionLeft, txtMarks, textQueCount;
    public static RadioGroup radioGroup;
    private Button btnPrev, btnNext;
    int currentQuestion = 0, testAttempt;
    String testID, testName, ClassName;
    int testDuration;
    Bundle bundle;
    private static CountDownTimer timer;
    int width;
    String title = "";
    Typeface font1;
    ImageView imageViewBeforeQuestion, imageViewAfterQuestion;
    ProgressBar progressBarBeforeImage, progressBarAfterImage;
    RelativeLayout relativeLayoutBeforImage, relativeLayoutAfterImage;
    String BeforeImagePath = "", AfterImagePath = "";
    Context context;
    View parentLayout;
    String FontAswomeTimer;
    String NegativeMarkingString;
    float NegativeMarking = 0.0f;
    ArrayList<String> queAttemptList = new ArrayList<String>();
    String FontAswomePreviousButton, FontAswomeNextButton;
    ProgressDialogShow progressDialogClickClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        try {
            this.context = getApplicationContext();
            font1 = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
            FontAswomePreviousButton = getResources().getString(R.string.fa_previous);
            FontAswomeNextButton = getResources().getString(R.string.fa_next);
            FontAswomeTimer = getResources().getString(R.string.fa_stop_watch);
            AppUtility.IsFirstTimeHome = false;
            imageViewBeforeQuestion = (ImageView) findViewById(R.id.imageViewBeforeQuestion);
            imageViewAfterQuestion = (ImageView) findViewById(R.id.imageViewAfetrQuestion);
            imageViewBeforeQuestion.setVisibility(View.INVISIBLE);
            imageViewAfterQuestion.setVisibility(View.VISIBLE);
            relativeLayoutBeforImage = (RelativeLayout) findViewById(R.id.relativeLayoutBeforeImage);
            relativeLayoutAfterImage = (RelativeLayout) findViewById(R.id.relativeLayoutAfetrImage);
            progressBarBeforeImage = (ProgressBar) findViewById(R.id.progressbarbeforeImage);
            progressBarAfterImage = (ProgressBar) findViewById(R.id.progressbarafterImage);
            txtQuestion = (TextView) findViewById(R.id.textViewQuestion);
            textViewQuestionLable = (TextView) findViewById(R.id.textViewQuestionLable);
            txtMarks = (TextView) findViewById(R.id.Marks);
            textQueCount = (TextView) findViewById(R.id.textQueCount);
            txtTime = (TextView) findViewById(R.id.textViewTime);
            radioGroup = (RadioGroup) findViewById(R.id.radioGroupOptions);
            btnNext = (Button) findViewById(R.id.buttonNext);
            btnNext.setText(FontAswomeNextButton);
            btnNext.setTypeface(font1);
            btnNext.setOnClickListener(this);

            btnPrev = (Button) findViewById(R.id.buttonPrev);
            btnPrev.setText(FontAswomePreviousButton);
            btnPrev.setTypeface(font1);
            btnPrev.setOnClickListener(this);
            parentLayout = findViewById(android.R.id.content);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                testID = bundle.getString(AppUtility.KEY_TEST_ID);
                testName = bundle.getString(AppUtility.KEY_TEST_NAME);
                testDuration = bundle.getInt("Test Time");
                ClassName = bundle.getString("ClassName");
                NegativeMarkingString = bundle.getString("NegativeMarking");
                InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(ExamActivity.this);
                boolean availble = internetConnectionCheck.checkConnection();
                if (availble) {
                    loadQuestion(testID);
                }

            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "OnCreate", e);
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
    public void onBackPressed() {
        try {
            final Dialog dialog = new Dialog(ExamActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Exit!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);


            txtMessage.setText("Are you sure? You want to stop exam");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("Yes");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("No");
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "onBackPressed", e);
            pc.showCatchException();
        }
    }


    private void loadQuestion(String testId) {
        try {
            if (testId == null) {
                Intent intent = new Intent(ExamActivity.this, MaterialDesignMainActivity.class);
                startActivity(intent);
            } else {
                progressDialogClickClass = new ProgressDialogShow
                        (ExamActivity.this, AppUtility.ProgressDialogType, AppUtility.ProgressDialogColor, "Loading. Please wait...");
                progressDialogClickClass.show();
                DataAccess dataAccess = new DataAccess(this, this);
                dataAccess.getQuestionsOf(testId);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "loadQuestion", e);
            pc.showCatchException();
        }
    }

    private String timeString;
    private long hr, min, sec;


    @Override
    public void onResponse(final Object data) {
        progressDialogClickClass.dismiss();
        try {
            if (data == null) {
                Snackbar.make(parentLayout, "Something went wrong please try again later", Snackbar.LENGTH_LONG).show();
            } else {
                questions = (List<Question>) data;
                if (questions.size() > 0) {
                    showCurrentQuestion(currentQuestion);
                    timer = new CountDownTimer((testDuration * 60) * 1000, 1000) {
                        @Override
                        public void onTick(long l) {

                            hr = TimeUnit.MILLISECONDS.toHours(l);
                            min = TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(hr);
                            sec = TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(l);
                            timeString = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                                    TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)),
                                    TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                            txtTime.setText(FontAswomeTimer + " " + timeString);
                            txtTime.setTypeface(font1);
                            txtTime.setTypeface(txtTime.getTypeface(), Typeface.BOLD);
                        }

                        @Override
                        public void onFinish() {
                            //todo submit exam
                            onSubmitTest();
                        }
                    }.start();
                } else {

                    final Dialog dialog = new Dialog(ExamActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog);
                    TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                    txtTitle.setText("Alert!!");
                    TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                    txtMessage.setText("Sorry!! Test Has No Questions");
                    final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                    dialogButton.setText("OK");
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivity.this, dialogButton);
                            bounce_button_class.BounceMethod();
                            ExamActivity.this.finish();
                        }
                    });
                    Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                    CancledialogButton.setText("No");
                    CancledialogButton.setVisibility(View.GONE);
                    CancledialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        } catch (Exception ex) {
            Snackbar.make(parentLayout, "" + ex, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 250:
                    synchronizes = (List<Synchronize>) data;
                    try {

                        int QuestionSize = questions.size();
                        String TQuestionSize = "Question Size: " + "       " + QuestionSize + "\n";
                        String QuestionAttempt = "Question Attempt: " + "   " + questionAttempt + "\n";
                        String CorrectAttempt = "Correct Attempt: " + "     " + correctAnsCount + "\n";
                        String WrongAttempt = "Wrong Attempt:    " + "    " + wrongAnsCount + "\n";
                        String marksEarned = "Marks Earned:    " + "      " + marks + "\n";
                        final Dialog dialog = new Dialog(ExamActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        text_error = (TextView) dialog.findViewById(R.id.text_error);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Result of " + testName);
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText(TQuestionSize + "\n" + QuestionAttempt + "\n" + CorrectAttempt + "\n" + WrongAttempt + "\n" + marksEarned);
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Try Again");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivity.this, dialogButton);
                                bounce_button_class.BounceMethod();
                                dialog.dismiss();
                                finish();
                            }
                        });
                        final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                        CancledialogButton.setText("Home");
                        CancledialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivity.this, CancledialogButton);
                                bounce_button_class.BounceMethod();
                                dialog.dismiss();
                                Intent intent = new Intent(ExamActivity.this, MaterialDesignMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                        if (synchronizes.get(0).getResult().equals("Test Result Save SuccessFully")) {
                            Snackbar.make(parentLayout, "Test Result Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            text_error.setText(synchronizes.get(0).getResult());
                            text_error.setTextColor(Color.GREEN);
                            text_error.setVisibility(View.VISIBLE);
                        } else if (synchronizes.get(0).getResult().equals("Test Result Not Save SuccessFully")) {
                            Snackbar.make(parentLayout, "Test Result Not Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            text_error.setText(synchronizes.get(0).getResult());
                            text_error.setTextColor(Color.RED);
                            text_error.setVisibility(View.VISIBLE);
                        } else if (synchronizes.get(0).getResult().equals("Something Went Wrong")) {
                            Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                            text_error.setText(synchronizes.get(0).getResult());
                            text_error.setTextColor(Color.RED);
                            text_error.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                    }
                    break;

                case 2500:
                    responses = (List<Response>) data;
                    try {
                        int QuestionSize = questions.size();
                        String TQuestionSize = "Question Size:  " + "       " + QuestionSize + "\n";
                        String QuestionAttempt = "Questio nAttempt: " + "   " + questionAttempt + "\n";
                        String CorrectAttempt = "Correct Attempt: " + "     " + correctAnsCount + "\n";
                        String WrongAttempt = "Wrong Attempt:    " + "    " + wrongAnsCount + "\n";
                        String marksEarned = "Marks Earned:    " + "      " + marks + "\n";
                        final Dialog dialog = new Dialog(ExamActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Result of " + testName);
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText(TQuestionSize + "\n" + QuestionAttempt + "\n" + CorrectAttempt + "\n" + WrongAttempt + "\n" + marksEarned);
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Try Again");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivity.this, dialogButton);
                                bounce_button_class.BounceMethod();
                                dialog.dismiss();
                                finish();
                            }
                        });
                        final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                        CancledialogButton.setText("Home");
                        CancledialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivity.this, CancledialogButton);
                                bounce_button_class.BounceMethod();
                                dialog.dismiss();
                                Intent intent = new Intent(ExamActivity.this, MaterialDesignMainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                        if (responses.get(0).getResultCode().equals("1")) {
                            Snackbar.make(parentLayout, responses.get(0).getResult(), Snackbar.LENGTH_LONG).show();

                        } else if (responses.get(0).getResultCode().equals("0")) {
                            Snackbar.make(parentLayout, responses.get(0).getResult(), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void calculateScreenSize() {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            (ExamActivity.this).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            width = metrics.widthPixels;
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "calculateScreenSize", e);
            pc.showCatchException();
        }
    }

    private void loadimagebeforequestion(String ImagePath, ImageView imageView) {
        try {
            String image = AppUtility.baseUrl + ImagePath;
            Picasso.with(this)
                    .load(image)
                    .resize(width, 100)
                    .into(imageView);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "loadimagebeforequestion", e);
            pc.showCatchException();
        }
    }

    private void loadimagebeforequestioninFullScreen(String ImagePath, final ImageView imageView, final Dialog dialog) {
        try {

            String image = AppUtility.baseUrl + ImagePath;
            Picasso.with(this)
                    .load(image)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("Ok", "");
                        }

                        @Override
                        public void onError() {
                            Log.d("Dismis", "");
                            dialog.dismiss();
                        }
                    });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "loadimagebeforequestioninFullScreen", e);
            pc.showCatchException();
        }

    }

    private void loadimageafterequestion(String ImagePath) {
        try {
            String image = AppUtility.baseUrl + ImagePath;
            Picasso.with(this)
                    .load(image)
                    .resize(width, 100)
                    .into(imageViewAfterQuestion);

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "loadimageafterequestion", e);
            pc.showCatchException();
        }
    }


    private void showCurrentQuestion(final int qId) {
        try {
            if (currentQuestion + 1 == questions.size()) {
                btnNext.setText("Submit");

            } else {
                btnNext.setText(FontAswomeNextButton);
            }
            BeforeImagePath = "";
            AfterImagePath = "";
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 5;
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.bottomMargin = 5;
            int size = currentQuestion + 1;
            int questionSize = questions.size();
            int marks = questions.get(qId).getMarks();
            BeforeImagePath = questions.get(qId).getBeforImagePath();
            AfterImagePath = questions.get(qId).getAfterImagePath();
            if (!(BeforeImagePath.equals(""))) {
//                RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,80);
//                relativeLayoutBeforImage.setLayoutParams(lp);
                relativeLayoutBeforImage.getLayoutParams().height=100;

                imageViewBeforeQuestion.setVisibility(View.VISIBLE);
                loadimagebeforequestion(BeforeImagePath, imageViewBeforeQuestion);
                imageViewBeforeQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        loadimagebeforequestioninFullScreen(BeforeImagePath, fullScreenImage, dialog);
                        dialog.setView(dialogLayout);

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        dialog.show();
                    }
                });

            } else {
                relativeLayoutBeforImage.getLayoutParams().height = 1;
                imageViewBeforeQuestion.setVisibility(View.INVISIBLE);
            }
            if (!(AfterImagePath.equals(""))) {
                relativeLayoutAfterImage.getLayoutParams().height = 100;
                imageViewAfterQuestion.setVisibility(View.VISIBLE);
                loadimageafterequestion(AfterImagePath);
                imageViewAfterQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        loadimagebeforequestioninFullScreen(AfterImagePath, fullScreenImage, dialog);
                        dialog.setView(dialogLayout);

                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        dialog.show();
                    }

                });
            } else {
                relativeLayoutAfterImage.getLayoutParams().height = 1;
                imageViewAfterQuestion.setVisibility(View.INVISIBLE);
            }

            try {
                txtMarks.setText("Marks: " + String.valueOf(marks));

                CustomTextViewBold txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
                txtactionbartitle.setText(testName);
                ImageButton imagviewbackpress = (ImageButton) findViewById(R.id.imagviewbackpress);
                imagviewbackpress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                textQueCount.setText("Q. " + size + " / " + questionSize);
                textViewQuestionLable.setText("Question: ");
                txtQuestion.setText(questions.get(qId).getQuestionString().toString());

            } catch (Exception e) {
                Log.d("Exception", "+e");
            }
            radioGroup.removeAllViews();
            radioGroup.clearCheck();
            int i = 1;
            Object optionImages = questions.get(qId).getOptionImages();

            for (Object option : questions.get(qId).getOptions()) {
                LinearLayout linearLayout=(LinearLayout)getLayoutInflater().inflate(R.layout.radio_layout, null);
                RadioButton radioButton = (RadioButton) linearLayout.findViewById(R.id.radoButton);
                ImageView imageView = (ImageView) linearLayout.findViewById(R.id.imageView);
                if (!option.toString().isEmpty()) {
                    radioButton.setText(option.toString());
                    radioButton.setId(i);
                    radioGroup.addView(linearLayout, layoutParams);
                    if (questions.get(currentQuestion).getSelectedOption() != -1) {
                        int p = radioButton.getId();
                        if (radioButton.getId() == questions.get(currentQuestion).getSelectedOption()) {
                            radioButton.setChecked(true);
                        }
                    }

                    if (i == 1) {
                        if (!(questions.get(qId).getAnsAImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsAImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsAImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }

                    } else if (i == 2) {
                        if (!(questions.get(qId).getAnsBImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsBImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsBImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }
                    } else if (i == 3) {
                        if (!(questions.get(qId).getAnsCImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsCImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsCImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }
                    } else if (i == 4) {
                        if (!(questions.get(qId).getAnsDImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsDImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsDImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }
                    } else if (i == 5) {
                        if (!(questions.get(qId).getAnsEImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsEImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsEImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }
                    } else if (i == 6) {
                        if (!(questions.get(qId).getAnsFImagePath().equals(""))) {
                            imageView.setVisibility(View.VISIBLE);
                            loadimagebeforequestion(questions.get(qId).getAnsFImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsFImagePath());
                                }
                            });
                        } else {
                            Log.d("Error", "Image blank");
                        }
                    }

//                    radioGroup.addView(imageView, layoutParams);
                } else if (!optionImages.toString().isEmpty()) {
                    if (i == 1) {
                        if (!questions.get(qId).getAnsAImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsAImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsAImagePath());
                                }
                            });
                        }
                    } else if (i == 2) {
                        if (!questions.get(qId).getAnsBImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsBImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsBImagePath());
                                }
                            });
                        }
                    } else if (i == 3) {
                        if (!questions.get(qId).getAnsCImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsCImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsCImagePath());
                                }
                            });
                        }
                    } else if (i == 4) {
                        if (!questions.get(qId).getAnsDImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsDImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsDImagePath());
                                }
                            });
                        }
                    } else if (i == 5) {
                        if (!questions.get(qId).getAnsEImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsEImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loadImageIntoFullScreen(questions.get(qId).getAnsEImagePath());
                                }
                            });
                        }
                    } else if (i == 6) {
                        if (!questions.get(qId).getAnsFImagePath().toString().equals("")) {
                            imageView.setVisibility(View.VISIBLE);
                            radioGroup.addView(linearLayout, layoutParams);
                            loadimagebeforequestion(questions.get(qId).getAnsFImagePath(), imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    loadImageIntoFullScreen(questions.get(qId).getAnsFImagePath());
                                }
                            });
                        }
                    }
                    radioButton.setId(i);

                    if (questions.get(currentQuestion).getSelectedOption() != -1) {
                        int p = radioButton.getId();
                        if (radioButton.getId() == questions.get(currentQuestion).getSelectedOption()) {
                            radioButton.setChecked(true);
                        }
                    }
//                    radioGroup.addView(linearLayout, layoutParams);

                } else {
                    radioButton.setText(option.toString());
                    radioButton.setId(i);
                    radioGroup.addView(linearLayout, layoutParams);
                    if (questions.get(currentQuestion).getSelectedOption() != -1) {
                        int p = radioButton.getId();
                        if (radioButton.getId() == questions.get(currentQuestion).getSelectedOption()) {
                            radioButton.setChecked(true);
                        }
                    }
                }

                radioButton.setOnClickListener(this);
                i++;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "showCurrentQuestion", e);
            pc.showCatchException();
        }
    }


    public void loadImageIntoFullScreen(String ImagePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);

        if (!(ImagePath.equals("")))
            loadimagebeforequestioninFullScreen(ImagePath, fullScreenImage, dialog);

        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void setCurrentAnswer(int qId, String selectedAnswer) {
        questions.get(qId).setSelectedAnswer(selectedAnswer);
    }

    private void setCurrentOption(int qId, int indexOfSelectedOption) {
        questions.get(qId).setCorrectOption(indexOfSelectedOption);
    }

    private void setIndexOfRadioButton(int qId, String id) {
        questions.get(qId).setSelectedRadioButtonIndex(id);

    }

    @Override
    public void noResponse(String error) {
        try {
            progressDialogClickClass.dismiss();
            VollyResponse vollyResponse = new VollyResponse(parentLayout, ExamActivity.this, getResources().getString(R.string.Error_Msg), error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivity.this, parentLayout, "ExamActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            if (radioGroup.getCheckedRadioButtonId() == -1) {

            } else {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton currentClicked = (RadioButton) findViewById(selectedId);
                if (currentClicked != null) {
                    int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                    String select = "";
                    if (selectedId == 1) {
                        select = "A";
                    } else if (selectedId == 2) {
                        select = "B";
                    } else if (selectedId == 3) {
                        select = "C";
                    } else if (selectedId == 4) {
                        select = "D";
                    } else if (selectedId == 5) {
                        select = "E";
                    } else if (selectedId == 6) {
                        select = "F";
                    }

                    setCurrentOption(currentQuestion, selectedId);
                    setCurrentAnswer(currentQuestion, currentClicked.getText().toString());

                    setIndexOfRadioButton(currentQuestion, select);

                }

            }


            Button currentClicked1 = (Button) view;
            switch (currentClicked1.getId()) {
                case R.id.buttonNext:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(this, btnNext);
                    bounce_button_class1.BounceMethod();
                    if (NegativeMarkingString.equals("0")) {
                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(ExamActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        btnPrev.setClickable(true);
                        currentQuestion++;
                        if (currentQuestion == questions.size()) {
                            if (NegativeMarkingString.equals("0")) {
                                if (radioGroup.getCheckedRadioButtonId() != -1) {
                                    onSubmitTest();
                                } else {
                                    Toast.makeText(ExamActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                onSubmitTest();
                            }
                        } else {
                            showCurrentQuestion(currentQuestion);
                        }

                    } else {
                        btnPrev.setClickable(true);
                        currentQuestion++;
                        if (currentQuestion == questions.size()) {
                            if (NegativeMarkingString.equals("0")) {
                                if (radioGroup.getCheckedRadioButtonId() != -1) {
                                    onSubmitTest();
                                } else {
                                    Toast.makeText(ExamActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                onSubmitTest();
                            }
                        } else {
                            showCurrentQuestion(currentQuestion);
                        }

                    }
                    break;
                case R.id.buttonPrev:
                    Bounce_Button_Class bounce_button_class2 = new Bounce_Button_Class(this, btnPrev);
                    bounce_button_class2.BounceMethod();
                    if (currentQuestion <= 0) {
                        if (currentQuestion == 0) {
                            Toast.makeText(ExamActivity.this, "First Question", Toast.LENGTH_SHORT).show();
                        } else {
                            showCurrentQuestion(0);
                        }
                    } else {
                        currentQuestion--;
                        showCurrentQuestion(currentQuestion);
                        btnNext.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        } catch (Exception e) {
        }
    }

    private void onSubmitTest() {
        try {

            if (timer != null) {
                timer.cancel();
                txtTime.setText("");
                timer = null;

            }
            questionAttempt1 = QuestionAttempt();

            float totalMarks = calculateMarks();
            if (totalMarks != 7000) {
                marks = calculateMarks();
            } else {
                Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
            }
            correctAnsCount = correctAns();
            questionAttempt = questions.size() - questionAttempt1;

            wrongAnsCount = questionAttempt - correctAnsCount;
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, h:mm a");
            String dateString = sdf.format(date);
            AppUtility.KEY_TEST_SUBMIT_TIME = dateString;
            String UserName = AppUtility.KEY_USERNAME;
            DataAccess dataAccess = new DataAccess(this, this);
            Gson gson = new Gson();
            Test submitTest = new Test();
            submitTest.setClassName(ClassName);
            submitTest.setUsername(UserName);
            submitTest.setIsTeacher(AppUtility.IsTeacher);
            submitTest.setTestName(testName);
            submitTest.setTestCode(testID);
            double ss = Math.round(marks * 100.0) / 100.0;
            String MarkString = String.valueOf(ss);
            submitTest.setCurrentMarks(MarkString);
            submitTest.setAttemptQuestion(questions.size());
            submitTest.setQuestions(questions);
            submitTest.setCorrectAttemptQuestion(correctAnsCount);
            submitTest.setWrongAttemptQuestion(wrongAnsCount);
            submitTest.setNegativeMarks(NegativeMarkingString);
            String testJson = gson.toJson(submitTest);
            dataAccess.SubmiteTestToServer(AppUtility.SYNCHRONIZATION_URL, testJson);
        } catch (Exception e) {

        }
    }

    private float calculateMarks() {
        try {
            NegativeMarking = Float.valueOf(NegativeMarkingString);
            float totalMarks = 0;
            for (Question q : questions) {
//                if (q.getCorrectAnswer().toString().equals(q.getSelectedAnswer())) {
                if (q.getCorrectOption().toString().equals(q.getSelectedRadioButtonIndex())) {
                    totalMarks = totalMarks + q.getMarks();
                } else {//Negative Marking Logic
                    if (NegativeMarking != 0) {
                        if (queAttemptList.size() > 0) {
                            if (queAttemptList.contains(q.getId())) {
                                float deductMark = q.getMarks() * NegativeMarking;
                                totalMarks = totalMarks - deductMark;
                            }
                        }
                    }
                }
            }
            return totalMarks;
        } catch (Exception e) {
            Snackbar.make(parentLayout, "" + e, Snackbar.LENGTH_SHORT).show();
            return 7000;
        }

    }

    private int correctAns() {
        int correctQuestionCount = 0;
        for (Question q : questions) {
            if (q.getCorrectOption().toString().equals(q.getSelectedRadioButtonIndex())) {
//            if (q.getCorrectAnswer().toString().equals(q.getSelectedAnswer())) {
                correctQuestionCount++;
            }
        }
        return correctQuestionCount;
    }

    private int QuestionAttempt() {
        int QuestioonAttempt = 0;
        for (Question q : questions) {
            if (q.getSelectedAnswer() == null) {
                QuestioonAttempt++;
            } else {
                queAttemptList.add(q.getId());
            }
        }
        return QuestioonAttempt;
    }

}



