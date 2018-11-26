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

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.GivenTestListResponse;
import com.mksmcqapplicationtest.ui.fragments.DetailResultFragmentActivity;
import com.mksmcqapplicationtest.util.AppUtility;

import java.util.ArrayList;
import java.util.List;

public class GivenTestListAdapter extends RecyclerView.Adapter<GivenTestListAdapter.TestViewHolder> {

    private List<GivenTestListResponse> tests;
    private Context context;
    SharedPreferences preferences;
    Typeface font1;
    String Username;
    String NegativeMark;
    View itemView;

    public GivenTestListAdapter(Context context, List<GivenTestListResponse> tests, String username) {
        this.tests = tests;
        this.context = context;
        this.Username = username;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_row, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        try {
            String FontAswome = context.getResources().getString(R.string.fa_timer);
            GivenTestListResponse test = tests.get(position);
            holder.txtTitle.setText(test.getTestName());
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
            holder.txtScore.setText("Your Score : " + test.getScore());
            String TimeAndDate = test.getSubmittedTestTime();
            if ((TimeAndDate != null) || !(TimeAndDate.equals("")) || !(TimeAndDate.equals("null"))) {
                String[] items1 = TimeAndDate.split(" ");
                holder.txtDate.setText("Date : " + items1[0]);
                holder.txtTestTime.setText("Time : " + items1[1]);
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle,txtDate,txtTestTotalMarks,txtTestNegativeMark,txtScore,txtTestTime,txtAttempt
                ,txtTextData,txtImageData,txtVideoData,txtPDFData;
        CustomEditText etdEnterMarks;
        public TestViewHolder(View itemView) {
            super(itemView);
            try {


                txtTitle =  itemView.findViewById(R.id.txtExamTitle);
                txtDate =  itemView.findViewById(R.id.txtDate);
                txtTestTotalMarks =  itemView.findViewById(R.id.txtTestTotalMarks);
                txtTestNegativeMark = itemView.findViewById(R.id.txtTestNegativeMark);
                txtScore = itemView.findViewById(R.id.txtScore);
                txtTestTime = itemView.findViewById(R.id.txtTestTime);
                txtAttempt = itemView.findViewById(R.id.txtAttempt);
                txtTextData =itemView.findViewById(R.id.txtTextData);
                txtImageData = itemView.findViewById(R.id.txtImageData);
                txtVideoData = itemView.findViewById(R.id.txtVideoData);
                txtPDFData = itemView.findViewById(R.id.txtPDFData);
                etdEnterMarks=itemView.findViewById(R.id.etdEnterMarks);


                txtTitle.setVisibility(View.VISIBLE);
                txtTestTotalMarks.setVisibility(View.VISIBLE);
                txtTestNegativeMark.setVisibility(View.VISIBLE);
                txtTestTime.setVisibility(View.VISIBLE);
                txtDate.setVisibility(View.VISIBLE);
                txtScore.setVisibility(View.VISIBLE);
                txtAttempt.setVisibility(View.VISIBLE);
                txtTextData.setVisibility(View.GONE);
                txtImageData.setVisibility(View.GONE);
                txtVideoData.setVisibility(View.GONE);
                txtPDFData.setVisibility(View.GONE);
                etdEnterMarks.setVisibility(View.GONE);

                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "OnCreate", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                Intent intentDetailResult = new Intent(context, DetailResultFragmentActivity.class);
                intentDetailResult.putExtra("data", AppUtility.GSON.toJson(tests.get(getPosition())));
                intentDetailResult.putExtra("UserName", Username);
                intentDetailResult.putExtra("NegativeMarking", tests.get(getPosition()).getNegativeMarks());
                context.startActivity(intentDetailResult);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "GivenTestListAdapter", "OnClick", e);
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
