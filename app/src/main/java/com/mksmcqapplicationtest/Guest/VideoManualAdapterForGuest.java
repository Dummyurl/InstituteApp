package com.mksmcqapplicationtest.Guest;

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
import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.beans.VideoManual;

import java.util.List;


public class VideoManualAdapterForGuest extends RecyclerView.Adapter<VideoManualAdapterForGuest.TestViewHolder> {
    private List<VideoManual> audioPlaysListResponse;
    Context context;
    View itemView;

    public VideoManualAdapterForGuest(Context context, List<VideoManual> audioPlaysList) {
        this.context = context;
        this.audioPlaysListResponse = audioPlaysList;

    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_detail, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        try {
            holder.txtVideoName.setText(audioPlaysListResponse.get(position).getVideoName().toString());
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "VideoManualAdapterForGuest", "onBindViewHolder", e);
            pc.showCatchException();
        }

    }


    @Override
    public int getItemCount() {
        return audioPlaysListResponse.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        CustomTextView txtVideoName;

        public TestViewHolder(View itemView) {
            super(itemView);
            try{
                txtVideoName =  itemView.findViewById(R.id.txtVideoName);
                itemView.setOnClickListener(this);
            }catch(Exception e){
                PrintCatchException pc = new PrintCatchException(context,itemView, "VideoManualAdapterForGuest", "TestViewHolder", e);
                pc.showCatchException();
            }
        }


        @Override
        public void onClick(View view) {
            try {
                    Intent newIntent = new Intent(context, YoutubeVideoPlayerActivity.class);
                    newIntent.putExtra("VideoID", audioPlaysListResponse.get(getPosition()).getVideoID());
                    newIntent.putExtra("VideoName", audioPlaysListResponse.get(getPosition()).getVideoName());
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newIntent);
            } catch (Exception ex) {
            }
        }
    }


}
