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


    public enum Column {
        COLUMN_IS_LEARNED("isLearned"),
        COLUMN_PERSONAL_HINT("personalHint"),
        COLUMN_IMAGE_HINT("imageHint"),
        COLUMN_WORD("word"),
        COLUMN_MP3("mp3"),
        COLUMN_TRANSLATION("translation");

        private String val;

        Column(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }


    private static final String DATABASE_NAME = "dailyApple.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table %s (" + Column.COLUMN_WORD.getVal()
            + " text primary key, " + Column.COLUMN_PERSONAL_HINT.getVal() + " text, " +
            "" + Column.COLUMN_IMAGE_HINT.getVal() + " text, " + Column.COLUMN_IS_LEARNED.getVal()
            + " integer, " + Column.COLUMN_MP3.getVal() + " text, " + Column.COLUMN_TRANSLATION.getVal() + " text);";
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
