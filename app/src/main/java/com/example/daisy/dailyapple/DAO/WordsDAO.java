package com.example.daisy.dailyapple.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.daisy.dailyapple.database.MySQLiteHelper;

import java.util.Map;

/**
 * Created by Daisy on 11/24/15.
 */
public class WordsDAO {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_WORD,
            MySQLiteHelper.COLUMN_IS_LEARNED, MySQLiteHelper
            .COLUMN_IMAGE_HINT, MySQLiteHelper.COLUMN_PERSONAL_HINT};
    private Context context;
    private MySQLiteHelper.TableNames tableNames;

    public WordsDAO(final Context context, final WordsListHolder.ListName listName) {
        this.context = context;
        tableNames = listName.getTableName();
        dbHelper = new MySQLiteHelper(context);
    }
    /**
     * Writing a words entry to db
     * @param wordsEntry
     */
    public void addWordsEntry(WordsEntry wordsEntry) {
        openForWrite();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_WORD, wordsEntry.getWord());
        contentValues.put(MySQLiteHelper.COLUMN_IMAGE_HINT, wordsEntry.getIconHint());
        int isLeanred = wordsEntry.isLearned() ? 1 : 0;
        contentValues.put(MySQLiteHelper.COLUMN_IS_LEARNED, isLeanred);
        contentValues.put(MySQLiteHelper.COLUMN_PERSONAL_HINT, wordsEntry.getPersonalHint());
        database.insert(tableNames.name(), null, contentValues);
    }

    public void getWordsEntry(String word) {
        openForRead();
        // TODO
    }

    public void getALLWordsEntryAndUpdateConcurrentMap(Map<String, WordsEntry> map) {
        String selectQuery = "SELECT  * FROM " + tableNames.name();
        openForRead();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(0);
                WordsEntry wordsEntry = map.get(word);
                if (wordsEntry == null) {
                    Log.d("Daisy", "WordsDAO " +
                            "getALLWordsEntryAndUpdateConcurrentMap words " + word + " doesn't exist in " +
                            "wordsList");
                    continue;
                }
                String imageHint = cursor.getString(2);
                String personalHint = cursor.getString(1);
                wordsEntry.setIconHint(imageHint);
                wordsEntry.setPersonalHint(personalHint);
                wordsEntry.setIsLearned(true);

            } while (cursor.moveToNext());
        }
    }

    private void openForRead() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    private void openForWrite() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }
}