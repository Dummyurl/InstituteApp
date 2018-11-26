package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
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
import com.mksmcqapplicationtest.beans.Test;

import java.util.ArrayList;
import java.util.List;


public class TestEnableAdapter extends RecyclerView.Adapter<TestEnableAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Test> classes;
    View itemView;

    public TestEnableAdapter(Context context, RecyclerView recyclerView, List<Test> classes) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.classes = classes;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_row_enable, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        if (AppUtility.LOGIN_RESPONSE.get(position).getIsAdmin().toString().equals("N")) {
        try {
            holder.txtNameOfUser.setText(classes.get(position).getTestName().toString());
            String ActiveStatus = classes.get(position).getAcFlag().toString();

            if (ActiveStatus.equals("Y")) {
                holder.chkactiveuserCheck.setChecked(true);
                classes.get(position).setAcFlag("Y");
            } else {
                holder.chkactiveuserCheck.setChecked(false);
                classes.get(position).setAcFlag("N");
            }
            //final int finalPosition = position;

            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        classes.get(position).setAcFlag("Y");
                    } else {
                        classes.get(position).setAcFlag("N");
                    }
                }
            });
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestEnableAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    public void setFilter(List<Test> testnewlist) {
        try {
            classes = new ArrayList<>();
            classes.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestEnableAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return classes == null ? 0 : classes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameOfUser;
        CheckBox chkactiveuserCheck;


        public MyViewHolder(View itemView) {
            super(itemView);
            try {

                txtNameOfUser = itemView.findViewById(R.id.txtExamTitle);
                chkactiveuserCheck = itemView.findViewById(R.id.chkactiveuserCheck);
                txtNameOfUser.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "TestEnableAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }

    public void selectAll() {
        try {
            for (int i = 0; i < classes.size(); i++) {
                classes.get(i).setAcFlag("Y");
                classes.get(i).setChecked("Y");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestEnableAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < classes.size(); i++) {
                classes.get(i).setAcFlag("N");
                classes.get(i).setChecked("N");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "TestEnableAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }


}

