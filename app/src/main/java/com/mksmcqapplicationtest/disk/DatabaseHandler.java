package com.mksmcqapplicationtest.disk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mksmcqapplicationtest.beans.Test;
import com.mksmcqapplicationtest.util.AppUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final String TAG = DatabaseHandler.class.getName();

    private static final String DATABASE_NAME = "androidoes.db";
    private static final int DATABASE_VERSION = 1;
    String data;
    private static final String TABLE_NAME = "oestable";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_SYNC = "sync";
    private static final String COLUMN_EXTRAS = "extra";
    private static final String COLUMN_ATTENDANCE = "attendance";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "( " +
                COLUMN_URL + " TEXT PRIMARY KEY, " +
                COLUMN_DATA + " TEXT, " +
                COLUMN_TIMESTAMP + " TEXT, " +
                COLUMN_EXTRAS + " TEXT, " +
                COLUMN_ATTENDANCE + " TEXT, " +
                COLUMN_SYNC + " INTEGER " +
                ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
    }

    public void insert(String url, String data, int sysnctatus) {
        //if present then update else insert
        SQLiteDatabase db = null;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_TIMESTAMP, new Date().toString());
        values.put(COLUMN_SYNC, sysnctatus);

        if (getData(url) != null) {
            update(url, values);
        } else {
            db.beginTransaction();
            db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        }
    }

    public void insertWithExtra(String url, String data, int sysnctatus, String extra) {

        SQLiteDatabase db = null;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_TIMESTAMP, new Date().toString());
        values.put(COLUMN_SYNC, sysnctatus);
        values.put(COLUMN_EXTRAS, extra);
        if (getData(url) != null) {
            update(url, values);
        } else {

            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    public void update(String url, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.update(TABLE_NAME, values, COLUMN_URL + " = ? ", new String[]{url});

        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public String getData(String url) {

        String GETDATA_QUERY = "SELECT " + COLUMN_DATA + " FROM " + TABLE_NAME + " WHERE " + COLUMN_URL + " = '" + url + "'";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY, null);
        if (cursor == null)
            return null;
        else {
            try {
                cursor.moveToFirst();
                String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                cursor.close();
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getStudentData(String url) {

        String GETDATA_QUERY = "SELECT " + COLUMN_DATA + " FROM " + TABLE_NAME + " WHERE " + COLUMN_URL + " = '" + url + "' ";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY, null);
        if (cursor == null)
            return null;
        else {
            try {
                cursor.moveToFirst();
                String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));

                cursor.close();
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Test> getSubmittedTests() {//TODO Submited Exam From Disk
        List<Test> tests = null;
        String GETDATA_QUERY = "SELECT " + COLUMN_DATA + "," + COLUMN_SYNC + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EXTRAS + " = '" + AppUtility.KEY_TEST_SUBMIT + "'";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY, null);
        if (cursor == null)
            return null;
        else {
            try {

                tests = new ArrayList<Test>();
                cursor.moveToFirst();
                do {

                    String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                    String syn = cursor.getString(cursor.getColumnIndex(COLUMN_SYNC));

                    tests.add(AppUtility.GSON.fromJson(data, Test.class));

                } while (cursor.moveToNext());
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tests;
    }

    public String[] getDataForSyncghronization() {

        String GETDATA_QUERY = "SELECT " + COLUMN_DATA + " FROM " + TABLE_NAME + " WHERE " + COLUMN_EXTRAS +
                " = '" + AppUtility.KEY_TEST_SUBMIT + "'" + " AND  " + COLUMN_SYNC + " = '" + "0 '";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY, null);
        String[] finalData = new String[cursor.getCount()];

        if (cursor == null)
            return null;
        else if (cursor.getCount() == 0) {
            db.close();
            return null;
        } else {
            try {
                int i = 0;
                cursor.moveToFirst();
                do {

                    data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                    finalData[i] = data;
                    i++;
                } while (cursor.moveToNext());
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return finalData;
    }

    public int getCountFor(String testId) {
        int attempt = 0;
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        String QUERY = "SELECT COUNT(*) as Count1 FROM " + TABLE_NAME + " WHERE " + COLUMN_URL + " LIKE '%*" + testId + "*%' AND " + COLUMN_EXTRAS + " = '" + AppUtility.KEY_TEST_SUBMIT + "'";
        cursor = db.rawQuery(QUERY, null);
        cursor.moveToFirst();
        String data1 = cursor.getString(cursor.getColumnIndex("Count1"));
        attempt = Integer.parseInt(data1);
        //attempt = cursor.getCount();
        cursor.close();
//        attempt = Value(data1);
        return attempt;
    }

    public void updatedataafterSynchronization(String Data) {
        SQLiteDatabase db = null;
        ContentValues values = new ContentValues();
        values.put(COLUMN_SYNC, 1);

        try {
            db = this.getWritableDatabase();
//         String query = " UPDATE " + TABLE_NAME + " SET " + COLUMN_SYNC + " = '"+" 1 '" + " WHERE " + COLUMN_DATA + "='" + Data + "'";
            String query = " UPDATE " + TABLE_NAME + " SET " + values + " WHERE " + COLUMN_DATA + "='" + Data + "'";
            db.execSQL(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void insertAttendance(String url, String data, String Attendance) {

        SQLiteDatabase db = null;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL, url);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_TIMESTAMP, new Date().toString());
        values.put(COLUMN_ATTENDANCE, Attendance);
        long rowInserted=-1;
        if (getData(url) != null) {
            db.beginTransaction();
             rowInserted= db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } else {
            db.beginTransaction();
             rowInserted= db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
        if(rowInserted == -1)
            Log.d("Insert","Fail");
        else
            Log.d("Insert","Sucessfully");

    }



    public String[] getAttendanceForSyncghronization() {

        String GETDATA_QUERY = "SELECT " + COLUMN_DATA + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ATTENDANCE +
                " = '" + "1'";
        Cursor cursor = null;
        SQLiteDatabase db = null;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(GETDATA_QUERY, null);
        String[] finalData = new String[cursor.getCount()];

        if (cursor == null){
            return null;
       } else if (cursor.getCount() == 0) {
            db.close();
            return null;
        } else {
            try {
                int i = 0;
                cursor.moveToFirst();
                do {

                    data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));
                    finalData[i] = data;
                    i++;
                } while (cursor.moveToNext());
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return finalData;
    }

    public void updateAttendanveAfterSynchronization(String Data) {
        SQLiteDatabase db = null;
        ContentValues values = new ContentValues();
        values.put(COLUMN_ATTENDANCE, 0);

        try {
            db = this.getWritableDatabase();
            String query = " UPDATE " + TABLE_NAME + " SET " + values + " WHERE " + COLUMN_DATA + "='" + Data + "'";
            db.execSQL(query);
            String DeleteQuery = " DELETE  FROM " + TABLE_NAME + " WHERE " + COLUMN_ATTENDANCE + " = '" + "0'";
            db.execSQL(DeleteQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

}
