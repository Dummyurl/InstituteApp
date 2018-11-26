package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.AboutUs;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.Communication.TabCommunicationActivity;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.SendQuery;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MessageAdapterForTeacher extends RecyclerView.Adapter<MessageAdapterForTeacher.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<SendQuery> sendQueries;
    String message, SendQueryJSONString, queryCode;
    View itemView;
    Typeface font;

    public MessageAdapterForTeacher(Context context, RecyclerView recyclerView, List<SendQuery> sendQueries) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.sendQueries = sendQueries;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_of_message_guest, parent, false);
        context = parent.getContext();
        font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtTitle.setText(sendQueries.get(position).getQueryTitle().toString());
            holder.txtMessage.setText(sendQueries.get(position).getQueryMessage().toString().trim());

            String DataCreationDate = sendQueries.get(position).getAcDate();
            SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat Stringformat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = Dateformat.parse(DataCreationDate);
                String datetime = Stringformat.format(date);
                holder.txtAcDate.setText("" + datetime);
            } catch (ParseException e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForTeacher", "onBindViewHolder", e);
                pc.showCatchException();
            }
            holder.txtSeeComment.setTypeface(font);
            if (sendQueries.get(position).getTeacherReplay().equals("True")) {
                holder.txtSeeComment.setTextColor(Color.GREEN);
                holder.txtSeeComment.setText(R.string.comment);
            } else {
                holder.txtSeeComment.setTextColor(Color.RED);
                holder.txtSeeComment.setText(R.string.comment);
            }
            holder.txtSeeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sendQueries.get(position).getTeacherReplay().equals("True")) {
                        if (holder.txtSeeDetailComment.getVisibility() == View.VISIBLE) {
                            holder.txtSeeDetailComment.setVisibility(View.GONE);
                            holder.txtStudentName.setVisibility(View.GONE);
                            holder.txtClassName.setVisibility(View.GONE);
                        } else {
                            holder.txtSeeDetailComment.setVisibility(View.VISIBLE);
                            holder.txtStudentName.setVisibility(View.VISIBLE);
                            holder.txtClassName.setVisibility(View.VISIBLE);
                            holder.txtSeeDetailComment.setText(sendQueries.get(position).getTeacherReplayMessage().toString());
                            holder.txtStudentName.setText(sendQueries.get(position).getStudentName().toString());
                            holder.txtClassName.setText(sendQueries.get(position).getClassName().toString());
                        }
                    } else {
                        holder.txtSeeDetailComment.setVisibility(View.GONE);
                        holder.txtStudentName.setVisibility(View.GONE);
                        holder.txtClassName.setVisibility(View.GONE);
                        DialogForReply();
                        queryCode = sendQueries.get(position).getQueryCode().toString();
                    }

                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForTeacher", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }

    private void DialogForReply() {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.sendmsg_dialog);
            final EditText txtTitle = (EditText) dialog.findViewById(R.id.txttitleOfMessage);
            final EditText txtMessage = (EditText) dialog.findViewById(R.id.txtcontentOfMessage);
            txtTitle.setVisibility(View.GONE);
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(context, dialogButton);
                    bounce_button_class.BounceMethod();
                    message = txtMessage.getText().toString().trim();
                    boolean result = CheckValidate();
                    if (result) {
                        dialog.dismiss();
                    } else {
                        txtMessage.setError("Enter Reply Here");
                    }
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(context, CancledialogButton);
                    bounce_button_class.BounceMethod();
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForTeacher", "DialogForReply", e);
            pc.showCatchException();
        }
    }



    private boolean CheckValidate() {
        if (message.equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return sendQueries == null ? 0 : sendQueries.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtMessage, txtAcDate, txtSeeComment, txtSeeDetailComment, txtStudentName, txtClassName;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitleOfMessage);
                txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
                txtSeeComment = (TextView) itemView.findViewById(R.id.txtseeComment);
                txtAcDate = (TextView) itemView.findViewById(R.id.txtMessageDate);
                txtSeeDetailComment = (TextView) itemView.findViewById(R.id.txtseeDetailreply);
                txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
                txtClassName = (TextView) itemView.findViewById(R.id.txtCLassName);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForTeacher", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }


}

