package com.example.fs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class DataContract {

    private DataContract() {
    }
    public static final String TAG = "Databasehelper";

    public static class Decisions implements BaseColumns {
        public static final String TABLE_NAME = "Decisions";
        public static final String CONTENT_COLUMN = "projectname";
    }

    public static class DrawItems implements BaseColumns {
        public static final String TABLE_NAME = "DrawItems";
        public static final String DID_COLUMN = "Decision";
        public static final String CONTENT_COLUMN = "drawitemname";
    }

    public static class Support implements BaseColumns {
        public static final String TABLE_NAME = "Support";
        public static final String ID_COLUMN = "Id";
    }


    public static final String createProjectTable = " CREATE TABLE " + Decisions.TABLE_NAME + "(" + Decisions._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + Decisions.CONTENT_COLUMN +" TEXT)";
    public static final String createItemTable = " CREATE TABLE " + DrawItems.TABLE_NAME + "(" + DrawItems._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + DrawItems.CONTENT_COLUMN +" TEXT, " + DrawItems.DID_COLUMN +" TEXT)";
    public static final String createSupportTable = " CREATE TABLE " + Support.TABLE_NAME + "(" + Support._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + Support.ID_COLUMN +" TEXT)";

    public static final String deleteProjectTable = " DROP TABLE IF EXISTS " + Decisions.TABLE_NAME;
    public static final String deleteItemTable = " DROP TABLE IF EXISTS " + DrawItems.TABLE_NAME;
    public static final String deleteSupportTable = " DROP TABLE IF EXISTS " + Support.TABLE_NAME;


}

