package com.mksmcqapplicationtest.ui.fragments.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.mksmcqapplicationtest.NotificationResponseConnectedToNetwork;
import com.mksmcqapplicationtest.util.AppUtility;

public class NetworkChangeReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            String status = NetworkUtil.getConnectivityStatusString(context);
            Log.d("Status", status);
            if ((status.equals("Wifi enabled")) || (status.equals("Mobile data enabled"))) {
//                NotificationResponseConnectedToNetwork notificationResponseConnectedToNetwork = new NotificationResponseConnectedToNetwork(context);
//                notificationResponseConnectedToNetwork.NetWorkCallForGetNotification();

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private void check(Context context) {
//        if (notificationFlag != null) {
//            if (notificationFlag.equals("D")) {
//                if (AppUtility.NOTIFICATION_COUNT == "1") {
//                    CheckDaily(context);
//                    AppUtility.NOTIFICATION_COUNT = "0";
//                }
//            } else if (notificationFlag.equals("W")) {
//                if (AppUtility.NOTIFICATION_COUNT == "1") {
//                    CheckWeekly(context);
//                    AppUtility.NOTIFICATION_COUNT = "0";
//                }
//            } else if (notificationFlag.equals("M")) {
//                if (AppUtility.NOTIFICATION_COUNT == "1") {
//                    CheckMonthly(context);
//                    AppUtility.NOTIFICATION_COUNT = "0";
//                }
//            } else if (notificationFlag == null||notificationFlag.equals("null")) {
//                return;
//            }
//        }else {
//            return;
//        }
//    }
//
//    private void CheckDaily(Context context) {
//        Intent intent = new Intent(context, Receiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//    }
//
//    private void CheckWeekly(Context context) {
//        Intent intent = new Intent(context, Receiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//    }
//
//    private void CheckMonthly(Context context) {
//        Intent intent = new Intent(context, Receiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_DAY * 31, pendingIntent);
//    }

}
