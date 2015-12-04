package com.example.daisy.dailyapple.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.daisy.dailyapple.DAO.WordsListHolder;

/**
 * Created by Daisy on 11/24/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String COLUMN_IS_LEARNED = "isLearned";
    public static final String COLUMN_PERSONAL_HINT = "personalHint";
    public static final String COLUMN_IMAGE_HINT = "imageHint";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_MP3 = "mp3";
    public static final String COLUMN_TRANSLATION = "translation";


    private static final String DATABASE_NAME = "dailyApple.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table %s (" + COLUMN_WORD
            + " text primary key, " + COLUMN_PERSONAL_HINT + " text, " +
            "" + COLUMN_IMAGE_HINT + " text, " + COLUMN_IS_LEARNED
            + " integer, " + COLUMN_MP3 + " text, " + COLUMN_TRANSLATION + " text);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS %s";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (WordsListHolder.ListName listName : WordsListHolder.ListName.values()) {
            db.execSQL(String.format(DATABASE_CREATE, listName.name()));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        for (WordsListHolder.ListName listName : WordsListHolder.ListName.values()) {
            db.execSQL(String.format(DATABASE_DROP, listName.name()));
        }
        onCreate(db);
    }
}
