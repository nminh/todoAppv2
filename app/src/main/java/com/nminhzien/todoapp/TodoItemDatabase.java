package com.nminhzien.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nminh on 5/30/17.
 */

public class TodoItemDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TODO_APP_DATABASE";
    private static final String TABLE_PACKAGES = "TASK";

    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "NAME";

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PACKAGES_TABLE = "CREATE TABLE " + TABLE_PACKAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_PACKAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);
        onCreate(db);
    }

    public void addTask(int id, String task) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task);
        values.put(KEY_ID,id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PACKAGES, null, values);
        db.close();
    }

    public ArrayList<String> getAllTasks() {
        String selectQuery = "SELECT * FROM " + TABLE_PACKAGES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                tasks.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public void updateTaskName(int id,String new_taskname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME , new_taskname);
        String msg="";
        int ret=db.update(TABLE_PACKAGES, values,
                "ID=?", new String[]{Integer.toString(id)});
    }

    public void doDeleteaTask(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PACKAGES,
                "ID=?",
                new String[]{Integer.toString(id)});
        Log.d("MinhNguyen: ", "id"+ Integer.toString(id));
    }

    public void refreshAllTasks(ArrayList<String> tasks) {
        deleteAllTasks();
        addAllTasks(tasks);
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_PACKAGES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void addAllTasks(ArrayList<String> tasks) {
        int i =0;
        for (String task : tasks) {
            addTask(i,tasks.get(i));
            i++;
        }
    }
}
