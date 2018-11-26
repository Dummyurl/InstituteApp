package com.mksmcqapplicationtest.TabStructure.Other;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mksmcqapplicationtest.InternetConnectionCheck;
import com.mksmcqapplicationtest.PDFTools;
import com.mksmcqapplicationtest.R;

import com.mksmcqapplicationtest.TabStructure.Exam.TabExamActivity;
import com.mksmcqapplicationtest.util.AppUtility;

public class TabUserManual extends Fragment {

    View view;
    String url;
    TextView txtPDFName;
    Typeface font1;
    String googleDocsUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_user_manual, container, false);
        try {

            if ((AppUtility.IsTeacher.equals("A")) || (AppUtility.IsTeacher.equals("T"))) {
                url = AppUtility.baseUrl + "TeacherUserManual.pdf";
            } else {
                url = AppUtility.baseUrl + "StudentUserManual.pdf";
            }

            txtPDFName = (TextView) view.findViewById(R.id.pdfName);
            font1 = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
            txtPDFName.setTypeface(font1);
            txtPDFName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InternetConnectionCheck internetConnectionCheck = new InternetConnectionCheck(getContext());
                    boolean availble = internetConnectionCheck.checkConnection();
                    if (availble) {
                        googleDocsUrl = "http://docs.google.com/viewer?url=";
                        PDFTools pdfTools=new PDFTools();
                        pdfTools.showPDFUrl(getContext(),url);
                    }else {

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


}

