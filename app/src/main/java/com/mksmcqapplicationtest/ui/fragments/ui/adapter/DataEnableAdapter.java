package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.Data;
import com.mksmcqapplicationtest.ui.fragments.DataFragmentActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DataEnableAdapter extends RecyclerView.Adapter<DataEnableAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Data> datas;
    Typeface font1;
    View itemView;

    public DataEnableAdapter(Context context, RecyclerView recyclerView, List<Data> datas) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_row_enable, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtNameOfUser.setText(datas.get(position).getDataName().toString());
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
                holder.txtAcDate.setText("Date:- " + datetime);
            } catch (ParseException e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "onBindViewHolder", e);
                pc.showCatchException();
            }
            String ActiveStatus = datas.get(position).getAcFlag().toString();

            if (ActiveStatus.equals("Y")) {
                holder.chkactiveuserCheck.setChecked(true);
                datas.get(position).setAcFlag("Y");
            } else {
                holder.chkactiveuserCheck.setChecked(false);
                datas.get(position).setAcFlag("N");
            }
            final int finalPostion = position;
            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        datas.get(finalPostion).setAcFlag("Y");
                    } else {
                        datas.get(finalPostion).setAcFlag("N");
                    }
                }
            });
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    public void setFilter(List<Data> testnewlist) {
        try {
            datas = new ArrayList<>();
            datas.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNameOfUser, txtAcDate;
        TextView txtTextData, txtImageData, txtVideoData, txtPDFData;
        CheckBox chkactiveuserCheck;


        public MyViewHolder(View itemView) {
            super(itemView);
            try {

                txtNameOfUser = itemView.findViewById(R.id.txtExamTitle);
                chkactiveuserCheck =  itemView.findViewById(R.id.chkactiveuserCheck);
                txtTextData =  itemView.findViewById(R.id.txtTextData);
                txtImageData = itemView.findViewById(R.id.txtImageData);
                txtVideoData = itemView.findViewById(R.id.txtVideoData);
                txtPDFData =  itemView.findViewById(R.id.txtPDFData);
                txtAcDate =  itemView.findViewById(R.id.txtDate);
                txtNameOfUser.setVisibility(View.VISIBLE);
                txtTextData.setVisibility(View.VISIBLE);
                txtImageData.setVisibility(View.VISIBLE);
                txtVideoData.setVisibility(View.VISIBLE);
                txtPDFData.setVisibility(View.VISIBLE);
                txtAcDate.setVisibility(View.VISIBLE);

                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "MyViewHolder", e);
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
                newIntent.putExtra("UploadAnswerSheet", "Y");
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception ex) {
            }
        }
    }

    public void selectAll() {
        try {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setAcFlag("Y");
                datas.get(i).setChecked("Y");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setAcFlag("N");
                datas.get(i).setChecked("N");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }


}

