package com.mksmcqapplicationtest.TabStructure.Other;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PrintCatchException;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.ShowErrorPopUp;
import com.mksmcqapplicationtest.TabStructure.OtherOld.AdvertiesClassForFragment;
import com.mksmcqapplicationtest.VollyResponse;
import com.mksmcqapplicationtest.beans.References;

import com.mksmcqapplicationtest.ui.fragments.ui.adapter.ReferenceListAdapter;
import com.mksmcqapplicationtest.util.AppUtility;
import com.mksmcqapplicationtest.util.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class TabReferenceActivity extends Fragment implements ResponseListener {

    String ReferanceJSONString;
    List<References> ReferencesResponse;
    private RecyclerView recyclerView;
    ReferenceListAdapter referenceListAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_reference_activity, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Advertise();
        return view;
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getContext().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }


    @Override
    public void onResponse(Object data) {

    }

    @Override
    public void onResponseWithRequestCode(Object data, int requestCode) {
        switch (requestCode) {
            case 850:
                try {
                    ReferencesResponse = (List<References>) data;
                    List<References> AppInstall = new ArrayList<References>();
                    List<References> AppUnInstall = new ArrayList<References>();
                    if (ReferencesResponse.get(0).getResultCode().equals("1")) {
                        for (int i = 0; i < ReferencesResponse.size(); i++) {

                            if (!AppUtility.PackageName.equals(ReferencesResponse.get(i).getAppPackageName())) {
                                References references = new References();
                                references.setAppCode(ReferencesResponse.get(i).getAppCode());
                                references.setAppLogo(ReferencesResponse.get(i).getAppLogo());
                                references.setAppName(ReferencesResponse.get(i).getAppName());
                                references.setAppPackageName(ReferencesResponse.get(i).getAppPackageName());
                                references.setAppShortDetail(ReferencesResponse.get(i).getAppShortDetail());
                                references.setAppSequence(ReferencesResponse.get(i).getAppSequence());
                                boolean isAppInstalled = appInstalledOrNot(ReferencesResponse.get(i).getAppPackageName());
                                if (isAppInstalled) {
                                    //App is allreadyinstall
                                    AppInstall.add(references);
                                } else {
                                    //App is Notinstall
                                    AppUnInstall.add(references);
                                }
                            }
                        }

                        for (int j = 0; j < AppInstall.size(); j++) {
                            References referencesinstall = new References();
                            referencesinstall.setAppCode(AppInstall.get(j).getAppCode());
                            referencesinstall.setAppLogo(AppInstall.get(j).getAppLogo());
                            referencesinstall.setAppName(AppInstall.get(j).getAppName());
                            referencesinstall.setAppPackageName(AppInstall.get(j).getAppPackageName());
                            referencesinstall.setAppShortDetail(AppInstall.get(j).getAppShortDetail());
                            referencesinstall.setAppSequence(AppInstall.get(j).getAppSequence());
                            AppUnInstall.add(referencesinstall);
                        }

                        referenceListAdapter = new ReferenceListAdapter(getContext(), AppUnInstall);
                        recyclerView.setAdapter(referenceListAdapter);
                    } else if (ReferencesResponse.get(0).getResultCode().equals("0")) {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",ReferencesResponse.get(0).getResult().toString());
                    } else {
                        ShowErrorPopUp showErrorPopUp=new ShowErrorPopUp();
                        showErrorPopUp.ShowPopUp(getContext(),"Error",ReferencesResponse.get(0).getResult().toString());
                    }
                } catch (Exception ex) {
                    Snackbar.make(view, "Message:" + ex, Snackbar.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public void noResponse(String error) {
        try {

            VollyResponse vollyResponse = new VollyResponse(view,getContext(),getResources().getString(R.string.Error_Msg),error);
            vollyResponse.ShowResponse();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "ReferancesActivity", "noResponse", e);
            pc.showCatchException();
        }
    }

    private void Advertise() {
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
    }

    private void ShowAdvertisement() {
        try {

            AdvertiesClassForFragment advertiesClass = new AdvertiesClassForFragment(getContext(), view, R.id.adView);
            advertiesClass.ShowAdver();

            if (AppUtility.AboveIsAdvertiesVisible) {
                AdvertiesClassForFragment advertiesClass1 = new AdvertiesClassForFragment(getContext(), view, R.id.adView1);
                advertiesClass1.ShowAdver();
            }
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(getContext(),view, "TabHomeActivity", "ShowAdvertisement", e);
            pc.showCatchException();
        }
    }


}

