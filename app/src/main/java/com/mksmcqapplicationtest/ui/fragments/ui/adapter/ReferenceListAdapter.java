package com.mksmcqapplicationtest.ui.fragments.ui.adapter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.beans.References;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReferenceListAdapter extends RecyclerView.Adapter<ReferenceListAdapter.RecyclerViewHolder> {
    private Context context;
    Typeface font;
    List<References> references;
    View itemView;

    public ReferenceListAdapter(Context context, List<References> references) {
        this.context = context;
        this.references = references;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_referance, viewGroup, false);
        font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public int getItemCount() {

        return references.size();

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        try {
            holder.txtAppName.setText(references.get(position).getAppName());
            if (!references.get(position).getAppShortDetail().equals("")) {
                holder.txtAppShortDetail.setText(references.get(position).getAppShortDetail());
            }
            boolean isAppInstalled = appInstalledOrNot(references.get(position).getAppPackageName());
            if (isAppInstalled) {
                //app is allready install
                holder.txtinstall.setTypeface(font);
                holder.txtinstall.setText(R.string.fa_download_complete);
            } else {
                //app is not install
                holder.txtinstall.setTypeface(font);
                holder.txtinstall.setText(R.string.fa_download);
            }
            LoadImage(holder, references.get(position).getAppLogo());

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(context,itemView, "ReferenceListAdapter", "onBindViewHolder", e);
            pc.showCatchException();
        }

    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtAppName, txtAppShortDetail, txtinstall;
        RelativeLayout relativelay;
        ImageView imgLogo;

        public RecyclerViewHolder(View view) {
            super(view);
            try {
                relativelay = (RelativeLayout) view.findViewById(R.id.relativelay);
                txtAppName = (TextView) view.findViewById(R.id.txtAppName);
                txtAppShortDetail = (TextView) view.findViewById(R.id.txtAppShortDetail);
                txtinstall = (TextView) view.findViewById(R.id.txtinstall);
                imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
                view.setOnClickListener(this);
            } catch (Exception e) {
                PrintCatchException pc = new PrintCatchException(context,itemView, "ReferenceListAdapter", "RecyclerViewHolder", e);
                pc.showCatchException();
            }

        }

        @Override
        public void onClick(View v) {
            Uri uri = Uri.parse("market://details?id=" + references.get(getPosition()).getAppPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                context.startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + references.get(getPosition()).getAppPackageName())));
            }
        }
    }


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void LoadImage(RecyclerViewHolder holder, String imagePath1) {
        try {
            if (imagePath1 != null || !imagePath1.equals("")) {

                String image = AppUtility.LetUsCbaseUrl + imagePath1;
                Picasso.with(context)
                        .load(image)
                        .error(R.drawable.noimageavailable)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(holder.imgLogo);

            } else {
                holder.imgLogo.setImageResource(R.drawable.noimageavailable);
                //TODO
            }

        } catch (Exception ex) {
            Log.d("Exception", "" + ex);
        }
    }

}