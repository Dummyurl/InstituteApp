package com.mksmcqapplicationtest.TabStructure.Other;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.Guest.VideoManualAdapterForGuest;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.VideoManual;

import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabVideoManual extends Fragment implements ResponseListener {

    View view;
    String AudioListJSONString;
    List<VideoManual> audioPlaysListResponse;
    RecyclerView recyclerViewOfMediaName;
    VideoManualAdapterForGuest mediaPlayerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_video_manual, container, false);
        try {
            setUI();
            recyclerViewOfMediaName = (RecyclerView) view.findViewById(R.id.recyclerViewOfMediaName);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerViewOfMediaName.setLayoutManager(layoutManager);
            recyclerViewOfMediaName.setItemAnimator(new DefaultItemAnimator());
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                networkCallForVideoManual();
            }


            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                }
            });


        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabVideoManual", "onCreate", e);
            pc.showCatchException();
        }
        return view;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Video_Manual_hint));

        ImageButton imageadd = getActivity().findViewById(R.id.imageadd);
        imageadd.setVisibility(View.GONE);

        ImageButton imageButtonBackPress = getActivity().findViewById(R.id.imagviewbackpress);
        imageButtonBackPress.setVisibility(View.VISIBLE);
        imageButtonBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void networkCallForVideoManual() {
        try {
            CreateJSON();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.getVideoManualList(AudioListJSONString, AppUtility.VIDEO_MANUAL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabVideoManual", "networkCallForVideoManual", e);
            pc.showCatchException();
        }
    }

    private void CreateJSON() {
        try {
            String toWhome="";
            if(AppUtility.IsTeacher.equals("T")){toWhome="Teacher";}
            else if(AppUtility.IsTeacher.equals("A")){toWhome="Teacher";}
            else if(AppUtility.IsTeacher.equals("S")){toWhome="Student";}
            else {toWhome="Guest";}
            VideoManual audioPlayResponse = new VideoManual();
            Gson gson = new Gson();
            audioPlayResponse.setForWhome(toWhome);
            String classResponseJSON = gson.toJson(audioPlayResponse);
            AudioListJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabVideoManual", "CreateJSON", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            switch (requestCode) {
                case 580:
                    audioPlaysListResponse = (List<VideoManual>) data;
                    String responseMsg = audioPlaysListResponse.get(0).getResult().toString();
                    if (responseMsg.equals("Suessfull")) {
                        mediaPlayerAdapter = new VideoManualAdapterForGuest(getContext(), audioPlaysListResponse);
                        recyclerViewOfMediaName.setAdapter(mediaPlayerAdapter);

                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",responseMsg);
                    }
                    break;

            }
        } catch (Exception ex) {
            Snackbar.make(view, "Something went wrong Try again later", Snackbar.LENGTH_LONG).show();

        }
    }


    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabVideoManual", "noResponse", e);
            pc.showCatchException();
        }
    }
}

