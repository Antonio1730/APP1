package com.example.fs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fs.data.LoginDataSource;


public class Databasehelper extends SQLiteOpenHelper {

    private static final String TAG = "Databasehelper";

    private static final String TABLE_NAME = "Decisions";
    private static final String DRAWITEMTABLENAME = "Items";
    private static final String Col1 = "Id";
    private static final String Col2 = "name";
    private static final String Col3 = "repeteyn";
    private static final String Col11 = "drawitemid";
    private static final String Col22 = "drawitem";
    private static final String Col33 = "decisiontemid";

    public Databasehelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProjectTable = " CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col2 + " TEXT," + Col3+ " TEXT)";
        db.execSQL(createProjectTable);
        String createDrawItemTable = "CREATE TABLE " + DRAWITEMTABLENAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col22 + "TEXT," + Col33 + "TEXT)";
        db.execSQL(createDrawItemTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean adddata(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, item);

        Log.d(TAG, "addData: Adding " + item + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getitemID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + Col1 + " FROM " + TABLE_NAME + " WHERE " + Col2 + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public void deleteitem(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE  FROM " + TABLE_NAME + " WHERE " + Col1 + "= '" + id + "'"+" AND "+Col2+"='"+name+"'";
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, "deleteitem: query: "+query);
        Log.d(TAG, "deleteitem: "+name);
        db.execSQL(query);

    }

    public Cursor edititem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE * FROM " + TABLE_NAME + " WHERE " + Col2 + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;


    }
    public boolean addDrawItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col22, item);

        Log.d(TAG, "addData: Adding " + item + "to" + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}

