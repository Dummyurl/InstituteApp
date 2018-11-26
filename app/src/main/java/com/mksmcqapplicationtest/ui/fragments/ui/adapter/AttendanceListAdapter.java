package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    Boolean isSelectedAll = false;
    List<Student> AttendanceListResponse;
    //    public int global_position=0;
    int i;
    View itemView;

    public AttendanceListAdapter(Context context, RecyclerView recyclerView, List<Student> AttendanceListResponse) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.AttendanceListResponse = AttendanceListResponse;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_of_attendance_list, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    public void selectAll() {
        try {
            for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                AppUtility.STUDENT_RESPONSE.get(i).setSelected(true);
                AppUtility.STUDENT_RESPONSE.get(i).setCheck("Y");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "AttendanceListAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < AppUtility.STUDENT_RESPONSE.size(); i++) {
                AppUtility.STUDENT_RESPONSE.get(i).setSelected(false);
                AppUtility.STUDENT_RESPONSE.get(i).setCheck("N");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "AttendanceListAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            String Name = AppUtility.STUDENT_RESPONSE.get(position).getStudentName();
            holder.txtStudentName.setText(Name);

            if (holder.chkMarkAttendance.isChecked()) {
                AppUtility.STUDENT_RESPONSE.get(position).setCheck("Y");
            } else {
                AppUtility.STUDENT_RESPONSE.get(position).setCheck("N");
            }

            if (AppUtility.STUDENT_RESPONSE.get(position).isSelected()) {
                AppUtility.STUDENT_RESPONSE.get(position).setCheck("Y");
            } else {
                AppUtility.STUDENT_RESPONSE.get(position).setCheck("N");
            }

            holder.chkMarkAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (isChecked) {
                            AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT += isChecked ? 1 : -1;
                            AppUtility.STUDENT_RESPONSE.get(position).setCheck("Y");
                            AppUtility.STUDENT_RESPONSE.get(position).setSelected(buttonView.isChecked());
                            AppUtility.SELECT_ALL_IS_CHECKED = "S";
                        } else {
                            if (AppUtility.SELECT_ALL_IS_CHECKED.equals("N")) {
                                AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT = 0;
                                AppUtility.STUDENT_RESPONSE.get(position).setCheck("N");
                                AppUtility.SELECT_ALL_IS_CHECKED = "P";
                                AppUtility.STUDENT_RESPONSE.get(position).setSelected(false);
                            } else if (AppUtility.SELECT_ALL_IS_CHECKED.equals("S")) {
                                AppUtility.KEY_ATTENDANCE_CHECK_BOX_CHECKED_COUNT += isChecked ? 1 : -1;
                                AppUtility.STUDENT_RESPONSE.get(position).setCheck("N");
                                AppUtility.STUDENT_RESPONSE.get(position).setSelected(false);
                            }

                        }
                    } catch (Exception ex) {
                        Log.d("Checkbox Exeption", "" + ex);
                    }
                }
            });


            holder.chkMarkAttendance.setChecked(AppUtility.STUDENT_RESPONSE.get(position).isSelected());
            LoadImage(holder, AppUtility.STUDENT_RESPONSE.get(position).getProfile());
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "AttendanceListAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    private void LoadImage(MyViewHolder holder, String profile) {
        try {
            if (profile != null || !profile.equals("")) {
                String imageString = AppUtility.baseUrl + profile;
                Picasso.with(context)
                        .load(imageString).error(R.drawable.profile).into(holder.studentProfile);
            } else {
                holder.studentProfile.setImageResource(R.drawable.profile);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context, itemView, "AttendanceListAdapter", "LoadImage", e);
            pc.showCatchException();
        }
    }


    @Override
    public int getItemCount() {

        return AttendanceListResponse == null ? 0 : AttendanceListResponse.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtStudentName;
        CheckBox chkMarkAttendance;
        CircleImageView studentProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtStudentName = itemView.findViewById(R.id.txtExamTitle);
                chkMarkAttendance = itemView.findViewById(R.id.chkactiveuserCheck);
                studentProfile = itemView.findViewById(R.id.userImageProfile);
                txtStudentName.setVisibility(View.VISIBLE);
                studentProfile.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context, itemView, "AttendanceListAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }


    }


}

