package com.mksmcqapplicationtest.TabStructure.Communication;

import android.content.Context;
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
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.SendQuery;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.MessageAdapterForTeacher;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabQueries extends Fragment implements ResponseListener {

    RecyclerView recyclerViewForMessage;
    Context context;
    String title, message, SendQueryJSONString;
    View parentLayout;
    List<SendQuery> sendQueries;
    MessageAdapterForTeacher adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_queies_guest_stud, container, false);
        try {
            setUI();
            this.context = getContext().getApplicationContext();
            recyclerViewForMessage = parentLayout.findViewById(R.id.recyclerViewMessage);
            swipeRefreshLayout = parentLayout.findViewById(R.id.swipeRefreshLayout);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewForMessage.setLayoutManager(layoutManager);
            recyclerViewForMessage.setItemAnimator(new DefaultItemAnimator());
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
NetworkCallForGetQuery();
                }
            });
            InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
            boolean availble = internetConnectionCheck.checkConnection();
            if (availble) {
                NetworkCallForGetQuery();
            }



        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForTeacher", "OnCreate", e);
            pc.showCatchException();
        }
        return parentLayout;
    }

    private void setUI() {
        CustomTextViewBold txtactionbartitle = getActivity().findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Queries_hint));

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

    private void NetworkCallForGetQuery() {
        try {
            CreateJSonForGetQuery();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.GetQuery(SendQueryJSONString, AppUtility.GET_QUERY);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForTeacher", "NetworkCallForGetQuery", e);
            pc.showCatchException();
        }
    }

    private void CreateJSonForGetQuery() {
        try {
            SendQuery sendQuery = new SendQuery();
            Gson gson = new Gson();
            sendQuery.setUserName(AppUtility.KEY_USERNAME);
            sendQuery.setStudentCode(AppUtility.KEY_STUDENTCODE);
            sendQuery.setWhichList("all");
            String getQuery = gson.toJson(sendQuery);
            SendQueryJSONString = "[" + getQuery + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForTeacher", "CreateJSonForGetQuery", e);
            pc.showCatchException();
        }
    }

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            switch (requestCode) {
                case 660:
                    try {
                        sendQueries = (List<SendQuery>) data;
                        if (sendQueries.get(0).getResultCode().equals("1")) {
                            adapter = new MessageAdapterForTeacher(getContext(), recyclerViewForMessage, sendQueries);
                            recyclerViewForMessage.setAdapter(adapter);
//                        NetworkCallForGetQuery();
                        } else {
                            ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                            showErrorPopUp.ShowPopUp(getContext(),"Error",sendQueries.get(0).getResult().toString());
                        }

                    } catch (Exception ex) {
                        Snackbar.make(parentLayout, "Message:" + ex, Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForTeacher", "onResponseWithRequestCode", e);
            pc.showCatchException();
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForTeacher", "noResponse", e);
            pc.showCatchException();
        }
    }



}

