package com.mksmcqapplicationtest.ui.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksmcqapplicationtest.PDFTools;
import com.mksmcqapplicationtest.R;
import com.mksmcqapplicationtest.util.AppUtility;


public class PDFDataFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    View view;
    TextView txtPDFName;
    Typeface font1;

    public PDFDataFragment() {
    }


    String googleDocsUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pdf_data, container, false);
        txtPDFName = (TextView) view.findViewById(R.id.pdfName);
        font1 = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        txtPDFName.setTypeface(font1);
        txtPDFName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleDocsUrl = "http://docs.google.com/viewer?url=";
                String url = AppUtility.baseUrl + "UploadedData/" + AppUtility.PDFPath;
                PDFTools pdfTools=new PDFTools();
                pdfTools.showPDFUrl(getContext(),url);

            }
        });

        return view;
    }
}
