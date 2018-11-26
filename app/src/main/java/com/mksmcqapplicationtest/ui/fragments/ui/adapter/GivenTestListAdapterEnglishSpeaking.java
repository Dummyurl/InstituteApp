package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.mksmcqapplicationtest.EnglishSpeaking.DetailResultFragmentActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.util.AppUtility;

import java.util.ArrayList;
import java.util.List;

public class GivenTestListAdapterEnglishSpeaking extends RecyclerView.Adapter<GivenTestListAdapterEnglishSpeaking.TestViewHolder> {

    private List<GivenTestListResponse> tests;
    private Context context;
    SharedPreferences preferences;
    Typeface font1;
    String Username;
    String NegativeMark;
    View itemView;

    public GivenTestListAdapterEnglishSpeaking(Context context, List<GivenTestListResponse> tests, String username) {
        this.tests = tests;
        this.context = context;
        this.Username = username;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.given_exam_list_card_english_speaking, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        try {
            String FontAswome = context.getResources().getString(R.string.fa_timer);
            GivenTestListResponse test = tests.get(position);
            holder.txtTitle.setText(test.getTestName());
            //  String name = (String) holder.txtTitle.getText();
//            String className = test.getClassName();
//            if ((className != null) || !(className.equals("null"))) {
//                holder.txtClassName.setText("Class : " + test.getClassName());
//            } else {
//                holder.txtClassName.setVisibility(View.GONE);
//            }

            NegativeMark = test.getNegativeMarks().toString();
            if (NegativeMark.equals("") || NegativeMark.equals("0")) {
                holder.txtTestNegativeMark.setVisibility(View.GONE);
            } else {
                float negativeMark = Float.parseFloat(NegativeMark);
                float marks = negativeMark * 100;
                int per = (int) marks;
                String percentage = String.valueOf(per);
                holder.txtTestNegativeMark.setText("Negative Marking:- " + percentage + "%");
            }

            holder.txtAttempt.setText("Attempt : " + test.getAttempt());
            holder.txtMarks.setText("Your Score : " + test.getScore());
            String TimeAndDate = test.getSubmittedTestTime();
            if ((TimeAndDate != null) || !(TimeAndDate.equals("")) || !(TimeAndDate.equals("null"))) {
                String[] items1 = TimeAndDate.split(" ");
                holder.txtSubmitTestDate.setText("Date : " + items1[0]);
                holder.txtSubmitTestTime.setText("Time : " + items1[1]);
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle, txtAttempt, txtTestTime, txtSubmitTestDate, txtClassName, txtMarks, txtSubmitTestTime, txtTestNegativeMark;
        ImageButton examlist;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                txtTitle = (TextView) itemView.findViewById(R.id.examTitle);
                txtAttempt = (TextView) itemView.findViewById(R.id.textViewTestAttempt);

                txtSubmitTestDate = (TextView) itemView.findViewById(R.id.examSubmitDate);
                txtMarks = (TextView) itemView.findViewById(R.id.textViewTestMarks);
                examlist = (ImageButton) itemView.findViewById(R.id.exam_list);
                txtClassName = (TextView) itemView.findViewById(R.id.txtClassName);
                txtClassName.setVisibility(View.GONE);
                txtSubmitTestTime = (TextView) itemView.findViewById(R.id.examSubmitTime);
                txtTestNegativeMark = (TextView) itemView.findViewById(R.id.txtTestNegativeMark);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            try {
                Intent intentDetailResult = new Intent(context, DetailResultFragmentActivity.class);
                intentDetailResult.putExtra("data", AppUtility.GSON.toJson(tests.get(getPosition())));
                intentDetailResult.putExtra("UserName", Username);
                intentDetailResult.putExtra("NegativeMarking", tests.get(getPosition()).getNegativeMarks());
                context.startActivity(intentDetailResult);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "onClick", e);
                pc.showCatchException();
            }
        }
    }

    public void setFilter(List<GivenTestListResponse> testnewlist) {
        try {
            tests = new ArrayList<>();
            tests.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

}