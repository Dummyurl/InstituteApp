package com.mksmcqapplicationtest.TabStructure.Communication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.View.CustomTextViewBold;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.Bounce_Button_Class;
import com.mksmcqapplicationtest.beans.SendQuery;
import com.mksmcqapplicationtest.dataaccess.DataAccess;
import com.mksmcqapplicationtest.ui.fragments.ui.adapter.MessageAdapterForGuest;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.List;

public class TabQueriesGuestStud extends Fragment implements ResponseListener {

    RecyclerView recyclerViewForMessage;
    FloatingActionButton fab;
    Context context;
    String title, message, SendQueryJSONString;
    View parentLayout;
    List<SendQuery> sendQueries;
    MessageAdapterForGuest adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentLayout = inflater.inflate(R.layout.tab_queies_guest_stud, container, false);
        try {
            setUI();
            this.context = getContext().getApplicationContext();
            recyclerViewForMessage = (RecyclerView) parentLayout.findViewById(R.id.recyclerViewMessage);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerViewForMessage.setLayoutManager(layoutManager);
            recyclerViewForMessage.setItemAnimator(new DefaultItemAnimator());
            fab = (FloatingActionButton) parentLayout.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            swipeRefreshLayout = (SwipeRefreshLayout) parentLayout.findViewById(R.id.swipeRefreshLayout);
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


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.sendmsg_dialog);
                    final EditText txtTitle = (EditText) dialog.findViewById(R.id.txttitleOfMessage);
                    final EditText txtMessage = (EditText) dialog.findViewById(R.id.txtcontentOfMessage);

                    final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), dialogButton);
                            bounce_button_class.BounceMethod();
                            title = txtTitle.getText().toString().trim();
                            message = txtMessage.getText().toString().trim();
                            boolean result = CheckValidate();
                            dialog.dismiss();
                            NetworkCallForSendQuery();


                        }
                    });
                    final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
                    CancledialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bounce_Button_Class bounce_button_class = new Bounce_Button_Class(getContext(), CancledialogButton);
                            bounce_button_class.BounceMethod();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
            if (AppUtility.IsTeacher.equals("G")) {
                if (AppUtility.IsAdvertiesVisibleForGuest) {
                    ShowAdvertisement();
                } else if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            } else {
                if (AppUtility.IsAdvertiesVisible) {
                    ShowAdvertisement();
                }
            }

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "OnCreate", e);
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

    private void ShowAdvertisement() {
        try {
//            InterstitialAdvertiesClass interstitialAdvertiesClass = new InterstitialAdvertiesClass(getContext());
//            interstitialAdvertiesClass.showInterstitialAdver();

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), parentLayout, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }



    private void NetworkCallForGetQuery() {
        try {
            CreateJSonForGetQuery();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.GetQuery(SendQueryJSONString, AppUtility.GET_QUERY);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "NetworkCallForGetQuery", e);
            pc.showCatchException();
        }
    }

    private void CreateJSonForGetQuery() {
        try {
            SendQuery sendQuery = new SendQuery();
            Gson gson = new Gson();
            sendQuery.setUserName(AppUtility.KEY_USERNAME);
            sendQuery.setStudentCode(AppUtility.KEY_STUDENTCODE);
            sendQuery.setWhichList("student");
            String getQuery = gson.toJson(sendQuery);
            SendQueryJSONString = "[" + getQuery + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "CreateJSonForGetQuery", e);
            pc.showCatchException();
        }
    }

    private void NetworkCallForSendQuery() {
        try {
            CreateJSon();
            DataAccess dataAccess = new DataAccess(getContext(), this);
            dataAccess.SendQuery(SendQueryJSONString, AppUtility.SEND_QUERY_URL);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "NetworkCallForSendQuery", e);
            pc.showCatchException();
        }

    }

    private void CreateJSon() {
        try {
            SendQuery sendQuery = new SendQuery();
            Gson gson = new Gson();
            sendQuery.setUserName(AppUtility.KEY_USERNAME);
            sendQuery.setQueryTitle(title);
            sendQuery.setQueryMessage(message);
            sendQuery.setStudentCode(AppUtility.KEY_STUDENTCODE);
            String classResponseJSON = gson.toJson(sendQuery);
            SendQueryJSONString = "[" + classResponseJSON + "]";
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "CreateJSon", e);
            pc.showCatchException();
        }
    }

    private boolean CheckValidate() {
        if (title.equals("")) {
            Snackbar.make(parentLayout, "Enter Title", Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (message.equals("")) {
            Snackbar.make(parentLayout, "Enter Message", Snackbar.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        swipeRefreshLayout.setRefreshing(false);
        switch (requestCode) {
            case 640:
                try {
                    sendQueries = (List<SendQuery>) data;
                    if (sendQueries.get(0).getResultCode().equals("1")) {
                        Snackbar.make(parentLayout, "Message Send Sucessfully", Snackbar.LENGTH_SHORT).show();
                        NetworkCallForGetQuery();
                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",sendQueries.get(0).getResult().toString());
                    }
                } catch (Exception ex) {
                    Snackbar.make(parentLayout, "Message:" + ex, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case 660:
                try {
                    sendQueries = (List<SendQuery>) data;
                    if (sendQueries.get(0).getResultCode().equals("1")) {
                        adapter = new MessageAdapterForGuest(getContext(), recyclerViewForMessage, sendQueries);
                        recyclerViewForMessage.setAdapter(adapter);

                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",sendQueries.get(0).getResult().toString());
                    }

                } catch (Exception ex) {
                    Snackbar.make(parentLayout, "Message:" + ex, Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void noResponse(String error) {
        try {
            swipeRefreshLayout.setRefreshing(false);
            VollyResponse vollyResponse = new VollyResponse(parentLayout,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),parentLayout, "MessageActivityForGuest", "noResponse", e);
            pc.showCatchException();
        }
    }



}

