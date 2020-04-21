package com.example.fs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


import com.example.fs.data.LoginDataSource;


public class Databasehelper extends SQLiteOpenHelper {

    /** private static final String TABLE_NAME = "Decisions";
    private static final String TAG = "Databasehelper";

    private static final String TABLE_NAME = "Decisions";
    private static final String DRAWITEMTABLENAME = "Items";
    private static final String SUPPORT_TABLE= "Help";
    private static final String Col1 = "Id";
    private static final String Col2 = "name";
    private static final String Col3 = "repeteyn";
    private static final String Col11 = "drawitemid";
    private static final String Col22 = "drawitem";
    private static final String Col111 = "Id";
    private static final String Col222 = "drawitemid";**/

    public static String DATABASE_NAME ="Resolverdb";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**String createProjectTable = " CREATE TABLE " + TABLE_NAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col2 + " TEXT," + Col3+ " TEXT)";
        String createDrawItemTable = "CREATE TABLE " + DRAWITEMTABLENAME + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col22 + "TEXT)";
        String createSupportTable = "CREATE TABLE " + SUPPORT_TABLE + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Col222+ "TEXT)";**/

        db.execSQL(DataContract.createItemTable);
        db.execSQL(DataContract.createProjectTable);
        db.execSQL(DataContract.createSupportTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DataContract.deleteItemTable);
        db.execSQL(DataContract.deleteProjectTable);
        db.execSQL(DataContract.deleteSupportTable);

        onCreate(db);

    }

    public boolean adddata(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Decisions.CONTENT_COLUMN, item);

        Log.d(DataContract.TAG, "addData: Adding " + item + "to" + DataContract.Decisions.TABLE_NAME);
        long result = db.insert(DataContract.Decisions.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getDecisions() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DataContract.Decisions.TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDecisionID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + DataContract.Decisions._ID + " FROM " + DataContract.Decisions.TABLE_NAME + " WHERE " + DataContract.Decisions.CONTENT_COLUMN + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public void deleteDecision(int id, String name) {
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
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(Col22, item);

        Log.d(TAG, "addData: Adding " + item + "to" + DRAWITEMTABLENAME);
        long result = db.insert(DRAWITEMTABLENAME, null, contentValues2);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}

