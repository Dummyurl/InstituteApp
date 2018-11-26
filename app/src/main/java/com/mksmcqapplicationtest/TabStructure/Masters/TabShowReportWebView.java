package com.mksmcqapplicationtest.TabStructure.Masters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mksmcqapplicationtest.R;

public class TabShowReportWebView extends Fragment  {

    View view;
    WebView WebView;
    String url;
//    http://192.168.1.107/SolatBiologyClasses/MCQReportSelection.aspx
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_show_report_web_view, container, false);
        WebView=(WebView)view.findViewById(R.id.WebView);
        url="http://192.168.1.107/SolatBiologyClasses/RDLCReportViwer.aspx?Report=Student%20Report&ClassCode=1&All=True&TakeGuest=True";
        WebView.setWebViewClient(new MyBrowser());
        WebSettings webSettings=WebView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        WebView.loadUrl(url);
        return view;
    }

    private  class MyBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }


}

