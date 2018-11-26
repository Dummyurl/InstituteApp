package com.mksmcqapplicationtest.ui.fragments.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.FullScreenImage;
import com.mksmcqapplicationtest.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingPosterAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> IMAGES = new ArrayList<String>();
    private ArrayList<String> HintName = new ArrayList<String>();


    String image;
    ImageView imgPoster;
    TextView txtPosterHint;
    String url;
    ViewPager view_pager;

    public SlidingPosterAdapter(Context context, ArrayList<String> IMAGES, ArrayList<String> HintName) {
        this.context = context;
        this.IMAGES = IMAGES;
        this.HintName = HintName;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {

        View imageLayout = inflater.inflate(R.layout.notice_card_view, view, false);
        assert imageLayout != null;
        try {
            txtPosterHint = (TextView) imageLayout.findViewById(R.id.txtNoticeHint);
            imgPoster = (ImageView) imageLayout.findViewById(R.id.imgNotice);
//            imgPoster.setMaxWidth(200);
//            imgPoster.setMinimumWidth(200);
//        url = client_AppUtility.imageURL + posterresponce.get(position).toString().trim();
            txtPosterHint.setText(HintName.get(position).toString().trim());
            setImageInViewPager(IMAGES.get(position).toString().trim());
            view.addView(imageLayout, 0);
            url = AppUtility.baseUrl + IMAGES.get(position).toString().trim();
            imgPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        url = AppUtility.baseUrl + IMAGES.get(position).toString().trim();
                        Intent fullScreenIntent = new Intent(context, FullScreenImage.class);
                        fullScreenIntent.putExtra("URL", url);
                        context.startActivity(fullScreenIntent);
                    }
                }
            });

        } catch (Exception ex) {
            Log.d("Exception", "" + ex);
        }
        return imageLayout;
    }


    private void setImageInViewPager(String ImagePath) {
        try {
            image = AppUtility.baseUrl + ImagePath;
            Picasso.with(context)
                    .load(image)
                    .error(R.drawable.notice_image)
                    .into(imgPoster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}