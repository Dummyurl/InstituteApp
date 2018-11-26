package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.Student;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class StudentEnableAdapter extends RecyclerView.Adapter<StudentEnableAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<Student> classes;
    View itemView;

    public StudentEnableAdapter(Context context, RecyclerView recyclerView, List<Student> classes) {
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            holder.txtNameOfUser.setText(classes.get(position).getStudentName().toString());
            holder.txtUserName.setText("Mobile Number:" + classes.get(position).getMobileNumber().toString());
            String Password = classes.get(position).getNewPassword();
            if (Password.length() > 2) {
                String FirstTwoCharacter = Password.substring(0, 2);
                String LastTeoCharacter = Password.substring(Password.length() - 2, Password.length());
                holder.txtUserPassword.setText("Password: " + FirstTwoCharacter + "******" + LastTeoCharacter);
            } else {
                holder.txtUserPassword.setText("Password: " + Password);
            }
            String ActiveStatus = classes.get(position).getAcFlag().toString();

            if (ActiveStatus.equals("Y")) {
                holder.chkactiveuserCheck.setChecked(true);
                classes.get(position).setAcFlag("Y");
            } else {
                holder.chkactiveuserCheck.setChecked(false);
                classes.get(position).setAcFlag("N");
            }
            final int finalPosition = position;

            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        classes.get(finalPosition).setAcFlag("Y");
                    } else {
                        classes.get(finalPosition).setAcFlag("N");
                    }
                }
            });

            LoadImage(holder, classes.get(position).getProfile());
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "onBindViewHolder", ex);
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
            PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "LoadImage", e);
            pc.showCatchException();
        }
    }

    public void setFilter(List<Student> testnewlist) {
        try {
            classes = new ArrayList<>();
            classes.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "setFilter", e);
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
        TextView txtNameOfUser, txtUserName, txtUserPassword;
        CheckBox chkactiveuserCheck;
        ImageView studentProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                txtNameOfUser =  itemView.findViewById(R.id.txtExamTitle);
                txtUserName =  itemView.findViewById(R.id.txtDate);
                txtUserPassword =  itemView.findViewById(R.id.txtTestTotalMarks);
                chkactiveuserCheck =  itemView.findViewById(R.id.chkactiveuserCheck);
                studentProfile =  itemView.findViewById(R.id.userImageProfile);
                txtNameOfUser.setVisibility(View.VISIBLE);
                txtUserName.setVisibility(View.VISIBLE);
                txtUserPassword.setVisibility(View.VISIBLE);
                studentProfile.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "MyViewHolder", e);
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
            PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "selectAll", e);
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
            PrintCatchException pc = new PrintCatchException(context,itemView, "StudentEnableAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }


}

