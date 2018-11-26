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

import com.mksmcqapplicationtest.NotificationActivityInDetails;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.SendNotification;
import com.mksmcqapplicationtest.beans.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.TestViewHolder> {

    private List<Test> tests;
    private Context context;
    SharedPreferences preferences;
    Typeface font1;
    List<SendNotification> datas;
    View itemView;

    public NotificationListAdapter(Context context, List<SendNotification> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new TestViewHolder(itemView);
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
                PrintCatchException pc = new PrintCatchException(context,itemView, "NotificationListAdapter", "onBindViewHolder", e);
                pc.showCatchException();
            }

        } catch (Exception ex) {
        }
    }

    public void setFilter(List<SendNotification> testnewlist) {
        try {
            datas = new ArrayList<>();
            datas.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "NotificationListAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNotificationTitle, txtNotificationText, txtNotificationDate;


        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                txtNotificationTitle = (TextView) itemView.findViewById(R.id.NotificationTitle);
                txtNotificationText = (TextView) itemView.findViewById(R.id.NotificationText);
                txtNotificationDate = (TextView) itemView.findViewById(R.id.Notificationdate);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "NotificationListAdapter", "TestViewHolder", e);
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
