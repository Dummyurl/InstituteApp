package com.mksmcqapplicationtest.beans;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Samsung on 16/01/2018.
 */

public class AdvertiesClass extends Activity{
    Context context;
    int id;
    private AdView mAdView;
    Activity activity;

    public AdvertiesClass(Context Context, Activity acti, int Id) {
        this.context=Context;
        this.id=Id;
        this.activity=acti;
    }

    public void ShowAdver(){
        mAdView = (AdView) activity.findViewById(id);
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        if (AppUtility.IsAdvertiesVisible) {
//            mAdView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        super.onDestroy();
    }


}
