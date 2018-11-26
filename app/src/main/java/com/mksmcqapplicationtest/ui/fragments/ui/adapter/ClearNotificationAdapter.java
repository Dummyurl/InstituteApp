package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.NotificationActivityInDetails;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.util.AppUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearNotificationAdapter extends RecyclerView.Adapter<ClearNotificationAdapter.TestViewHolder> {

    private List<Test> tests;
    private Context context;
    SharedPreferences preferences;
    Typeface font1;
    List<SendNotification> datas;
    View itemView;

    public ClearNotificationAdapter(Context context, List<SendNotification> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_row_enable, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new TestViewHolder(itemView);
    }

    public void selectAll() {
        try {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setSelected(true);
                datas.get(i).setCheck("Y");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < datas.size(); i++) {
                datas.get(i).setSelected(false);
                datas.get(i).setCheck("N");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        try {
            holder.txtNotificationTitle.setText(datas.get(position).getNotificationTitle());
            holder.txtNotificationText.setText(datas.get(position).getResult());
            String SelectedDate = datas.get(position).getAcDate();
            SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat Stringformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = Dateformat.parse(SelectedDate);
                SelectedDate = Stringformat.format(date);
                holder.txtNotificationDate.setText(SelectedDate);
            } catch (ParseException e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "onBindViewHolder", e);
                pc.showCatchException();
            }
            if (holder.chkactiveuserCheck.isChecked()) {
                datas.get(position).setCheck("Y");
            } else {
                datas.get(position).setCheck("N");
            }

            if (datas.get(position).isSelected()) {
                datas.get(position).setCheck("Y");
            } else {
                datas.get(position).setCheck("N");
            }

            final int finalPostion = position;
            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT += isChecked ? 1 : -1;
                            datas.get(finalPostion).setCheck("Y");
                            datas.get(finalPostion).setSelected(buttonView.isChecked());
                            AppUtility.SELECT_ALL_IS_CHECKED = "S";
                        } else {
                            if (AppUtility.SELECT_ALL_IS_CHECKED.equals("N")) {
                                AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                                datas.get(finalPostion).setCheck("N");
                                AppUtility.SELECT_ALL_IS_CHECKED = "P";
                                datas.get(finalPostion).setSelected(false);
                            } else if (AppUtility.SELECT_ALL_IS_CHECKED.equals("S")) {
                                AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT += isChecked ? 1 : -1;
                                datas.get(finalPostion).setCheck("N");
                                datas.get(finalPostion).setSelected(false);
                            }

                        }
                    } catch (Exception ex) {
                        PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "CheckedChangeListener", ex);
                        pc.showCatchException();
                    }
                }
            });

        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
        holder.chkactiveuserCheck.setChecked(datas.get(position).isSelected());
    }

    public void setFilter(List<SendNotification> testnewlist) {
        try {
            datas = new ArrayList<>();
            datas.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CustomTextView txtNotificationTitle, txtNotificationText, txtNotificationDate;
        CheckBox chkactiveuserCheck;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                txtNotificationTitle = itemView.findViewById(R.id.txtExamTitle);
                txtNotificationText =  itemView.findViewById(R.id.txtDate);
                txtNotificationDate =  itemView.findViewById(R.id.txtAcDate);
                chkactiveuserCheck =  itemView.findViewById(R.id.chkactiveuserCheck);
                txtNotificationTitle.setVisibility(View.VISIBLE);
                txtNotificationText.setVisibility(View.VISIBLE);
                txtNotificationDate.setVisibility(View.VISIBLE);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "ClearNotificationAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                Intent newIntent = new Intent(context, NotificationActivityInDetails.class);
                newIntent.putExtra("NotificationTitle", datas.get(getPosition()).getNotificationTitle());
                newIntent.putExtra("NotificationText", datas.get(getPosition()).getResult());
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception ex) {
            }
        }
    }
}
