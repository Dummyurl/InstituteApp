package com.mksmcqapplicationtest.EnglishSpeaking;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.ExamActivity;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.MyBounceInterpolator;
import com.mksmcqapplicationtest.PrintCatchException;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.beans.Response;
import com.mksmcqapplicationtest.beans.Synchronize;
import com.mksmcqapplicationtest.beans.Test;

import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamActivityEnglishSpeaking extends AppCompatActivity implements ResponseListener, View.OnClickListener {

    int questionAttempt1;
    TextView text_error;
    float marks = 0.0f;
    int correctAnsCount;
    int questionAttempt;
    int wrongAnsCount;
    private List<Question> questions = new ArrayList<Question>();
    public List<Synchronize> synchronizes;
    private List<Response> responses;
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
    String NegativeMarkingString, AudioCode, Stage, PassOrFail = "0";
    float NegativeMarking = 0.0f;
    ArrayList<String> queAttemptList = new ArrayList<String>();
    int passingStage;
    String FontAswomePreviousButton, FontAswomeNextButton;

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
//        btnNext.setText(" NEXT" + "  " + FontAswomeNextButton+" ");
            btnNext.setText(FontAswomeNextButton);
            btnNext.setTypeface(font1);
            btnNext.setOnClickListener(this);
            btnPrev = (Button) findViewById(R.id.buttonPrev);
//        btnPrev.setText(" "+FontAswomePreviousButton + "  " + "PREVIOUS ");
            btnPrev.setText(FontAswomePreviousButton);
            btnPrev.setTypeface(font1);
            btnPrev.setOnClickListener(this);
            calculateScreenSize();

            parentLayout = findViewById(android.R.id.content);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                testAttempt = bundle.getInt(AppUtility.KEY_TEST_ATTEMPT);
                testID = bundle.getString(AppUtility.KEY_TEST_ID);
                testName = bundle.getString(AppUtility.KEY_TEST_NAME);
                testDuration = bundle.getInt("Test Time");
                NegativeMarkingString = bundle.getString("NegativeMarking");
                AudioCode = bundle.getString("AudioCode");
                Stage = bundle.getString("Stage");
                InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(ExamActivityEnglishSpeaking.this);
                boolean availble = internetConnectionCheck.checkConnection();
                if (availble) {
                    loadQuestion(testID);
                }
            }


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "onCreate", e);
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
            AppUtility.IsFirstTimeHome = false;
            AppUtility.IsFirstAudio = false;
            final Dialog dialog = new Dialog(ExamActivityEnglishSpeaking.this);
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
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, dialogButton);
                    bounce_button_class.BounceMethod();
                    onSubmitTest();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("No");
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "onBackPressed", e);
            pc.showCatchException();
        }
    }


    private void loadQuestion(String testId) {
        try {
            if (testId == null) {
                Intent intent = new Intent(ExamActivityEnglishSpeaking.this, HomeActivity.class);
                startActivity(intent);
            } else {

            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "loadQuestion", e);
            pc.showCatchException();
        }
    }

    private String timeString;
    private long hr, min, sec;


    @Override
    public void onResponse(final Object data) {
        try {
            if (data == null) {
//                Intent interCheckInternetConnection = new Intent(this, InterNetConnectionActivity.class);
//                startActivity(interCheckInternetConnection);
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

                    final Dialog dialog = new Dialog(ExamActivityEnglishSpeaking.this);
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
                            Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, dialogButton);
                            bounce_button_class.BounceMethod();
                            ExamActivityEnglishSpeaking.this.finish();
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

//                    new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher_logo_c)
//                            .setCancelable(false).setTitle("Alert!!")
//                            .setMessage("Sorry!!Test Has No Questions")
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ExamActivity.this.finish();
//                                }
//                            }).show();
//                    Snackbar.make(parentLayout, "Test has no questions", Snackbar.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "onResponse", ex);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            switch (requestCode) {
                case 820:
                    synchronizes = (List<Synchronize>) data;
                    if (synchronizes.size() > 0) {
                        try {

                            int QuestionSize = questions.size();
                            String TQuestionSize = "Question Size: " + "       " + QuestionSize + "\n";
                            String QuestionAttempt = "Question Attempt: " + "   " + questionAttempt + "\n";
                            String CorrectAttempt = "Correct Attempt: " + "     " + correctAnsCount + "\n";
                            String WrongAttempt = "Wrong Attempt:    " + "    " + wrongAnsCount + "\n";
                            String marksEarned = "Marks Earned:    " + "      " + marks + "\n";
                            final Dialog dialog = new Dialog(ExamActivityEnglishSpeaking.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            text_error = (TextView) dialog.findViewById(R.id.text_error);
                            text_error.setVisibility(View.VISIBLE);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Result of " + testName);
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            txtMessage.setText(TQuestionSize + "\n" + QuestionAttempt + "\n" + CorrectAttempt + "\n" + WrongAttempt + "\n" + marksEarned);

                            if (AppUtility.PassingCriteria == true) {
                                text_error.setText("Congratulation");
                                text_error.setTextColor(Color.GREEN);
                            } else {
                                text_error.setText("Uh ohh");
                                text_error.setTextColor(Color.RED);
                            }

                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("Next");

                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, dialogButton);
                                    bounce_button_class.BounceMethod();
                                    AppUtility.PassingCriteria = false;
                                    AppUtility.IsFirstTimeHome = false;
                                    AppUtility.IsFirstAudio = false;

                                    Intent intent = new Intent(ExamActivityEnglishSpeaking.this, HomeActivity.class);
                                    intent.putExtra("LevelCount", AppUtility.Level);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
//                                    completeLevel();
                                    dialog.dismiss();
                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                            CancledialogButton.setText("Retry");

                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, CancledialogButton);
                                    bounce_button_class.BounceMethod();
//                                    dialog.dismiss();
                                    Intent intent = new Intent(ExamActivityEnglishSpeaking.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    AppUtility.PassingCriteria = false;
                                    finish();
                                }
                            });

                            dialog.show();


                            if (synchronizes.get(0).getResultCode().equals("1")) {
                                Snackbar.make(parentLayout, "Test Result Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            } else if (synchronizes.get(0).getResultCode().equals("2")) {
                                Snackbar.make(parentLayout, "Test Result Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            } else if (synchronizes.get(0).getResultCode().equals("0")) {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(ExamActivityEnglishSpeaking.this,"Error","Test Result Not Save SuccessFully");
//                                Snackbar.make(parentLayout, "Test Result Not Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            } else {
                                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                                showErrorPopUp.ShowPopUp(ExamActivityEnglishSpeaking.this,"Error","Test Result Not Save SuccessFully");
//                                Snackbar.make(parentLayout, "Test Result Not Save SuccessFully", Snackbar.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
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
                        final Dialog dialog = new Dialog(ExamActivityEnglishSpeaking.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.dialog1);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                        txtTitle.setText("Result of " + testName);
                        TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                        txtMessage.setText(TQuestionSize + "\n" + QuestionAttempt + "\n" + CorrectAttempt + "\n" + WrongAttempt + "\n" + marksEarned);
                        final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                        dialogButton.setText("Try Again");
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, dialogButton);
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
                                Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(ExamActivityEnglishSpeaking.this, CancledialogButton);
                                bounce_button_class.BounceMethod();
                                dialog.dismiss();
                                Intent intent = new Intent(ExamActivityEnglishSpeaking.this, HomeActivity.class);
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
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    private void calculateScreenSize() {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            (ExamActivityEnglishSpeaking.this).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            width = metrics.widthPixels;
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "calculateScreenSize", e);
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
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "loadimagebeforequestion", e);
            pc.showCatchException();
        }
    }

    private void loadimagebeforequestioninFullScreen(String ImagePath, ImageView imageView) {
        try {
            String image = AppUtility.baseUrl + ImagePath;
            int width = getScreenWidth();
            int height = getScreenHeight();
            Picasso.with(this)
                    .load(image)
                    .resize(width - 10, height - 10)
                    .into(imageView);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "loadimagebeforequestioninFullScreen", e);
            pc.showCatchException();
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void loadimageafterequestion(String ImagePath) {
        try {
            String image = AppUtility.baseUrl + ImagePath;
            Picasso.with(this)
                    .load(image)
                    .resize(width, 100)
                    .into(imageViewAfterQuestion);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "loadimageafterequestion", e);
            pc.showCatchException();
        }
    }

    private void showCurrentQuestion(int qId) {
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
                relativeLayoutBeforImage.getLayoutParams().height = 100;
                imageViewBeforeQuestion.setVisibility(View.VISIBLE);
                loadimagebeforequestion(BeforeImagePath, imageViewBeforeQuestion);
                imageViewBeforeQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivityEnglishSpeaking.this);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        loadimagebeforequestioninFullScreen(BeforeImagePath, fullScreenImage);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivityEnglishSpeaking.this);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.full_screen_image_layout, null);
                        final ImageView fullScreenImage = (ImageView) dialogLayout.findViewById(R.id.imgFullScreen);
                        loadimagebeforequestioninFullScreen(AfterImagePath, fullScreenImage);
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

                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(" " + testName);
                toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
                setSupportActionBar(toolbar);

                textQueCount.setText("Q. " + size + " / " + questionSize);
                textViewQuestionLable.setText("Question: ");
                txtQuestion.setText(questions.get(qId).getQuestionString().toString());

                //todo for html code
//            String dd="What <br> &lt ss &gt is <b> Android </b>";
//            txtQuestion.setText(Html.fromHtml(dd));

            } catch (Exception e) {
                Log.d("Exception", "+e");
            }
            radioGroup.removeAllViews();
            radioGroup.clearCheck();
            for (Object option : questions.get(qId).getOptions()) {
                if (!option.toString().isEmpty()) {
                    //todo inflate
                    RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_layout, null);
                    radioButton.setText(option.toString());

                    radioButton.setOnClickListener(this);
                    radioGroup.addView(radioButton, layoutParams);
                    if (questions.get(currentQuestion).getSelectedAnswer() != null)
                        if (radioButton.getText() == questions.get(currentQuestion).getSelectedAnswer()) {
                            radioButton.setChecked(true);
                        }
                }
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "showCurrentQuestion", e);
            pc.showCatchException();
        }
    }

    private void setCurrentAnswer(int qId, String selectedAnswer) {
        try {
            questions.get(qId).setSelectedAnswer(selectedAnswer);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "setCurrentAnswer", e);
            pc.showCatchException();
        }
    }

    private void setCurrentOption(int qId, int indexOfSelectedOption) {
        try {
            questions.get(qId).setCorrectOption(indexOfSelectedOption);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "setCurrentOption", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,ExamActivityEnglishSpeaking.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

//    @Override
//    public void onClick(View view) {
//        try {
//            RadioButton currentClicked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
//            if (currentClicked != null) {
//                int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
//                setCurrentOption(currentQuestion, index);
//                setCurrentAnswer(currentQuestion, currentClicked.getText().toString());
//            }
//            Button currentClicked1 = (Button) view;
//            switch (currentClicked1.getId()) {
//                case R.id.buttonNext:
//                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(this, btnNext);
//                    bounce_button_class1.BounceMethod();
//                    if (NegativeMarkingString.equals("0")) {
//                        if (radioGroup.getCheckedRadioButtonId() == -1) {
//                            Toast.makeText(ExamActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        btnPrev.setClickable(true);
//                        currentQuestion++;
//                        showCurrentQuestion(currentQuestion);
//
//                    } else {
//                        btnPrev.setClickable(true);
//                        currentQuestion++;
//                        showCurrentQuestion(currentQuestion);
//
//                    }
//                    break;
//                case R.id.buttonPrev:
//                    Bounce_Button_Class bounce_button_class2 = new Bounce_Button_Class(this, btnPrev);
//                    bounce_button_class2.BounceMethod();
//                    if (currentQuestion <= 0) {
//                        if (currentQuestion == 0) {
//                            Toast.makeText(ExamActivity.this, "First Question", Toast.LENGTH_SHORT).show();
//                        } else {
//                            showCurrentQuestion(0);
//                        }
//                    } else {
//                        currentQuestion--;
//                        showCurrentQuestion(currentQuestion);
//                        btnNext.setVisibility(View.VISIBLE);
//                        btnSubmit.setVisibility(View.INVISIBLE);
//                    }
//                    break;
//                case R.id.buttonSubmit:
//                    Bounce_Button_Class bounce_button_class3 = new Bounce_Button_Class(this, btnSubmit);
//                    bounce_button_class3.BounceMethod();
//                    if (NegativeMarkingString.equals("0")) {
//                        if (radioGroup.getCheckedRadioButtonId() != -1) {
//                            onSubmitTest();
//                        } else {
//                            Toast.makeText(ExamActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        onSubmitTest();
//                    }
//
//                    break;
//            }
//            //}
//        } catch (Exception e) {
//            PrintCatchException pc = new PrintCatchException(parentLayout, "ExamActivity", "onClick", e);
//            pc.showCatchException();
//        }
//    }

    @Override
    public void onClick(View view) {
        try {
            int selectedId=radioGroup.getCheckedRadioButtonId();
            RadioButton currentClicked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            if (currentClicked != null) {
                int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
                setCurrentOption(currentQuestion, index);
                setCurrentAnswer(currentQuestion, currentClicked.getText().toString());
            }


            Button currentClicked1 = (Button) view;
            switch (currentClicked1.getId()) {
                case R.id.buttonNext:
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(this, btnNext);
                    bounce_button_class1.BounceMethod();
                    if (NegativeMarkingString.equals("0")) {
                        if (radioGroup.getCheckedRadioButtonId() == -1) {
                            Toast.makeText(ExamActivityEnglishSpeaking.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        btnPrev.setClickable(true);
                        currentQuestion++;
                        if (currentQuestion == questions.size()) {
                            if (NegativeMarkingString.equals("0")) {
                                if (radioGroup.getCheckedRadioButtonId() != -1) {
                                    onSubmitTest();
                                } else {
                                    Toast.makeText(ExamActivityEnglishSpeaking.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ExamActivityEnglishSpeaking.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ExamActivityEnglishSpeaking.this, "First Question", Toast.LENGTH_SHORT).show();
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
            PrintCatchException pc = new PrintCatchException(ExamActivityEnglishSpeaking.this,parentLayout, "ExamActivity", "onClick", e);
            pc.showCatchException();
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

            int passingStage1 = calculateStage(marks);
            if (passingStage1 != 7000) {
                passingStage = calculateStage(marks);
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

        } catch (Exception e) {

        }
    }


    private float calculateMarks() {
        try {
            NegativeMarking = Float.valueOf(NegativeMarkingString);
            float totalMarks = 0;
            for (Question q : questions) {
                if (q.getCorrectAnswer().toString().equals(q.getSelectedAnswer())) {
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
            if (q.getCorrectAnswer().toString().equals(q.getSelectedAnswer())) {
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


    private int calculateStage(float marks) {
        try {

            int passingStage;
            float passingCriteria = Float.parseFloat(questions.get(0).getPassingCriteria());
            int FinalStage = Integer.parseInt(Stage);
            int IntAudioCode = (Integer.parseInt(AudioCode));
            if (FinalStage == IntAudioCode) {
                if (marks >= passingCriteria) {
                    AppUtility.PassingCriteria = true;
                    passingStage = FinalStage + 1;
                    PassOrFail = "1";
                } else {
                    passingStage = FinalStage;
                    PassOrFail = "0";
                }
            } else {
                if (marks >= passingCriteria) {
                    AppUtility.PassingCriteria = true;
                    PassOrFail = "1";
                } else {
                    PassOrFail = "0";
                }
                passingStage = FinalStage;
            }
            return passingStage;
        } catch (Exception e) {
            Snackbar.make(parentLayout, "" + e, Snackbar.LENGTH_SHORT).show();
            return 7000;
        }
    }



    public void completeLevel() {
        try {
            final Dialog dialog = new Dialog(ExamActivityEnglishSpeaking.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogforcomplete);
            TextView text_dialog1 = (TextView) dialog.findViewById(R.id.text_dialog1);
            TextView text_dialog2 = (TextView) dialog.findViewById(R.id.text_dialog2);
            final Button dialogButtonNext = (Button) dialog.findViewById(R.id.btn_dialog_next);
            final Button dialogButtonRetry = (Button) dialog.findViewById(R.id.btn_dialog_retry);
            ImageView user_profile_photo = (ImageView) dialog.findViewById(R.id.user_profile_photo);
            if (AppUtility.PassingCriteria == true) {
                text_dialog2.setText("Congratulation");
                text_dialog2.setTextColor(Color.GREEN);
                dialogButtonNext.setVisibility(View.VISIBLE);
                dialogButtonRetry.setVisibility(View.GONE);
                user_profile_photo.setImageResource(R.drawable.happy);
            } else {
                text_dialog2.setText("Uh ohh");
                text_dialog2.setTextColor(Color.RED);
                dialogButtonNext.setVisibility(View.GONE);
                dialogButtonRetry.setVisibility(View.VISIBLE);
                user_profile_photo.setImageResource(R.drawable.sad);
            }
            dialogButtonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final android.view.animation.Animation myAnim7 = android.view.animation.AnimationUtils.loadAnimation(ExamActivityEnglishSpeaking.this, R.anim.bounce);
                    MyBounceInterpolator interpolator7 = new MyBounceInterpolator(0.1, 20);
                    myAnim7.setInterpolator(interpolator7);
                    dialogButtonNext.startAnimation(myAnim7);

                    AppUtility.PassingCriteria = false;
                    AppUtility.IsFirstTimeHome = false;
                    AppUtility.IsFirstAudio = false;
                    Intent intent = new Intent(ExamActivityEnglishSpeaking.this, HomeActivity.class);
                    intent.putExtra("LevelCount", AppUtility.Level);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            dialogButtonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final android.view.animation.Animation myAnim7 = android.view.animation.AnimationUtils.loadAnimation(ExamActivityEnglishSpeaking.this, R.anim.bounce);
                    MyBounceInterpolator interpolator7 = new MyBounceInterpolator(0.1, 20);
                    myAnim7.setInterpolator(interpolator7);
                    dialogButtonRetry.startAnimation(myAnim7);
                    AppUtility.PassingCriteria = false;
                    finish();
                }
            });

            dialog.show();
        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
    }

}
