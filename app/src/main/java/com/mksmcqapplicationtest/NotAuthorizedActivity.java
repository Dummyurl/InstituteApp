package com.mksmcqapplicationtest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class NotAuthorizedActivity {

    public void ShowDailog(final Context context) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText("Warning!!");
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText("Access Denied!!!");
            final Button dialogButton = (Button) dialog.findViewById(R.id.Okbtn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            final Button CancledialogButton = (Button) dialog.findViewById(R.id.Canclebtn_dialog);
            CancledialogButton.setVisibility(View.GONE);
            CancledialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            Log.d("Exception", "" + e);
        }
    }
}
