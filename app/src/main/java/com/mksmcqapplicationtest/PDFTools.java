package com.mksmcqapplicationtest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.mksmcqapplicationtest.util.AppUtility;

import java.io.File;


public class PDFTools {
    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?url=";
    private static final String PDF_MIME_TYPE = "application/pdf";
    private static final String HTML_MIME_TYPE = "text/html";

    public static void showPDFUrl(final Context context, final String pdfUrl) {
        if (isPDFSupported(context)) {
            downloadAndOpenPDF(context, pdfUrl);
        } else {
            askToOpenPDFThroughGoogleDrive(context, pdfUrl);
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void downloadAndOpenPDF(final Context context, final String pdfUrl) {        // Get filename
        final String filename = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);

        final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename);
        if (tempFile.exists()) {

            openPDF(context, Uri.fromFile(tempFile));
            return;
        }


        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(pdfUrl));
        r.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, filename);
        final DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        BroadcastReceiver onComplete = new BroadcastReceiver() {



            @Override
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);

                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));

                if (c.moveToFirst()) {
                    int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        openPDF(context, Uri.fromFile(tempFile));
                    } else if (status == DownloadManager.STATUS_FAILED) {
                        new AlertDialog.Builder(context)
                                .setTitle("Message")
                                .setMessage("Failed to download this pdf")
                                .setIcon(R.mipmap.ic_launcher_logo_c)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();

                    } else {
                    }

                }
                c.close();
            }
        };
        context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        dm.enqueue(r);
    }

    public static void askToOpenPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        new AlertDialog.Builder(context)
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGoogleDrive(context, pdfUrl);
                    }
                })
                .show();
    }

    public static void openPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse(GOOGLE_DRIVE_PDF_READER_PREFIX + pdfUrl), HTML_MIME_TYPE);
        context.startActivity(i);
    }

    public static final void openPDF(Context context, Uri localUri) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(localUri, PDF_MIME_TYPE);
        context.startActivity(i);
//        ((Activity) context).finish();


    }

    public static boolean isPDFSupported(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        final File tempFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "test.pdf");
        i.setDataAndType(Uri.fromFile(tempFile), PDF_MIME_TYPE);
        return context.getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

}