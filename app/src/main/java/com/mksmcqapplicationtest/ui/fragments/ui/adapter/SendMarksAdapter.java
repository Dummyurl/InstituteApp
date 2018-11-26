package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomEditText;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SendMarksAdapter extends RecyclerView.Adapter<SendMarksAdapter.MyViewHolder> {

    Context context;
    RecyclerView recyclerView;
    private List<Student> StudentResponseOnDataCode;
    View itemView;
    String StudentCode;

    public SendMarksAdapter(Context context, RecyclerView recyclerView, List<Student> studentResponseOnDataCode) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.StudentResponseOnDataCode = studentResponseOnDataCode;
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
            holder.txtTitle.setText(StudentResponseOnDataCode.get(position).getStudentName());
            LoadImage(holder, StudentResponseOnDataCode.get(position).getProfile());

            holder.etdEnterMarks.setText(AppUtility.arraylist[position]);

            holder.etdEnterMarks.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void afterTextChanged(Editable editable) {
                    AppUtility.arraylist[position] = holder.etdEnterMarks.getText().toString();
                }

            });

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "SendMarksAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }
    }

    private void LoadImage(MyViewHolder holder, String profile) {
        try {
            if (profile != null || !profile.equals("")) {
                String imageString = AppUtility.baseUrl + profile;
                Picasso.with(context)
                        .load(imageString).error(R.drawable.profile).into(holder.imgprofile);
            } else {
                holder.imgprofile.setImageResource(R.drawable.profile);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "SendMarksAdapter", "LoadImage", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return StudentResponseOnDataCode.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgprofile;


        TextView txtTitle, txtDate, txtTestTotalMarks, txtTestNegativeMark, txtScore, txtTestTime, txtAttempt, txtTextData, txtImageData, txtVideoData, txtPDFData;
        CustomEditText etdEnterMarks;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                imgprofile =  itemView.findViewById(R.id.userImageProfile);

                txtTitle = itemView.findViewById(R.id.txtExamTitle);
                txtDate = itemView.findViewById(R.id.txtDate);
                txtTestTotalMarks = itemView.findViewById(R.id.txtTestTotalMarks);
                txtTestNegativeMark = itemView.findViewById(R.id.txtTestNegativeMark);
                txtScore = itemView.findViewById(R.id.txtScore);
                txtTestTime = itemView.findViewById(R.id.txtTestTime);
                txtAttempt = itemView.findViewById(R.id.txtAttempt);
                txtTextData = itemView.findViewById(R.id.txtTextData);
                txtImageData = itemView.findViewById(R.id.txtImageData);
                txtVideoData = itemView.findViewById(R.id.txtVideoData);
                txtPDFData = itemView.findViewById(R.id.txtPDFData);
                etdEnterMarks = itemView.findViewById(R.id.etdEnterMarks);


                txtTitle.setVisibility(View.VISIBLE);
                txtTestTotalMarks.setVisibility(View.GONE);
                txtTestNegativeMark.setVisibility(View.GONE);
                txtTestTime.setVisibility(View.GONE);
                txtDate.setVisibility(View.GONE);
                txtScore.setVisibility(View.GONE);
                txtAttempt.setVisibility(View.GONE);
                txtTextData.setVisibility(View.GONE);
                txtImageData.setVisibility(View.GONE);
                txtVideoData.setVisibility(View.GONE);
                txtPDFData.setVisibility(View.GONE);
                etdEnterMarks.setVisibility(View.VISIBLE);


            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "SendMarksAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }

}
