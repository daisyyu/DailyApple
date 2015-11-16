package com.example.daisy.dailyapple.learn;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import com.example.daisy.dailyapple.R;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Daisy on 11/15/15.
 */
public class WordsListHolder {

    public static enum ListName {
        TESTING_LIST("testingList");

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
    static Map<String, WordsEntry> testingList;

    public WordsListHolder(final Context context) {
        this.context = context;
    }

    public Map<String, WordsEntry> getList(final ListName listName) {
        Map<String, WordsEntry> map = null;
        switch (listName) {
            case TESTING_LIST:
                map = getTestingList();
                refreshList(map, listName);
            default:
        }
        return map;
    }

    private Map<String, WordsEntry> getTestingList() {
        if (testingList != null) {
            return testingList;
        }
        Resources resources = context.getResources();
        testingList = new Hashtable<>();
        String[] words = resources.getStringArray(R.array.testing_list);
        for (String word : words) {
            testingList.put(word, new WordsEntry(null, null, false));
        }
        return testingList;
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
                sharedPref = context.getSharedPreferences(context
                        .getString(R.string
                                .preference_file_key_is_learned_testing_list)
                        , Context.MODE_PRIVATE);
            default:
        }
        if (sharedPref == null) {
            Log.d("Daisy", listName.getListName() + " is null");
            return;
        }
        for (Map.Entry<String, WordsEntry> item : map.entrySet()) {
            boolean isLearned = sharedPref.getBoolean(item.getKey(), false);
            item.getValue().setIsLearned(isLearned);
        }
    }
}
