package com.example.daisy.dailyapple.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.daisy.dailyapple.database.MySQLiteHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Daisy on 11/24/15.
 */
public class WordsDAO {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    //    private String[] allColumns = {MySQLiteHelper.COLUMN_WORD,
//            MySQLiteHelper.COLUMN_IS_LEARNED, MySQLiteHelper
//            .COLUMN_IMAGE_HINT, MySQLiteHelper.COLUMN_PERSONAL_HINT};
    private Context context;
    private WordsListHolder.ListName listName;

    public WordsDAO(final Context context, final WordsListHolder.ListName listName) {
        this.context = context;
        this.listName = listName;
        dbHelper = new MySQLiteHelper(context);
    }

    /**
     * Writing a words entry to db
     *
     * @param wordsEntry
     */
    public void addWordsEntry(WordsEntry wordsEntry) {
        openForWrite();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.Column.COLUMN_WORD.getVal(), wordsEntry.getWord());
        contentValues.put(MySQLiteHelper.Column.COLUMN_IMAGE_HINT.getVal(), wordsEntry.getIconHint());
        int isLeanred = wordsEntry.isLearned() ? 1 : 0;
        contentValues.put(MySQLiteHelper.Column.COLUMN_IS_LEARNED.getVal(), isLeanred);
        contentValues.put(MySQLiteHelper.Column.COLUMN_PERSONAL_HINT.getVal(), wordsEntry.getPersonalHint());
        contentValues.put(MySQLiteHelper.Column.COLUMN_MP3.getVal(), wordsEntry.getPhoneticMP3Address());
        contentValues.put(MySQLiteHelper.Column.COLUMN_TRANSLATION.getVal(), wordsEntry.getTranslation());
        database.insert(listName.name(), null, contentValues);
    }

    public void getWordsEntry(String word) {
        openForRead();
        // TODO
    }

    public void updateWordsWithColumn(String words, MySQLiteHelper.Column columnName, Object value) {
        Log.d("Daisy", "executing updateWordsWithColumn");
        try {
            openForWrite();
            ContentValues contentValues = new ContentValues();
            switch (columnName) {
                case COLUMN_IS_LEARNED:
                    contentValues.put(columnName.getVal(), (int) value);
                    break;
                case COLUMN_PERSONAL_HINT:
                    contentValues.put(columnName.getVal(), (String) value);
                    break;
                case COLUMN_IMAGE_HINT:
                    contentValues.put(columnName.getVal(), (String) value);
                    break;
                case COLUMN_WORD:
                    contentValues.put(columnName.getVal(), (String) value);
                    break;
                case COLUMN_MP3:
                    contentValues.put(columnName.getVal(), (String) value);
                    break;
                case COLUMN_TRANSLATION:
                    contentValues.put(columnName.getVal(), (String) value);
                    break;
            }
            database.update(listName.name(), contentValues, "word=?", new String[]{words});
        } finally {
            dbHelper.close();
        }
    }

    public void getALLWordsEntryAndUpdateConcurrentMap(Map<String, WordsEntry> map) {
        String selectQuery = "SELECT  * FROM " + listName.name();
        Cursor cursor = null;
        try {
            openForRead();
            cursor = database.rawQuery(selectQuery, null);
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
                    //TODO: should retrieve column number via CONST
                    String imageHint = cursor.getString(2);
                    String personalHint = cursor.getString(1);
                    String mp3 = cursor.getString(4);
                    String translation = cursor.getString(5);
                    wordsEntry.setIconHint(imageHint);
                    wordsEntry.setPersonalHint(personalHint);
                    wordsEntry.setIsLearned(true);
                    wordsEntry.setPhoneticMP3Address(mp3);
                    wordsEntry.setTranslation(translation);

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            dbHelper.close();
        }
    }

    public Map<String, WordsEntry> getAllWordsEntryForReviewActionAndReturnMap() {
        String selectQuery = "SELECT  * FROM " + listName.name();
        Cursor cursor = null;
        Map<String, WordsEntry> map = new HashMap<>();
        try {
            openForRead();
            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String word = cursor.getString(0);
                    String imageHint = cursor.getString(2);
                    String personalHint = cursor.getString(1);
                    String mp3 = cursor.getString(4);
                    String translation = cursor.getString(5);
                    WordsEntry wordsEntry = new WordsEntry(imageHint,
                            personalHint, true, word, mp3, translation);
                    map.put(word, wordsEntry);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            dbHelper.close();
        }
        return map;
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
