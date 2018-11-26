package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.mksmcqapplicationtest.EnglishSpeaking.ExamActivityEnglishSpeaking;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.Question;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestListAdapterEnglishSpeaking extends RecyclerView.Adapter<TestListAdapterEnglishSpeaking.TestViewHolder>  {

    private List<Test> tests;
    private List<Question> questions;
    private Context context;
    Typeface font1;
    File folder;
    String testname, testtime;
    View itemView;
    String Stage, AudioCode;

    public TestListAdapterEnglishSpeaking (Context context, List<Test> tests, String Stage, String AudioCode) {
        this.tests = tests;
        this.context = context;
        this.Stage = Stage;
        this.AudioCode = AudioCode;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_list_card_english_speaking, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, final int position) {
        try {
            String FontAswome = context.getResources().getString(R.string.fa_timer);
            final Test test = tests.get(position);
            holder.txtTitle.setText(test.getTestName());
            holder.txtTestTime.setText(FontAswome + " " + test.getTestTime());
            holder.txtTestTime.setTypeface(font1);
            holder.txtTotalMarks.setText("Total Marks: " + test.getTestTotalMarks());
            String passingCriteria = test.getPassingCriteria();
            if (passingCriteria != null) {
                holder.txtPassingCriteria.setText("Passing Criteria: " + test.getPassingCriteria());
            } else {
                holder.txtPassingCriteria.setVisibility(View.GONE);
            }

            String NegativeMark = test.getNegativeMarks().toString();
            if (NegativeMark.equals("") || NegativeMark.equals("0")) {
                holder.txtTestNegativeMark.setVisibility(View.GONE);
            } else {
                float negativeMark = Float.parseFloat(NegativeMark);
                float marks = negativeMark * 100;
                int per = (int) marks;
                String percentage = String.valueOf(per);
                holder.txtTestNegativeMark.setText("Negative Marking: " + percentage + "%");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void OpenDialog(final int position) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Confirmation!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Are you sure? You want to start MCQ test");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setText("Yes");
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(context, dialogButton);
                    bounce_button_class1.BounceMethod();
                    dialog.dismiss();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setText("No");
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class1 = new Bounce_Button_Class(context, CancledialogButton);
                    bounce_button_class1.BounceMethod();
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "OpenDialog", e);
            pc.showCatchException();
        }
    }


    public void setFilter(List<Test> testnewlist) {
        try {
            tests = new ArrayList<>();
            tests.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }


        @Override
    public int getItemCount() {
        return tests.size();
    }



    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle, txtTestTime, txtTotalMarks, txtTestNegativeMark, txtPassingCriteria;
        ImageButton examlist;
        LinearLayout linearLayout, linearLayoutOfMarksAndClassName;
        RelativeLayout relativeLayout;

        public TestViewHolder(View itemView1) {
            super(itemView1);
            try {
                txtTitle = (TextView) itemView1.findViewById(R.id.examTitle);
                txtTestTime = (TextView) itemView1.findViewById(R.id.examTime);
                examlist = (ImageButton) itemView1.findViewById(R.id.exam_list);
                txtTotalMarks = (TextView) itemView1.findViewById(R.id.txtTestTotalMarks);
                linearLayout = (LinearLayout) itemView1.findViewById(R.id.Linear);
                relativeLayout = (RelativeLayout) itemView1.findViewById(R.id.circle);
                linearLayoutOfMarksAndClassName = (LinearLayout) itemView1.findViewById(R.id.layoutofmarksandclassname);
                txtTestNegativeMark = (TextView) itemView1.findViewById(R.id.txtTestNegativeMark);
                txtPassingCriteria = (TextView) itemView1.findViewById(R.id.txtPassingCriteria);
                itemView1.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            OpenDialog(getPosition());
        }
    }


    private void createPDF() {              //TODO: Tejal PDF is created
        createFolder();
        Document doc = new Document();
        String outPath = "/" + folder + "/" + testname + "_" + testtime + ".pdf";
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

                String que = (i + 1) + ". " + "Question:-" + questions.get(i).getQuestionString();
                Paragraph p1 = new Paragraph(que);
                p1.add(new Chunk(glue));
                p1.add("Marks:" + String.valueOf(questions.get(i).getMarks()));
                doc.add(p1);
                if (!questions.get(i).getAfterImagePath().equals("")) {
                    String afterQuestion = AppUtility.baseUrl + questions.get(i).getAfterImagePath();
                    doc.add(new Paragraph(afterQuestion));
                }
                String option = questions.get(i).getOptions().toString();
                String options = option.substring(1, option.length() - 1);
                String[] separate = options.split(",");
                for (int j = 0; j < separate.length; j++) {
                    if (!separate[j].trim().isEmpty()) {
                        RadioButton radioButton = new RadioButton(context);
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
                        RadioGroup radioGroup = new RadioGroup(context);
                        radioGroup.addView(radioButton);
                        doc.add(new Paragraph(radioButton.getText().toString()));
                    }
                }

                LineSeparator ls = new LineSeparator();
                doc.add(new Chunk(ls));
                doc.getPageNumber();
                doc.bottomMargin();
            }

            doc.close();
            new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_input_get).setTitle("Message")
                    .setMessage("PDF Store in " + folder + " Folder")
                    .setIcon(R.mipmap.ic_launcher_logo_c)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Open in Folder", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String str = android.os.Build.MANUFACTURER + android.os.Build.MODEL;
                    if (str.contains("samsung")) {
                        try {
                            Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                            Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "MCQExam");
                            intent.putExtra("CONTENT_TYPE", "*/*");
                            context.startActivity(Intent.createChooser(intent, "Open folder"));
                        } catch (Exception ex) {
                            Log.d("Exception ", "" + ex);
                        }
                    } else {
                        try {
                            Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + File.separator + "MCQExam");
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(selectedUri, "*/*");
                        } catch (Exception ex) {
                            Log.d("Exception1", "" + ex);
                        }
                    }

                }
            }).show();

        } catch (FileNotFoundException e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "createPDF", e);
            pc.showCatchException();
        } catch (DocumentException e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "createPDF", e);
            pc.showCatchException();
        }
    }

    private void createFolder() {
        try {
            if (AppUtility.ROOT_FOLDERPATH.equals("")) {
                folder = new File(Environment.getExternalStorageDirectory() + File.separator + "MCQExam/MCQExamQuestionPaper");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {

                } else {

                    Snackbar.make(itemView, "Folder Not Created", Snackbar.LENGTH_LONG).show();
                }
            } else {
                folder = new File(AppUtility.ROOT_FOLDERPATH);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "createFolder", e);
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
                PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "onStartPage", e);
                pc.showCatchException();
            }
        }

        public void onEndPage(PdfWriter writer, Document document) {
            try {
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "TestListAdapter", "onEndPage", e);
                pc.showCatchException();
            }
        }

    }

}
