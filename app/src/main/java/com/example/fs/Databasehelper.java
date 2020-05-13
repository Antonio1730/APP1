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

    public static String DATABASE_NAME = "Resolverdb";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 130);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

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
    public boolean editdata(String item, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Decisions.CONTENT_COLUMN, item);

        Log.d(DataContract.TAG, "addData: Updating " + item + "at" + DataContract.Decisions.TABLE_NAME);
        long result = db.update(DataContract.Decisions.TABLE_NAME, contentValues, "_ID ="+id,null);

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

    public Cursor getDrawItems(String projectid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.DID_COLUMN + "= '" + projectid + "'";
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
        String query1 = "DELETE  FROM " + DataContract.Decisions.TABLE_NAME + " WHERE " + DataContract.Decisions._ID + "= '" + id + "'" + " AND " + DataContract.Decisions.CONTENT_COLUMN + "='" + name + "'";
        String query2 = "DELETE  FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.DID_COLUMN + "= '" + id + "'";
        Cursor data = db.rawQuery(query1, null);
        Cursor data2 = db.rawQuery(query2, null);
        Log.d(DataContract.TAG, "deleteitem: query: " + query1 + "," + query2);
        Log.d(DataContract.TAG, "deleteitem: " + name);
        db.execSQL(query1);
        db.execSQL(query2);

    }

    public Cursor randomchoice(String projectname) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.DID_COLUMN + "= '" + projectname + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public boolean addDrawItem(String newDrawItem, String projectname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DrawItems.CONTENT_COLUMN, newDrawItem);
        contentValues.put(DataContract.DrawItems.DID_COLUMN, projectname);

        Log.d(DataContract.TAG, "addData: Adding " + newDrawItem + projectname + "to" + DataContract.DrawItems.TABLE_NAME);
        long result = db.insert(DataContract.DrawItems.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor geteditableDrawItems(String oldname){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM "+DataContract.DrawItems.TABLE_NAME+" WHERE "+DataContract.DrawItems.DID_COLUMN+" ='"+oldname+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public boolean editProjectnameforDrawItems(String newname, Integer drawItemID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.DrawItems.DID_COLUMN, newname);

        Log.d(DataContract.TAG, "addData: Adding " + newname + "to" + DataContract.DrawItems.TABLE_NAME);
        long result = db.update(DataContract.DrawItems.TABLE_NAME, contentValues, "_ID = '"+drawItemID+"'",null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor checkunique(String newDecision){
        SQLiteDatabase db = this.getWritableDatabase();
        String Uquery = "SELECT * FROM " + DataContract.Decisions.TABLE_NAME + " WHERE " + DataContract.Decisions.CONTENT_COLUMN + "= '" + newDecision + "'";
        Cursor data = db.rawQuery(Uquery, null);
        return data;
    }
    public Cursor getFactor(String oldDrawitems){
        SQLiteDatabase db = this.getWritableDatabase();
        String Uquery = "SELECT * FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.CONTENT_COLUMN + "= '" + oldDrawitems + "'";
        Cursor data = db.rawQuery(Uquery, null);
        return data;
    }
    public void deleteDrawItems( String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE  FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.DID_COLUMN + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(DataContract.TAG, "deleteitem: query: " + query );
        Log.d(DataContract.TAG, "deleteitem: " + name);
        db.execSQL(query);

    }
    public void deleteSpecificDrawItem( String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE  FROM " + DataContract.DrawItems.TABLE_NAME + " WHERE " + DataContract.DrawItems.CONTENT_COLUMN + "= '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        Log.d(DataContract.TAG, "deletespecificitem: query: " + query);
        Log.d(DataContract.TAG, "deletespecificitem: " + name);
        db.execSQL(query);

    }
}

