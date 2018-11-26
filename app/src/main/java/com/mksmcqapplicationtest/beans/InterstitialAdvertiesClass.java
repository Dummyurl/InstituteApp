package com.mksmcqapplicationtest.beans;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.mksmcqapplicationtest.R;


/**
 * Created by Samsung on 17/01/2018.
 */

public class InterstitialAdvertiesClass {

    Context context;
    InterstitialAd mInterstitialAd;

    public InterstitialAdvertiesClass(Context context) {
        this.context=context;
    }

    public void showInterstitialAdver(){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.Interstitial_main_new));
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });

    }

    public void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
//            if (AppUtility.IsAdvertiesVisible) {
                mInterstitialAd.show();
//            }

        }
    }
}
