package com.mksmcqapplicationtest;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;



public class PrintCatchException {

    View parentLayout;
    String FileName, MethodName;
    Exception Exeption;
    Context context;


    public PrintCatchException(Context context, View parentLayout, String FileName, String MethodName, Exception Exeption) {
        this.context = context;
        this.parentLayout = parentLayout;
        this.FileName = FileName;
        this.MethodName = MethodName;
        this.Exeption = Exeption;
    }

    public void showCatchException() {
//        Snackbar.make(parentLayout, "File Name: " + FileName + ", Method Name : " + MethodName + ", Exception : " + Exeption,
//                Snackbar.LENGTH_LONG).show();
        String Msg = "" + Exeption;
        ShowPopUp(context, context.getResources().getString(R.string.Exception_Msg), Msg);
    }

    public void ShowPopUp(final Context context, String Title, String Message) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_exception);
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
