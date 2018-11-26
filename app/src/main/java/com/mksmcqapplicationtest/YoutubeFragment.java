package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.mksmcqapplicationtest.util.AppUtility;


public class YoutubeFragment extends Fragment {

    private static final String API_KEY = "MKS";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.you_tube_api, container, false);
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    player.loadVideo(AppUtility.VIDEO_ID);
                    player.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                try {
                    String errorMessage = error.toString();
                    if (errorMessage.equals("SERVICE_MISSING")) {
                        try {
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog);
                            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
                            txtTitle.setText("Warning!!!");
                            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
                            //Are you sure You Tube app is present into your mobile???
                            txtMessage.setText("You required to install You Tube Player to watch this video");
                            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                            dialogButton.setText("Install");
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                dialog.dismiss();
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube"));
                                    startActivity(intent);
                                    getActivity().finish();
                                    dialog.dismiss();

                                }
                            });
                            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);

                            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    getActivity().finish();
                                }
                            });
                            dialog.show();
                        } catch (Exception ex) {
                            Log.d("Exception", "" + ex);
                        }
                    }
                    Log.d("errorMessage:", errorMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

}