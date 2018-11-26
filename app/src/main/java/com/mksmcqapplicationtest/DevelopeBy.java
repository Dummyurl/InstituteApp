package com.mksmcqapplicationtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mksmcqapplicationtest.View.CustomTextView;
import com.mksmcqapplicationtest.View.CustomTextViewBold;

public class DevelopeBy extends AppCompatActivity {
    Context context;
    View parentLayout;
    TextView txtDevelopeBy, txtContact, txtContactUs, txtVisiteOurWebSite, txtPrivacyPolicy;
    LinearLayout linearContact, linearContactUs, linearVisiteOurWebSite, linearPrivacyPolicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_developby);
        try {
            setUI();
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(this, parentLayout, "Develop By", "OnCreate", e);
            pc.showCatchException();
        }
    }
    private void setUI() {
        parentLayout = findViewById(android.R.id.content);
        linearContact = (LinearLayout) findViewById(R.id.linearContact);
        linearContactUs = (LinearLayout) findViewById(R.id.linearContactUs);
        linearVisiteOurWebSite = (LinearLayout) findViewById(R.id.linearVisiteOurWebSite);
        linearPrivacyPolicy = (LinearLayout) findViewById(R.id.linearPrivacyPolicy);
        txtDevelopeBy = (TextView) findViewById(R.id.txtDevelopeBy);
        txtContact = (TextView) findViewById(R.id.txtContact);
        txtContactUs = (TextView) findViewById(R.id.txtContactUs);
        txtVisiteOurWebSite = (TextView) findViewById(R.id.txtVisiteOurWebSite);
        txtPrivacyPolicy = (TextView) findViewById(R.id.txtPrivacyPolicy);
        CustomTextViewBold txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.Develop_by_title));
        ImageButton imagviewbackpress = (ImageButton) findViewById(R.id.imagviewbackpress);
        imagviewbackpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txtDevelopeBy.setText(getResources().getString(R.string.About_MKS));
        txtContact.setText(getResources().getString(R.string.Contact));
        txtContactUs.setText(getResources().getString(R.string.Contact_Us));
        txtVisiteOurWebSite.setText(getResources().getString(R.string.Visit_web_site));
        txtPrivacyPolicy.setText(getResources().getString(R.string.Privacy_policy));
    }
}
