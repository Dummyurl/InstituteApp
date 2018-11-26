package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.beans.Student;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class IndividualReportListAdapter extends RecyclerView.Adapter<IndividualReportListAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    int count = 0;
    List<Student> IndividualStatusListResponse;
    View itemView;

    public IndividualReportListAdapter(Context context, RecyclerView recyclerView, List<Student> IndividualStatusListResponse) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.IndividualStatusListResponse = IndividualStatusListResponse;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_of_individual_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtStudentName.setText(IndividualStatusListResponse.get(position).getStudentName().toString());
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "IndividualReportListAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }


    @Override
    public int getItemCount() {

        return IndividualStatusListResponse == null ? 0 : IndividualStatusListResponse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtStudentName;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtStudentName = itemView.findViewById(R.id.txtExamTitle);

            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "IndividualReportListAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }


}

