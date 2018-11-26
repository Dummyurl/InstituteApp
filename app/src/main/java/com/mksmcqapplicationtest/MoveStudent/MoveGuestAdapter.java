package com.mksmcqapplicationtest.MoveStudent;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import com.mksmcqapplicationtest.beans.SignUpGuest;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MoveGuestAdapter extends RecyclerView.Adapter<MoveGuestAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<SignUpGuest> classes;
    View itemView;

    public MoveGuestAdapter(Context context, RecyclerView recyclerView, List<SignUpGuest> classes) {
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
    public void selectAll() {
        try {
            for (int i = 0; i < classes.size(); i++) {
                classes.get(i).setIsSelected("Y");
//                classes.get(i).setChecked("Y");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "selectAll", e);
            pc.showCatchException();
        }
    }

    public void deselectAll() {
        try {
            for (int i = 0; i < classes.size(); i++) {
                classes.get(i).setIsSelected("N");
//                classes.get(i).setChecked("N");
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "DataEnableAdapter", "deselectAll", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        if (AppUtility.LOGIN_RESPONSE.get(position).getIsAdmin().toString().equals("N")) {
        try {
            holder.txtNameOfUser.setText(classes.get(position).getStudentName().toString());
            holder.txtMobileNumber.setText("Mobile Number: " + classes.get(position).getMobileNumber().toString());
            String Password = classes.get(position).getPassword();
            if (Password.length() > 2) {
                String FirstTwoCharacter = Password.substring(0, 2);
                String LastTeoCharacter = Password.substring(Password.length() - 2, Password.length());
                holder.txtPassword.setText("Password: " + FirstTwoCharacter + "******" + LastTeoCharacter);
            } else {
                holder.txtPassword.setText("Password: " + Password);
            }
            holder.txtEmail.setText("Email: " + classes.get(position).getEmailAddress().toString());
            holder.txtUniversityName.setText("University: " + classes.get(position).getUniversityName().toString());
            try {
                String timedate = classes.get(position).getAcDate().toString();
                String splittimedate[] = timedate.split(" ");
                String date = splittimedate[0];
                String time = splittimedate[1];
                holder.txtAcDate.setText(date);
            } catch (Exception ex) {
                Log.d("Ex", "" + ex);
            }

            final int finalPosition = position;
            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        classes.get(finalPosition).setIsSelected("Y");
                    } else {
                        classes.get(finalPosition).setIsSelected("N");
                    }
                }
            });
            if (classes.get(position).getIsSelected() != null) {
                String ActiveStatus = classes.get(position).getIsSelected().toString();

                if (ActiveStatus.equals("Y")) {
                    holder.chkactiveuserCheck.setChecked(true);
                } else {
                    holder.chkactiveuserCheck.setChecked(false);
                }
            }
            LoadImage(holder, classes.get(position).getProfile());
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "MoveGuestAdapter", "onBindViewHolder", ex);
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
            PrintCatchException pc = new PrintCatchException(context,itemView, "MoveGuestAdapter", "LoadImage", e);
            pc.showCatchException();
        }
    }

    public void setFilter(List<SignUpGuest> testnewlist) {
        try {
            classes = new ArrayList<>();
            classes.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "MoveGuestAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }


    @Override
    public int getItemCount() {
        return classes == null ? 0 : classes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView txtNameOfUser, txtMobileNumber, txtPassword, txtEmail, txtAcDate,txtUniversityName;
        CheckBox chkactiveuserCheck;
        CircleImageView studentProfile;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {

                txtNameOfUser =  itemView.findViewById(R.id.txtExamTitle);
                txtMobileNumber =  itemView.findViewById(R.id.txtDate);
                txtPassword =  itemView.findViewById(R.id.txtTestTotalMarks);
                chkactiveuserCheck =  itemView.findViewById(R.id.chkactiveuserCheck);
                studentProfile =  itemView.findViewById(R.id.userImageProfile);
                txtEmail =  itemView.findViewById(R.id.txtEmailAddress);
                txtUniversityName = itemView.findViewById(R.id.txtUniversityName);
                txtAcDate =  itemView.findViewById(R.id.txtAcDate);
                txtNameOfUser.setVisibility(View.VISIBLE);
                txtMobileNumber.setVisibility(View.VISIBLE);
                txtPassword.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                txtUniversityName.setVisibility(View.VISIBLE);
                txtAcDate.setVisibility(View.VISIBLE);
                studentProfile.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "MoveGuestAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }
    }


}

