package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class VollyResponse {

    String ErrorResponse;
    Boolean isFound;
    Context context;
    String Title;
    String MessageResponse="Something went wrong try again later";

    public VollyResponse(View view,Context context,String Title, String error) {
        this.ErrorResponse = error;
        this.context=context;
        this.Title=Title;
    }

    public void ShowResponse() {
        int responsetype = 0;
        boolean isFound = ErrorResponse.contains("com.android.volley.NoConnectionError");
        if (isFound == true) {
            responsetype = 1;
        } else {
            if (ErrorResponse.equals("com.android.volley.ServerError")) {
                responsetype = 2;
            } else if (ErrorResponse.equals("com.android.volley.TimeOutError")) {
                responsetype = 3;
            } else {
                responsetype = 0;
            }
        }
       MessageResponse= MessageResponse+" :" + responsetype;
        ShowPopUp(context,Title,MessageResponse);
    }

    public void ShowPopUp(final Context context, String Title, String Message){
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_warning);
            TextView txtTitle = (TextView) dialog.findViewById(R.id.text_dialog);
            txtTitle.setText(Title);
            TextView txtMessage = (TextView) dialog.findViewById(R.id.text_dialog2);
            txtMessage.setText(Message);
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
