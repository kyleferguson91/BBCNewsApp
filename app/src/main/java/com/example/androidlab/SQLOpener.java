package com.example.androidlab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "TodoDB";
    protected final static int VERSION_NUM = 2;
    public final static String TABLE_NAME = "TodoList";
    public final static String todoText = "todoText";
    public final static String todoUrgency = "todoUrgency";
    public final static String COL_ID = "_id";

public SQLOpener(Context ctx) { super(ctx, DATABASE_NAME, null, VERSION_NUM);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( "CREATE TABLE "  + TABLE_NAME  + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + todoText + " TEXT, "
                + todoUrgency + " TEXT);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

