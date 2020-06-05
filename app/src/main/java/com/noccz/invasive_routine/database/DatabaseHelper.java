package com.noccz.invasive_routine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.noccz.invasive_routine.database.DatabaseConstants.DATABASE_NAME;
import static com.noccz.invasive_routine.database.DatabaseConstants.TABLE_NAME;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_CONTENT;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_ID;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_IS_COMPLETED;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_TIME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK_CONTENT + " TEXT, " +
            TASK_TIME + " TEXT NOT NULL, " +
            TASK_IS_COMPLETED + " INTEGER NOT NULL" +
            ");";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.setVersion(newVersion);
        onCreate(db);
    }
}
