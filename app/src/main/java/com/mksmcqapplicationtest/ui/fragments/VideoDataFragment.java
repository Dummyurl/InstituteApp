package com.mksmcqapplicationtest.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.util.AppUtility;


public class VideoDataFragment extends Fragment {
    View view;
    View parentLayout;
    VideoView videoView;
    Button btnPlayVideo;
    String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_data, container, false);
        parentLayout = view.findViewById(android.R.id.content);
        btnPlayVideo = (Button) view.findViewById(R.id.btnPlayVideo);
        videoView = (VideoView) view.findViewById(R.id.videoVIew);
        url = AppUtility.baseUrl + "UploadedData/" + AppUtility.VideoPath;
        btnPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setDataAndType(Uri.parse(url), "video/*");
                startActivity(Intent.createChooser(intent, "Complete action using"));
            }
        });

        return view;
    }

}

