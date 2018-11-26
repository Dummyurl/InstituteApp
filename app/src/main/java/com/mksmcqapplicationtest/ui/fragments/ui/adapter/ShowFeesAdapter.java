package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowDetailFees;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;
import com.mksmcqapplicationtest.beans.Fees;
import com.mksmcqapplicationtest.util.AppUtility;

import org.w3c.dom.Text;

import java.util.List;

public class ShowFeesAdapter extends RecyclerView.Adapter<ShowFeesAdapter.TestViewHolder> {
    private List<Fees> feess;
    Context context;
    String ClassCodeForNetworkCall;
    String EducationalYear;
    View itemView;
    public ShowFeesAdapter(Context context, List<Fees> fees, String ClassCodeForNetworkCall, String EducationalYear) {
        this.context = context;
        this.feess = fees;
        this.ClassCodeForNetworkCall = ClassCodeForNetworkCall;
        this.EducationalYear = EducationalYear;

    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_fees, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        try {
            holder.txtStudentName.setText(feess.get(position).getStudentName());
            holder.txtTotalPaid.setText("Total Paid: "+feess.get(position).getTotalPaid());
            int TotalPaid = Integer.parseInt(feess.get(position).getTotalPaid());
            int RemaingPaid = AppUtility.ClassTotalFees - TotalPaid;
            holder.txtRemainingPaid.setText("Remaining: "+String.valueOf(RemaingPaid));
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ShowFeesAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }


    @Override
    public int getItemCount() {
        return feess.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CustomTextView txtStudentName, txtTotalPaid, txtRemainingPaid;

        public TestViewHolder(View itemView) {
            super(itemView);
            txtStudentName =  itemView.findViewById(R.id.txtStudentName);
            txtTotalPaid = itemView.findViewById(R.id.txtTotalPaid);
            txtRemainingPaid = itemView.findViewById(R.id.txtRemainingPaid);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                Intent newIntent = new Intent(context, ShowDetailFees.class);
                newIntent.putExtra("StudentCode", feess.get(getPosition()).getStudentCode());
                newIntent.putExtra("ClassCode", ClassCodeForNetworkCall);
                newIntent.putExtra("EducationalYear", EducationalYear);
                newIntent.putExtra("StudentName", feess.get(getPosition()).getStudentName());
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception ex) {
            }
        }
    }
}
