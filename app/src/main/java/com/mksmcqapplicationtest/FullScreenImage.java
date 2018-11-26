package com.mksmcqapplicationtest;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.mksmcqapplicationtest.util.AppUtility;

import java.util.ArrayList;


import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImage extends AppCompatActivity {
    View v;
    Bundle bundle;
    String image;
    ImageView imageView;
    String URL;
    PhotoViewAttacher mAttacher;
    ProgressDialog dialog;
    int statusBarHeight = 0, actionBarHeight = 0;
    View parentLayout;

    private ArrayList<String> IMAGES = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        parentLayout = findViewById(android.R.id.content);
        try {
            bundle = getIntent().getExtras();
            image = bundle.getString("URL");
            imageView = (ImageView) findViewById(R.id.fullImage);

            int resource = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resource > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resource);
            }
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }

            loadimage(image);
            mAttacher.update();

        } catch (Exception ex) {
            Log.d("Exception", "" + ex);
        }
    }





    private void loadimage(final String image) {
        try {
            String url = image;
            int size = (int) Math.ceil(Math.sqrt(AppUtility.KEY_WIDTH * (AppUtility.KEy_HEIGHT)));
            Picasso.with(this)
                    .load(url)
                    .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {

                                    Snackbar.make(parentLayout, "Unable to load image", Snackbar.LENGTH_LONG)
                                            .setAction("Retry", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    loadimage(image);
                                                }
                                            }).show();
                                    final Handler ha = new Handler();
                                    ha.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            FullScreenImage.this.finish();
                                            ha.postDelayed(this, 3000);
                                        }
                                    }, 3000);


                                }
                            }
                    );
            mAttacher = new PhotoViewAttacher(imageView);

        } catch (Exception ex) {
            Log.d("Exception", "" + ex);
        }
    }


}
