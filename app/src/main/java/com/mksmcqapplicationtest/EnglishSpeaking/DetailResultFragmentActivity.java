package com.mksmcqapplicationtest.EnglishSpeaking;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;

import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.beans.Question;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.QuestionwiseResultAdapterEnglishSpeaking;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


public class DetailResultFragmentActivity extends AppCompatActivity implements ResponseListener {
    private List<Question> questions;
    private RecyclerView recyclerView;
    String testname, submitTime, TestCode, JSONArrayString, NegativeMarking;
    String testDate, testTime;
    View parentLayout;
    File folder;
    String date;
    String UserName;
    Float NegativeMark;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_result_fragment);
        try {
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            Bundle bundle = getIntent().getExtras();
            GivenTestListResponse test = AppUtility.GSON.fromJson(bundle.getString("data"), GivenTestListResponse.class);
            UserName = bundle.getString("UserName");
            NegativeMarking = bundle.getString("NegativeMarking");
            testname = test.getTestName();
            TestCode = test.getTestCode();
            submitTime = test.getTime();
            date = test.getTime();

            if (!(NegativeMarking.equals("") || NegativeMarking == null)) {
                NegativeMark = Float.parseFloat(NegativeMarking);
            }



            parentLayout = findViewById(android.R.id.content);
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(DetailResultFragmentActivity.this);
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                displayQuestionList();
            }

            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(" " + testname);
            toolbar.setLogo(R.mipmap.ic_launcher_logo_c);
            setSupportActionBar(toolbar);

        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","onCreate",e);
            pc.showCatchException();
        }
    }

    private void displayQuestionList() {
//        Bundle bundle = getIntent().getExtras();
//        GivenTestListResponse test = AppUtility.GSON.fromJson(bundle.getString("data"), GivenTestListResponse.class);
//        testname = test.getTestName();
//        getSupportActionBar().setTitle(testname);
//        TestCode = test.getTestCode();
        try {
            submitTime = submitTime.replaceAll("/", "_");
            submitTime = submitTime.replaceAll(",", "_");
            submitTime = submitTime.replaceAll(":", "_");
            submitTime = submitTime.replaceAll(" ", "_");
            submitTime = submitTime.substring(0, submitTime.length() - 3);//TODO test

            String[] parts = date.split(" ");
            testDate = parts[0];
            testDate = testDate.replaceAll(",", "");
            testTime = parts[1] + " " + parts[2];
            NetWorkCallForDetailOfGivenTestList();
        } catch (NullPointerException e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","displayQuestionList",e);
            pc.showCatchException();
        }

    }

    private void NetWorkCallForDetailOfGivenTestList() {
        try {
            CreateJSONForGetQuestionOfGivenTestList();
            //todo here will update
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","NetWorkCallForDetailOfGivenTestList",e);
            pc.showCatchException();
        }
    }


    private void CreateJSONForGetQuestionOfGivenTestList() {
        try {
            Gson gson = new Gson();
            GivenTestListResponse givenTestListResponse = new GivenTestListResponse();
            givenTestListResponse.setTestCode(TestCode);
            givenTestListResponse.setTime(date);
            givenTestListResponse.setUserName(UserName);
            String JsonString = gson.toJson(givenTestListResponse);
            JSONArrayString = "[" + JsonString + "]";
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","CreateJSONForGetQuestionOfGivenTestList",e);
            pc.showCatchException();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.pdf_menu, menu);
//        return true;
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdf_menu:

                createPDF();
                break;
//                return true;
            case android.R.id.home:
                onBackPressed();
                this.finish();
                break;
//                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void createPDF() {
        permision();
        createFolder();
        Document doc = new Document();

        String outPath = "/" + folder + "/" + testname + "_" + submitTime + ".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outPath));
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            doc.open();
            for (int i = 0; i <= questions.size() - 1; i++) {
                doc.add(new Paragraph());
                if (!questions.get(i).getBeforImagePath().equals("")) {
                    String beforeQuestion = AppUtility.baseUrl + questions.get(i).getBeforImagePath();
                    doc.add(new Paragraph(beforeQuestion));
                }
                Font font1 = new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLD);
                Chunk glue = new Chunk(new VerticalPositionMark());

                String que = (i + 1) + ". " + "Question: " + questions.get(i).getQuestionString();
                Paragraph p1 = new Paragraph(que);
                p1.add(new Chunk(glue));
                p1.add("Marks:" + String.valueOf(questions.get(i).getMarks()));
                doc.add(p1);
                if (!questions.get(i).getAfterImagePath().equals("")) {
                    String afterQuestion = AppUtility.baseUrl + questions.get(i).getAfterImagePath();
                    doc.add(new Paragraph(afterQuestion));
                }
                final String[] separate = new String[questions.get(i).getOptions().size()];
                try {
                    for (int m = 0; m < questions.get(i).getOptions().size(); m++) {
                        separate[m] = questions.get(i).getOptions().get(m).toString();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                for (int j = 0; j < separate.length; j++) {
                    if (!separate[j].trim().isEmpty()) {
                        RadioButton radioButton = new RadioButton(this);
                        if (j == 0) {
                            radioButton.setText("A" + ". " + separate[j].toString());
                        } else if (j == 1) {
                            radioButton.setText("B" + ". " + separate[j].toString());
                        } else if (j == 2) {
                            radioButton.setText("C" + ". " + separate[j].toString());
                        } else if (j == 3) {
                            radioButton.setText("D" + ". " + separate[j].toString());
                        } else if (j == 4) {
                            radioButton.setText("E" + ". " + separate[j].toString());
                        } else if (j == 5) {
                            radioButton.setText("F" + ". " + separate[j].toString());
                        }
                        RadioGroup radioGroup = new RadioGroup(this);
                        radioGroup.addView(radioButton);
                        doc.add(new Paragraph(radioButton.getText().toString()));
                    }
                }
                for (int p = 0; p < separate.length; p++) {
                    String CorrectAns = questions.get(i).getCorrectAnswer().toString().trim();
                    String SepaterdAns = separate[p].toString().trim();
                    if (CorrectAns.equals(SepaterdAns)) {
                        if (p == 0) {
                            Chunk que0 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que0));

                        } else if (p == 1) {
                            Chunk que1 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que1));
                        } else if (p == 2) {
                            Chunk que2 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que2));
                        } else if (p == 3) {
                            Chunk que3 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que3));
                        } else if (p == 4) {
                            Chunk que4 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que4));
                        } else if (p == 5) {
                            Chunk que5 = new Chunk("Correct Answer: " + questions.get(i).getCorrectAnswer().toString(), font1);
                            doc.add(new Paragraph(que5));
                        }
                    }
                }
                String selected = null;
                String SelectedOption = questions.get(i).getSelectedAnswer();
                if (SelectedOption.equals("0")) {
                    selected = separate[0].toString();
                } else if (SelectedOption.equals("1")) {
                    selected = separate[1].toString();
                } else if (SelectedOption.equals("2")) {
                    selected = separate[2].toString();
                } else if (SelectedOption.equals("3")) {
                    selected = separate[3].toString();
                } else if (SelectedOption.equals("4")) {
                    selected = separate[4].toString();
                } else if (SelectedOption.equals("5")) {
                    selected = separate[5].toString();
                }

                for (int q = 0; q < separate.length; q++) {
                    //if (questions.get(i).getSelectedAnswer() != null) {

                    if (selected != null) {
                        if (selected.equals(separate[q].toString().trim())) {
                            if (q == 0) {
                                Chunk que11 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que11));
                            } else if (q == 1) {
                                Chunk que21 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que21));
                            } else if (q == 2) {
                                Chunk que31 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que31));
                            } else if (q == 3) {
                                Chunk que41 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que41));
                            } else if (q == 4) {
                                Chunk que51 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que51));
                            } else if (q == 5) {
                                Chunk que61 = new Chunk("Selected Answer: " + selected, font1);
                                doc.add(new Paragraph(que61));
                            }
                            //  }
                        }
                    }
                }
                Chunk ans = new Chunk("Description: " + questions.get(i).getAnswerDescription(), font1);
                doc.add(new Paragraph(ans));
                LineSeparator ls = new LineSeparator();
                doc.add(new Chunk(ls));
                doc.getPageNumber();
                doc.bottomMargin();
            }

            //Creating Paragraph

            doc.close();

            final Dialog dialog = new Dialog(DetailResultFragmentActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Sucessful!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("PDF Store in " + folder + " Folder");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("Ok");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(DetailResultFragmentActivity.this, dialogButton);
                    bounce_button_class1.BounceMethod();
                    dialog.dismiss();

                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("Open in Folder");
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(DetailResultFragmentActivity.this, CancledialogButton);
                        bounce_button_class1.BounceMethod();
                        Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                        Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "MCQExam");
                        intent.putExtra("CONTENT_TYPE", "*/*");
                        startActivity(Intent.createChooser(intent, "Open folder"));
                    } catch (ExceptionConverter ex) {

                    }

                }
            });

            dialog.show();

        } catch (FileNotFoundException e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","CreatePDF",e);
            pc.showCatchException();
        } catch (DocumentException e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","CreatePDF",e);
            pc.showCatchException();
        }
    }

    private void permision() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
            } else {
                requestPermission();
            }
        } else {
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(DetailResultFragmentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        try {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DetailResultFragmentActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(parentLayout, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Snackbar.LENGTH_INDEFINITE).show();
            } else {
                ActivityCompat.requestPermissions(DetailResultFragmentActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","requestPermission",e);
            pc.showCatchException();
        }
    }


    private void createFolder() {
        try {
            if (AppUtility.ROOT_FOLDERPATH.equals("")) {
                folder = new File(Environment.getExternalStorageDirectory() + "/" + AppUtility.StorePDFPath);
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                if (success) {

                } else {

                    Snackbar.make(parentLayout, "Folder Not Created", Snackbar.LENGTH_LONG).show();

                }
            } else {
                folder = new File(AppUtility.ROOT_FOLDERPATH);
            }
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","createFolder",e);
            pc.showCatchException();

        }
    }


    @Override
    public void onResponse(Object data) {
        try {
            questions = (List<Question>) data;
            if (questions.size() > 0) {
                QuestionwiseResultAdapterEnglishSpeaking adapter = new QuestionwiseResultAdapterEnglishSpeaking(questions, NegativeMark);
                recyclerView.setAdapter(adapter);
            } else {
//                Snackbar.make(parentLayout, "No Questions For this ", Snackbar.LENGTH_LONG).show();
                ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                showErrorPopUp.ShowPopUp(DetailResultFragmentActivity.this,"Error","No Questions For this");
            }

        } catch (Exception ex) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","onResponse",ex);
            pc.showCatchException();
//            Snackbar.make(parentLayout, "Something went wrong", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {

    }

    @Override
    public void noResponse(String error) {
        try {
            VollyResponse vollyResponse = new VollyResponse(parentLayout,DetailResultFragmentActivity.this,getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","noResponse",e);
            pc.showCatchException();
        }
    }


    public class HeaderFooterPageEvent extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 25, Font.BOLDITALIC);

        public void onStartPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte cb = writer.getDirectContent();
                Phrase header = new Phrase(testname, ffont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header, (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
            } catch (Exception e) {
                PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","onStartPage",e);
                pc.showCatchException();
            }
        }

        public void onEndPage(PdfWriter writer, Document document) {
            try {
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
            } catch (Exception e) {
                PrintCatchException pc=new PrintCatchException(DetailResultFragmentActivity.this,parentLayout,"DetailResultFragmentActivity","onEndPage",e);
                pc.showCatchException();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppUtility.IsFirstTimeHome=false;
        AppUtility.IsFirstAudio=false;
    }
}
