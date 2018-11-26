package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;


public class ProfileAdapterForStudent extends RecyclerView.Adapter<ProfileAdapterForStudent.TestViewHolder> {
    Context context;
    Typeface font1;
    String Feild;
    String Feild_User;
    int Font;
    View itemView;

    public ProfileAdapterForStudent(Context context, String Feild, String Feild_User, int Font) {
        this.context = context;
        this.Feild = Feild;
        this.Feild_User = Feild_User;
        this.Font = Font;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_detail_for_student, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        context = parent.getContext();
        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        try {
            holder.Font_icon_phone.setTypeface(font1);
            holder.Font_icon_phone.setText(Font);
            holder.txt_Feild_Name.setText(Feild);
            holder.txt_Feild.setText(Feild_User);
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ProfileAdapterForStudent", "onBindViewHolder", ex);
            pc.showCatchException();
        }

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    class TestViewHolder extends RecyclerView.ViewHolder {

        TextView Font_icon_phone, txt_Feild, txt_Feild_Name;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                Font_icon_phone = (TextView) itemView.findViewById(R.id.Font_icon_phone);
                txt_Feild = (TextView) itemView.findViewById(R.id.txt_Feild);
                txt_Feild_Name = (TextView) itemView.findViewById(R.id.txt_Feild_Name);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "ProfileAdapterForStudent", "TestViewHolder", e);
                pc.showCatchException();
            }
        }


    }
}
