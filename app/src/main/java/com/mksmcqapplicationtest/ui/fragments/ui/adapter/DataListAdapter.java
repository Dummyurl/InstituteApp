package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.ui.fragments.DataFragmentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.TestViewHolder> {

    private List<Test> tests;
    private Context context;
    SharedPreferences preferences;
    Typeface font1;
    List<Data> datas;
    View itemView;

    public DataListAdapter(Context context, List<Data> datas) {
        this.context = context;
        this.datas = datas;
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
            holder.txtTitle.setText(datas.get(position).getDataName());
            if (datas.get(position).getDataText().equals("Y")) {
                holder.txtTextData.setVisibility(View.VISIBLE);
                holder.txtTextData.setTypeface(font1);

            } else {
                holder.txtTextData.setVisibility(View.GONE);
            }
            if (datas.get(position).getImagePath().equals("Y")) {
                holder.txtImageData.setVisibility(View.VISIBLE);
                holder.txtImageData.setTypeface(font1);
            } else {
                holder.txtImageData.setVisibility(View.GONE);
            }
            if (datas.get(position).getVideoPath().equals("Y")) {
                holder.txtVideoData.setVisibility(View.VISIBLE);
                holder.txtVideoData.setTypeface(font1);
            } else {
                holder.txtVideoData.setVisibility(View.GONE);
            }
            if (datas.get(position).getPDFPath().equals("Y")) {
                holder.txtPDFData.setVisibility(View.VISIBLE);
                holder.txtPDFData.setTypeface(font1);
            } else {
                holder.txtPDFData.setVisibility(View.GONE);
            }
            String DataCreationDate = datas.get(position).getAcDate();
            SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat Stringformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = Dateformat.parse(DataCreationDate);
                String datetime = Stringformat.format(date);
                holder.txtDate.setText("Date:- " + datetime);
            } catch (ParseException e) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "DataListAdapter", "onBindViewHolder", e);
                pc.showCatchException();
            }
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "DataListAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    public void setFilter(List<Data> testnewlist) {
        try {
            datas = new ArrayList<>();
            datas.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "DataListAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txtTitle, txtDate, txtTestTotalMarks, txtTestNegativeMark, txtScore, txtTestTime, txtAttempt, txtTextData, txtImageData, txtVideoData, txtPDFData;
        CustomEditText etdEnterMarks;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                txtTitle = itemView.findViewById(R.id.txtExamTitle);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtTestTotalMarks = itemView.findViewById(R.id.txtTestTotalMarks);
                txtTestNegativeMark = itemView.findViewById(R.id.txtTestNegativeMark);
                txtScore = itemView.findViewById(R.id.txtScore);
                txtTestTime = itemView.findViewById(R.id.txtTestTime);
                txtAttempt = itemView.findViewById(R.id.txtAttempt);
                txtTextData = itemView.findViewById(R.id.txtTextData);
                txtImageData = itemView.findViewById(R.id.txtImageData);
                txtVideoData = itemView.findViewById(R.id.txtVideoData);
                txtPDFData = itemView.findViewById(R.id.txtPDFData);
                etdEnterMarks = itemView.findViewById(R.id.etdEnterMarks);


                txtTitle.setVisibility(View.VISIBLE);
                txtTestTotalMarks.setVisibility(View.GONE);
                txtTestNegativeMark.setVisibility(View.GONE);
                txtTestTime.setVisibility(View.GONE);
                txtDate.setVisibility(View.VISIBLE);
                txtScore.setVisibility(View.GONE);
                txtAttempt.setVisibility(View.GONE);
                txtTextData.setVisibility(View.VISIBLE);
                txtImageData.setVisibility(View.VISIBLE);
                txtVideoData.setVisibility(View.VISIBLE);
                txtPDFData.setVisibility(View.VISIBLE);
                etdEnterMarks.setVisibility(View.GONE);


                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "DataListAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                Intent newIntent = new Intent(context, DataFragmentActivity.class);
                newIntent.putExtra("DataName", datas.get(getPosition()).getDataName());
                newIntent.putExtra("DataCode", datas.get(getPosition()).getDataCode());
                newIntent.putExtra("UploadAnswerSheet", "N");
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception ex) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "DataListAdapter", "onClick", ex);
                pc.showCatchException();
            }
        }
    }
}
