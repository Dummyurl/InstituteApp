package com.mksmcqapplicationtest.ui.fragments.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.beans.AudioPlay;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class LevelListAdapter extends RecyclerView.Adapter<LevelListAdapter.RecyclerViewHolder> {
    private Context context;
    List<AudioPlay> audioPlays;
    String AudioListJSONString;
    List<AudioPlay> audioPlaysListResponse;
    ItemListAdapter itemListAdapter;
    View itemView;
    RecyclerViewHolder holder1;

    public LevelListAdapter(Context context, List<AudioPlay> audioPlay) {
        this.context = context;
        this.audioPlays = audioPlay;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.level_row, viewGroup, false);
        return new RecyclerViewHolder(itemView);

    }

    @Override
    public int getItemCount() {
        int levels = Integer.parseInt(audioPlays.get(0).getLevelCount());
        return levels;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        try {
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            int levelPosition = position + 1;
            holder.btnLevel.setText("Level " + levelPosition);

//
            holder.btnLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(context, holder.btnLevel);
                        bounce_button_class.BounceMethod();
                        clickEvent(position, holder);

                    } catch (Exception e) {
                        PrintCatchException pc = new PrintCatchException(context,itemView, "LevelListAdapter", "btnLevel onClick", e);
                        pc.showCatchException();
                    }
                }
            });
            holder.imgMusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(context, holder.btnLevel);
                        bounce_button_class.BounceMethod();
                        clickEvent(position, holder);

                    } catch (Exception e) {
                        PrintCatchException pc = new PrintCatchException(context,itemView, "LevelListAdapter", "imgMusic onClick", e);
                        pc.showCatchException();
                    }
                }
            });
            holder.imgTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(context, holder.btnLevel);
                        bounce_button_class.BounceMethod();
                        clickEvent(position, holder);

                    } catch (Exception e) {
                        PrintCatchException pc = new PrintCatchException(context,itemView, "LevelListAdapter", "imgTest onClick", e);
                        pc.showCatchException();
                    }
                }
            });
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "LevelListAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }

    }

    public void clickEvent(int position, RecyclerViewHolder holder) {
        holder1 = holder;
        int levelcount = position + 1;
        AppUtility.Level = String.valueOf(levelcount);
        if (AppUtility.arraylist[position] == "false") {
            AppUtility.arraylist[position] = "true";

        } else {
            AppUtility.arraylist[position] = "false";
            holder.recyclerView.setVisibility(GONE);
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        Button btnLevel;
        RecyclerView recyclerView;
        ImageButton imgMusic, imgTest;

        public RecyclerViewHolder(View view) {
            super(view);
            try {
                btnLevel = (Button) view.findViewById(R.id.btnLevel);
                recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                imgMusic = (ImageButton) view.findViewById(R.id.music_image_button);
                imgTest = (ImageButton) view.findViewById(R.id.test_image_button);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "LevelListAdapter", "RecyclerViewHolder", e);
                pc.showCatchException();
            }
        }


    }



}