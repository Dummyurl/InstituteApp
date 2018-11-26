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
import com.mksmcqapplicationtest.beans.Master;
import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.util.AppUtility;

import java.util.ArrayList;
import java.util.List;


public class MasterGetquestionAdapter extends RecyclerView.Adapter<MasterGetquestionAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Master> masterResponse;
    View itemView;

    public MasterGetquestionAdapter(Context context, RecyclerView recyclerView, List<Master> masterResponse) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.masterResponse = masterResponse;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview_row, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtNameOfUser.setText(masterResponse.get(position).getQuestion().toString());

            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        masterResponse.get(position).setAcFlag("Y");
                        masterResponse.get(position).setSelected(buttonView.isChecked());
                    } else {
                        masterResponse.get(position).setAcFlag("N");
                        masterResponse.get(position).setSelected(false);
                    }

                }
            });
            holder.chkactiveuserCheck.setChecked(masterResponse.get(position).isSelected());
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "TestEnableAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    public void setFilter(List<Master> testnewlist) {
        try {
            masterResponse = new ArrayList<>();
            masterResponse.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "TestEnableAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return masterResponse == null ? 0 : masterResponse.size();
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
                txtNameOfUser = (TextView) itemView.findViewById(R.id.txtExamTitle);
                chkactiveuserCheck = (CheckBox) itemView.findViewById(R.id.chkactiveuserCheck);
                txtNameOfUser.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "TestEnableAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }

    public void selectAll() {
        try {
            for (int i = 0; i < masterResponse.size(); i++) {
                masterResponse.get(i).setAcFlag("Y");
                masterResponse.get(i).setSelected(true);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "TestEnableAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < masterResponse.size(); i++) {
                masterResponse.get(i).setAcFlag("N");
                masterResponse.get(i).setSelected(false);
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "TestEnableAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }


}

