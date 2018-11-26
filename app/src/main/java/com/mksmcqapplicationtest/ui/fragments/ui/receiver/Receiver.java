package com.mksmcqapplicationtest.ui.fragments.ui.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.mksmcqapplicationtest.LauncherActivity;
import com.mksmcqapplicationtest.R;

import java.util.Random;


public class Receiver extends BroadcastReceiver {
    PendingIntent pIntent;
    NotificationCompat.Builder notification;
    NotificationManager manager;
    Intent resultIntent;
    TaskStackBuilder stackBuilder;
    int notificationId = new Random().nextInt();
    int Count = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            showNotification(context);
            Toast.makeText(context, "UnFortunatly Time up", Toast.LENGTH_LONG).show();
            Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotification(Context context) {
        try {
            notification = new NotificationCompat.Builder(context);
            notification.setContentTitle("Learn2Crack Updates");
            notification.setContentText("New Post on Android Notification.");
            notification.setTicker("New Message Alert!");
            notification.setSmallIcon(R.drawable.ic_play_arrow);
            notification.setAutoCancel(true);
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(LauncherActivity.class);
            resultIntent = new Intent(context, LauncherActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pIntent);
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(notificationId, notification.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
