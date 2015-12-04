package com.example.daisy.dailyapple.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.util.Log;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.database.MySQLiteHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Daisy on 11/15/15.
 */
public class WordsListHolder {

    public enum ListName {
        TESTING_LIST("testingList"),
        DAILY_LIST("daily100"),
        GRE_LIST("GREList"),
        // Custom list must be the last one
        CUSTOM_LIST("CustomList");

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        private String listName;


        ListName(String listName) {
            this.listName = listName;
        }

    }

    private Context context;
    public static Map<String, WordsEntry> testingList;
    public static Map<String, WordsEntry> testingListReview;

    public WordsListHolder(final Context context) {
        this.context = context;
    }

    public Map<String, WordsEntry> getList(final ListName listName, final
    boolean isReview) {
        Map<String, WordsEntry> map = null;
        switch (listName) {
            case TESTING_LIST:
                map = getTestingList(isReview);
                break;
            default:
                break;
        }
        return map;
    }


    private Map<String, WordsEntry> getTestingList(final boolean isReview) {


        if (!isReview) {
            if (testingList != null) {
                return testingList;
            }
            Resources resources = context.getResources();
            testingList = new ConcurrentHashMap<>();
            String[] words = resources.getStringArray(R.array.testing_list);
            for (String word : words) {
                testingList.put(word, new WordsEntry(null, null, false, word,null,null));
            }
            refreshList(testingList, ListName.TESTING_LIST);
            return testingList;
        } else {
            // Review action, return everything from database to form
            // hashmap, no concurent write will be considered.
            WordsDAO wordsDAO = new WordsDAO(context, ListName.TESTING_LIST);
            return wordsDAO.getAllWordsEntryForReviewActionAndReturnMap
                    ();
        }
    }

    /**
     * Retrieve from sharedPref and tag the WordsEntry with isLearned.
     * Logic gets delegated to Learning Detail page to decide if we need to
     * retrive icon and personalHint
     *
     * @param map
     * @param listName
     */

    private void refreshList(Map<String, WordsEntry> map, ListName listName) {
        SharedPreferences sharedPref = null;
        switch (listName) {
            case TESTING_LIST:
                WordsDAO wordsDAO = new WordsDAO(context, listName);
                wordsDAO.getALLWordsEntryAndUpdateConcurrentMap(map);
            default:
        }
    }
}
