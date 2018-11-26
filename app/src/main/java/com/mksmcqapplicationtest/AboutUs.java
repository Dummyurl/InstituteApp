package com.mksmcqapplicationtest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mksmcqapplicationtest.View.CustomTextViewBold;

public class AboutUs extends AppCompatActivity {

    String  AboutUsString = "";
    String versionName = "";
    View parentLayout;
    TextView txtVersion, txtRateUs, txtContact, txtVisiteOurWebSite, txtPrivacyPolicy;
    LinearLayout linearVersion, linearRateUs, linearContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_aboutus);
        try {
            setUI();

        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(this, parentLayout, "AboutUs", "OnCreate", e);
            pc.showCatchException();
        }
    }

    private void setUI() {
        parentLayout = findViewById(android.R.id.content);
        linearContact = (LinearLayout) findViewById(R.id.linearContact);
        linearVersion = (LinearLayout) findViewById(R.id.linearVersion);
        linearRateUs = (LinearLayout) findViewById(R.id.linearRateUs);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtContact = (TextView) findViewById(R.id.txtContact);
        txtRateUs = (TextView) findViewById(R.id.txtRateUs);

        CustomTextViewBold txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
        txtactionbartitle.setText(getResources().getString(R.string.About_us_title));
        ImageButton imagviewbackpress = (ImageButton) findViewById(R.id.imagviewbackpress);
        imagviewbackpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            PrintCatchException pc = new PrintCatchException(AboutUs.this,parentLayout, "AboutUs", "OnCreate", e);
            pc.showCatchException();
        }
        AboutUsString = getResources().getString(R.string.app_name);
        txtVersion.setText("Version:" + versionName);
        txtContact.setText(getResources().getString(R.string.Contact));
        txtRateUs.setText(getResources().getString(R.string.rate_us));
    }
}
