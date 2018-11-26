package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.MediaPlayer.MusicPlayerActivity;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;

import java.util.ArrayList;
import java.util.List;


public class AudioFilesEnableAdapter extends RecyclerView.Adapter<AudioFilesEnableAdapter.MyViewHolder> {
    Context context;
    RecyclerView recyclerView;
    List<AudioPlay> datas;
    Typeface font1;
    View itemView;

    public AudioFilesEnableAdapter(Context context, RecyclerView recyclerView, List<AudioPlay> datas) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_of_enable, parent, false);
        font1 = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        context = parent.getContext();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {
            holder.txtNameOfUser.setText(datas.get(position).getAudioName().toString());

            String ActiveStatus = datas.get(position).getAcFlag().toString();

            if (ActiveStatus.equals("Y")) {
                holder.chkactiveuserCheck.setChecked(true);
                datas.get(position).setAcFlag("Y");
            } else {
                holder.chkactiveuserCheck.setChecked(false);
                datas.get(position).setAcFlag("N");
            }
            final int finalPostion = position;
            holder.chkactiveuserCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        datas.get(finalPostion).setAcFlag("Y");
                    } else {
                        datas.get(finalPostion).setAcFlag("N");
                    }
                }
            });
        } catch (Exception ex) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "AudioFilesEnableAdapter", "onBindViewHolder", ex);
            pc.showCatchException();
        }
    }

    public void setFilter(List<AudioPlay> testnewlist) {
        try {
            datas = new ArrayList<>();
            datas.addAll(testnewlist);
            notifyDataSetChanged();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "AudioFilesEnableAdapter", "setFilter", e);
            pc.showCatchException();
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNameOfUser, txtAcDate;
        TextView txtTextData, txtImageData, txtVideoData, txtPDFData;
        CheckBox chkactiveuserCheck;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            try {
                cardView = (CardView) itemView.findViewById(R.id.cardview);
                txtNameOfUser = (TextView) itemView.findViewById(R.id.txtNameOfUser);
                chkactiveuserCheck = (CheckBox) itemView.findViewById(R.id.chkactiveuserCheck);
                txtTextData = (TextView) itemView.findViewById(R.id.TextData);
                txtImageData = (TextView) itemView.findViewById(R.id.ImageData);
                txtVideoData = (TextView) itemView.findViewById(R.id.VideoData);
                txtPDFData = (TextView) itemView.findViewById(R.id.PDFData);
                txtAcDate = (TextView) itemView.findViewById(R.id.DataCreationDate);
                txtTextData.setVisibility(View.GONE);
                txtImageData.setVisibility(View.GONE);
                txtVideoData.setVisibility(View.GONE);
                txtPDFData.setVisibility(View.GONE);
                txtAcDate.setVisibility(View.GONE);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "AudioFilesEnableAdapter", "MyViewHolder", e);
                pc.showCatchException();
            }
        }

        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                Intent newIntent = new Intent(context, MusicPlayerActivity.class);
                newIntent.putExtra("AudioPath", datas.get(getPosition()).getAudioPath());
                newIntent.putExtra("AudioName", datas.get(getPosition()).getAudioName());
                newIntent.putExtra("AudioFormula", datas.get(getPosition()).getAudioFormula());
                newIntent.putExtra("AudioDescription", datas.get(getPosition()).getAudioDescription());
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception ex) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "AudioFilesEnableAdapter", "onClick", ex);
                pc.showCatchException();
            }
        }
    }


}

