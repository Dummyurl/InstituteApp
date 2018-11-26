package com.mksmcqapplicationtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mksmcqapplicationtest.View.CustomTextViewBold;

public class NotificationActivityInDetails extends AppCompatActivity {
    Bundle bundle;
    String NotificationTitle, NotificationText;
    TextView txtNotificationText,txtNotificationTitle;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_in_details);
        try{
            view = findViewById(android.R.id.content);
            txtNotificationText = (TextView) findViewById(R.id.txtNotificationText);
            txtNotificationTitle = (TextView) findViewById(R.id.txtNotificationTitle);
            bundle = getIntent().getExtras();
            if (bundle != null) {
                NotificationTitle = bundle.getString("NotificationTitle");
                NotificationText = bundle.getString("NotificationText");
            }
            txtNotificationText.setText(NotificationText);
            txtNotificationTitle.setText(NotificationTitle);
           CustomTextViewBold txtactionbartitle = (CustomTextViewBold) findViewById(R.id.txtactionbartitle);
           txtactionbartitle.setText(getResources().getString(R.string.Show_Notification));
           ImageButton imagviewbackpress = (ImageButton) findViewById(R.id.imagviewbackpress);
            ImageButton  imageadd = (ImageButton) findViewById(R.id.imageadd);
            imageadd.setVisibility(View.GONE);
            ImageButton  imageButtonLogout = (ImageButton) findViewById(R.id.imageButtonLogout);
            imageButtonLogout.setVisibility(View.GONE);
           imagviewbackpress.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onBackPressed();
               }
           });

        }catch(Exception e){
            PrintCatchException pc = new PrintCatchException(NotificationActivityInDetails.this,view, "NotificationActivityInDetails", "OnCreate", e);
            pc.showCatchException();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
