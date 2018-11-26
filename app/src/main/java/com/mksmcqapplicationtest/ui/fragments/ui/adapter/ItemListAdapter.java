package com.mksmcqapplicationtest.ui.fragments.ui.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mksmcqapplicationtest.EnglishSpeaking.UnlockAudioActivity;
import com.mksmcqapplicationtest.MyBounceInterpolator;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.tour.MaterialShowcaseView;
import com.mksmcqapplicationtest.tour.shape.RectangleShape;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.RecyclerViewHolder> {
    private Context context;
    Typeface font;
    List<AudioPlay> audioPlays;
    View itemView;

    public ItemListAdapter(Context context, List<AudioPlay> audioPlay) {
        this.context = context;
        this.audioPlays = audioPlay;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);
        font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public int getItemCount() {

        return audioPlays == null ? 0 : audioPlays.size();

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        try {
            int stage = Integer.parseInt(audioPlays.get(position).getStage());
            int audioPosition = Integer.parseInt(audioPlays.get(position).getAudioCode());
            holder.txtAudioCode.setText(audioPlays.get(position).getAudioCode());
            holder.txtAudioName.setText(audioPlays.get(position).getAudioName());
            if (stage >= audioPosition) {
                holder.relativelay.setBackgroundColor(Color.parseColor("#47A64A"));
                holder.txtLock.setTypeface(font);
                holder.txtLock.setText(R.string.fa_unlock_item_c);

            } else {
                holder.relativelay.setBackgroundColor(Color.parseColor("#F50057"));
                holder.txtLock.setTypeface(font);
                holder.txtLock.setText(R.string.fa_lock);
            }
            // int calStage = position + 1;
            if (stage == Integer.parseInt(audioPlays.get(position).getAudioCode())) {
                holder.relativelay.startAnimation(holder.animBlink);
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ItemListAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }

    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtLock, txtAudioCode, txtAudioName;
        RelativeLayout relativelay;
        Animation animBlink;

        public RecyclerViewHolder(View view) {
            super(view);
            try {
                relativelay = (RelativeLayout) view.findViewById(R.id.relativelay);
                txtLock = (TextView) view.findViewById(R.id.txtLock);
                txtAudioCode = (TextView) view.findViewById(R.id.txtAudioCode);
                txtAudioName = (TextView) view.findViewById(R.id.txtAudioName);
                animBlink = AnimationUtils.loadAnimation(context, R.anim.blink);
                view.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "ItemListAdapter", "RecyclerViewHolder", e);
                pc.showCatchException();
            }

        }


        @Override
        public void onClick(View view) {
            try {
                int stage = Integer.parseInt(audioPlays.get(getPosition()).getStage());
                int audioPosition = Integer.parseInt(audioPlays.get(getPosition()).getAudioCode());
                if (stage >= audioPosition) {
                    final Animation myAnim7 = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    MyBounceInterpolator interpolator7 = new MyBounceInterpolator(0.1, 20);
                    myAnim7.setInterpolator(interpolator7);
                    view.startAnimation(myAnim7);
                    Intent newIntent = new Intent(context, UnlockAudioActivity.class);
                    newIntent.putExtra("AudioPath", audioPlays.get(getPosition()).getAudioPath());
                    newIntent.putExtra("AudioName", audioPlays.get(getPosition()).getAudioName());
                    newIntent.putExtra("AudioFormula", audioPlays.get(getPosition()).getAudioFormula());
                    newIntent.putExtra("AudioDescription", audioPlays.get(getPosition()).getAudioDescription());
                    newIntent.putExtra("AudioCode", audioPlays.get(getPosition()).getAudioCode());
                    newIntent.putExtra("Stage", audioPlays.get(getPosition()).getStage());
                    context.startActivity(newIntent);

                } else {
                    String MaskColor = "#8047A64A";
                    int maskCol = Color.parseColor(MaskColor);
                    String TextMaskColor = "#FFFFFF";
                    int textMaskCol = Color.parseColor(TextMaskColor);

                    new MaterialShowcaseView.Builder((Activity) context)
                            .setShape(new RectangleShape(80, 80))
                            .setTarget(view)
                            .setDismissText("Got It")
                            .setSkipText("Skip")
                            .setDismissStyle(Typeface.DEFAULT_BOLD)
                            .setSkipStyle(Typeface.DEFAULT_BOLD)
                            .setTitleText("This audio is lock. Need to clear previous audio test.")
                            .setTitleTextColor(textMaskCol)
                            .show();
                    //Snackbar.make(itemView, "This audio is lock", Snackbar.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}