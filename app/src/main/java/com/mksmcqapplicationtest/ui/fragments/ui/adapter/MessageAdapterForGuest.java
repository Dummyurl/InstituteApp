package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.SendQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MessageAdapterForGuest extends RecyclerView.Adapter<MessageAdapterForGuest.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<SendQuery> sendQueries;
    Typeface font;
    View itemView;

    public MessageAdapterForGuest(Context context, RecyclerView recyclerView, List<SendQuery> sendQueries) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.sendQueries = sendQueries;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_of_message_teacher, parent, false);
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
                PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForGuest", "onBindViewHolder", e);
                pc.showCatchException();
            }
            holder.txtSeeComment.setTypeface(font);
            holder.txtSeeComment.setText(R.string.comment);
            if (sendQueries.get(position).getTeacherReplay().equals("True")) {
                holder.txtSeeComment.setTextColor(Color.RED);
            } else {
                holder.txtSeeComment.setTextColor(Color.GREEN);
            }
            holder.txtSeeComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sendQueries.get(position).getTeacherReplay().equals("True")) {
                        if (holder.txtSeeDetailComment.getVisibility() == View.VISIBLE) {
                            holder.txtSeeDetailComment.setVisibility(View.GONE);
                        } else {
                            holder.txtSeeDetailComment.setVisibility(View.VISIBLE);
                            holder.txtSeeDetailComment.setText(sendQueries.get(position).getTeacherReplayMessage().toString());
                        }
                    } else {
                        holder.txtSeeDetailComment.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForGuest", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return sendQueries == null ? 0 : sendQueries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtMessage, txtAcDate, txtSeeComment, txtSeeDetailComment;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtTitle = (TextView) itemView.findViewById(R.id.txtTitleOfMessage);
                txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
                txtSeeComment = (TextView) itemView.findViewById(R.id.txtseeComment);
                txtAcDate = (TextView) itemView.findViewById(R.id.txtMessageDate);
                txtSeeDetailComment = (TextView) itemView.findViewById(R.id.txtseeDetailreply);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "MessageAdapterForGuest", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }


}

