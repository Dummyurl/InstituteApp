package com.mksmcqapplicationtest.MediaPlayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Bounce_View_Class;

import java.util.List;


public class MediaPlayerAdapter extends RecyclerView.Adapter<MediaPlayerAdapter.TestViewHolder> {
    private List<AudioPlay> audioPlaysListResponse;
    Context context;
    View itemView;

    public MediaPlayerAdapter(Context context, List<AudioPlay> audioPlaysList) {
        this.context = context;
        this.audioPlaysListResponse = audioPlaysList;

    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_detail, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TestViewHolder holder, int position) {
        try {
            String AcFlag = audioPlaysListResponse.get(position).getAcFlag().toString();
            if (AcFlag.equals("Y")) {
                int color = ContextCompat.getColor(context, R.color.Black);
                holder.edtMediaName.setTextColor(color);
            } else {
                int color = ContextCompat.getColor(context, R.color.RecyclerViewBackColor);
                holder.edtMediaName.setTextColor(color);
//                holder.edtMediaName.setTextColor(R.color.RecyclerViewBackColor);
            }
            holder.edtMediaName.setText(audioPlaysListResponse.get(position).getAudioName().toString());


        } catch (Exception ex) {
            Log.d("Class Adapter Exception", "" + ex);
        }

    }


    @Override
    public int getItemCount() {
        return audioPlaysListResponse.size();
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView edtMediaName;

        public TestViewHolder(View itemView) {
            super(itemView);
            try {
                edtMediaName = (TextView) itemView.findViewById(R.id.txtVideoName);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "MediaPlayerAdapter", "TestViewHolder", e);
                pc.showCatchException();
            }
        }


        @Override
        public void onClick(View view) {
            try {
                Bounce_View_Class bounce_button_class1 = new Bounce_View_Class(context, view);
                bounce_button_class1.BounceMethod();
                String AcFlag = audioPlaysListResponse.get(getPosition()).getAcFlag().toString();
                if (AcFlag.equals("Y")) {
                    Intent newIntent = new Intent(context, MusicPlayerActivity.class);
                    newIntent.putExtra("AudioPath", audioPlaysListResponse.get(getPosition()).getAudioPath());
                    newIntent.putExtra("AudioName", audioPlaysListResponse.get(getPosition()).getAudioName());
                    newIntent.putExtra("AudioFormula", audioPlaysListResponse.get(getPosition()).getAudioFormula());
                    newIntent.putExtra("AudioDescription", audioPlaysListResponse.get(getPosition()).getAudioDescription());
                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(newIntent);
                } else {

                }

            } catch (Exception ex) {
            }
        }
    }


}
