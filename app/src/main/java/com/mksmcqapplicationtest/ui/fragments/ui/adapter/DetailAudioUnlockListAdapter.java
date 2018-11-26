package com.mksmcqapplicationtest.ui.fragments.ui.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksmcqapplicationtest.EnglishSpeaking.UnlockAudioActivity;
import com.mksmcqapplicationtest.MyBounceInterpolator;
import com.mksmcqapplicationtest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailAudioUnlockListAdapter extends RecyclerView.Adapter<DetailAudioUnlockListAdapter.RecyclerViewHolder> {
    Typeface font1;// Recyclerview will extend to
    RecyclerView recyclerView;
    private Context context;
    Typeface font;
    Integer originalPrice, Price;
    String Percentage;
    String[] arrayList;

    public DetailAudioUnlockListAdapter(Context context, RecyclerView recyclerView, String[] arrayList) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.arrayList = arrayList;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.card_view_unlock_audio_list, viewGroup, false);
        return new RecyclerViewHolder(mainGroup);

    }

    @Override
    public int getItemCount() {

        return arrayList == null ? 0 : arrayList.length;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        try {
            holder.txtMenuName.setText(arrayList[position]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtMenuName;

        public RecyclerViewHolder(View view) {
            super(view);
            try {
                this.txtMenuName = (TextView) view.findViewById(R.id.txtMenuName);
                view.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onClick(View view) {
            try{
                final Animation myAnim7 = AnimationUtils.loadAnimation(context, R.anim.bounce);
                MyBounceInterpolator interpolator7 = new MyBounceInterpolator(0.1, 20);
                myAnim7.setInterpolator(interpolator7);
                view.startAnimation(myAnim7);
                Intent intent = new Intent(context, UnlockAudioActivity.class);
                context.startActivity(intent);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


}