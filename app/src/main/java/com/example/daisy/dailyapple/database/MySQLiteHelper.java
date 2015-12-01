package com.example.daisy.dailyapple.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daisy on 11/24/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static enum TableNames {
        TABLE_TESTING_LIST,TABLE_GRE_LIST,TABLE_DAILY_100,TABLE_CUSTOME;
    }

    public static final String TABLE_TESTING_LIST = "testingList";
    public static final String COLUMN_IS_LEARNED = "isLearned";
    public static final String COLUMN_PERSONAL_HINT = "personalHint";
    public static final String COLUMN_IMAGE_HINT = "imageHint";
    public static final String COLUMN_WORD = "word";

    private static final String DATABASE_NAME = "dailyApple.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table %s (" + COLUMN_WORD
            + " text primary key, " + COLUMN_PERSONAL_HINT + " text, " +
            "" + COLUMN_IMAGE_HINT + " text, " + COLUMN_IS_LEARNED
            + " integer);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS %s";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (TableNames tableName : TableNames.values()) {
            db.execSQL(String.format(DATABASE_CREATE, tableName.name()));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        for (TableNames tableName : TableNames.values()) {
            db.execSQL(String.format(DATABASE_DROP, tableName.name()));
        }
        onCreate(db);
    }
}
