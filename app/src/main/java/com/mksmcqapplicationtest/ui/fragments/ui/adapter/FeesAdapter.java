package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Fees;

import java.util.List;


public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.TestViewHolder> {
    private List<Fees> feess;
    Context context;
    View itemView;

    public FeesAdapter(Context context, List<Fees> fees) {
        this.context = context;
        this.feess = fees;

    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fees_detail, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        try {
            holder.edtPaidFees.setText(feess.get(position).getPaidFees());
            String TimeAndDate = feess.get(position).getPaidDate();
            String[] items1 = TimeAndDate.split(" ");
            holder.edtPaidDate.setText(items1[0]);
            holder.edtPaidTime.setText(items1[1] + " " + items1[2]);
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "FeesAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }

    }


    @Override
    public int getItemCount() {
        return feess.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder {


        TextView edtPaidDate, edtPaidTime, edtPaidFees;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                edtPaidDate = (TextView) itemView.findViewById(R.id.edtPaidDate);
                edtPaidTime = (TextView) itemView.findViewById(R.id.edtPaidTime);
                edtPaidFees = (TextView) itemView.findViewById(R.id.edtPaidFees);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "FeesAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }
    }
}
