package com.noccz.invasive_routine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.noccz.invasive_routine.task.TaskItem;

import static com.noccz.invasive_routine.database.DatabaseConstants.TABLE_NAME;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_CONTENT;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_ID;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_IS_COMPLETED;
import static com.noccz.invasive_routine.database.DatabaseConstants.TASK_TIME;

/**
 * References:
 * https://www.journaldev.com/9438/android-sqlite-database-example-tutorial
 * https://developer.android.com/training/data-storage/sqlite
 **/
public class DatabaseManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public DatabaseManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(TaskItem item) {
        database.insert(TABLE_NAME, null, getTaskValues(item));
    }

    public void update(TaskItem item) {
        database.update(TABLE_NAME, getTaskValues(item), TASK_ID + "=" + item.getId(), null);
    }

    public void delete(int id) {
        database.delete(TABLE_NAME, TASK_ID + "=" + id, null);
    }

    public Cursor fetch() {
        String[] columns = new String[] { TASK_ID, TASK_CONTENT, TASK_TIME, TASK_IS_COMPLETED };
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    private ContentValues getTaskValues(TaskItem item) {
        ContentValues values = new ContentValues();
        values.put(TASK_CONTENT, item.getContent());
        values.put(TASK_TIME, item.getTime());
        values.put(TASK_IS_COMPLETED, item.isCompleted() ? 1 : 0);
        return values;
    }
}
