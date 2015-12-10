package com.example.daisy.dailyapple.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.ArrayRes;
import android.util.Log;
import android.widget.Toast;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.database.MySQLiteHelper;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Daisy on 11/15/15.
 */
public class WordsListHolder {

    /**
     * ListName are also table names
     */
    public enum ListName {
        TESTING_LIST("testingList", R.array.testing_list, true),
        DAILY_LIST("daily2000", R.array.daily_2000, false),
        GRE_LIST("GREList", R.array.gre_list, false),
        //        // Custom list must be the last one
        CUSTOM_LIST("CustomList", 0, false);

        private int xmlResource;
        //TODO: hidden list should be hidden from navigation drawer
        private boolean isHidden;
        private String listName;

        ListName(String listName, int xmlResource, boolean isHidden) {
            this.listName = listName;
            this.xmlResource = xmlResource;
            this.isHidden = isHidden;
        }

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        public int getXmlResource() {
            return xmlResource;
        }

        public void setXmlResource(int xmlResource) {
            this.xmlResource = xmlResource;
        }

        public boolean isHidden() {
            return isHidden;
        }

        public void setIsHidden(boolean isHidden) {
            this.isHidden = isHidden;
        }

    }

    private Context context;
    public static Map<String, WordsEntry> testingList;
    public static Map<String, WordsEntry> testingListReview;
    public static Map<String, WordsEntry> daily100List;
    public static Map<String, WordsEntry> daily100ListReview;
    public static Map<String, WordsEntry> greListReview;
    public static Map<String, WordsEntry> greList;
    public static Map<String, WordsEntry> daily2000List;
    public static Map<String, WordsEntry> daily2000ListReview;

    public WordsListHolder(final Context context) {
        this.context = context;
    }

    public Map<String, WordsEntry> getList(final ListName listName, final
    boolean isReview) {
        if (isReview) {
            return getReviewList(listName);
        } else {
            return getLearningList(listName);
        }

    }

    private Map<String, WordsEntry> getReviewList(ListName listName) {
        Map<String, WordsEntry> list = getReviewListMemoryHolder(listName);
        if (list == null) {
            Log.d("Daisy", "Getting Review list: " + listName + " from db");
            WordsDAO wordsDAO = new WordsDAO(context, listName);
            list = wordsDAO.getAllWordsEntryForReviewActionAndReturnMap
                    ();
            setReviewListMemoryHolder(listName, list);
        }
        return list;
    }

    private Map<String, WordsEntry> getLearningList(ListName listName) {
        // TODO: why is list null every time?
        // When it is not null, need to update reviewList containt to be consistant with learning progress
        Map<String, WordsEntry> list = getLearningListMemoryHolder(listName);
        if (list == null) {
            Log.d("Daisy", "Getting learning list: " + listName + " from db");
        }
        if (list != null) {
            return list;
        }
        Resources resources = context.getResources();
        list = new ConcurrentHashMap<>();
        String[] words = resources.getStringArray(listName.getXmlResource());
        for (String word : words) {
            list.put(word, new WordsEntry(null, null, false, word, null, null));
        }
        refreshList(list, listName);
        setLearningListMemoryHolder(listName, list);
        return list;
    }

    /**
     * Retrieve from db if the WordsEntry is Learned.
     * Logic gets delegated to Learning Detail page to decide if we need to
     * retrive icon and personalHint
     *
     * @param map
     * @param listName
     */

    private void refreshList(Map<String, WordsEntry> map, ListName listName) {
        WordsDAO wordsDAO = new WordsDAO(context, listName);
        wordsDAO.getALLWordsEntryAndUpdateConcurrentMap(map);

    }

    /**
     * Getter for Review list Getting. list saved in memory using listName
     *
     * @param listName
     * @return
     */
    private Map<String, WordsEntry> getReviewListMemoryHolder(ListName listName) {
        switch (listName) {
            case TESTING_LIST:
                return testingListReview;
            case DAILY_LIST:
                return daily2000ListReview;
            case GRE_LIST:
                return greListReview;
            case CUSTOM_LIST:
            default:
                return null;
        }
    }

    /**
     * Getter for Learning list. Getting list saved in memory using listName
     *
     * @param listName
     * @return
     */
    private Map<String, WordsEntry> getLearningListMemoryHolder(ListName listName) {
        switch (listName) {
            case TESTING_LIST:
                return testingList;
            case DAILY_LIST:
                return daily2000List;
            case GRE_LIST:
                return greList;
            case CUSTOM_LIST:
                // TODO custom list
            default:
                return null;
        }
    }

    /**
     * Setter for Review list by listName
     *
     * @param listName ListName used in switch statement
     * @param map      Map to set
     */
    private void setReviewListMemoryHolder(ListName listName, Map<String, WordsEntry> map) {
        switch (listName) {

            case TESTING_LIST:
                testingListReview = map;
                break;
            case DAILY_LIST:
                daily2000ListReview = map;
                break;
            case GRE_LIST:
                greListReview = map;
                break;
            case CUSTOM_LIST:
                // TODO custom list
            default:
                break;
        }
    }

    /**
     * Setter for Learning list by listName
     *
     * @param listName ListName used in switch statement
     * @param map      Map to set
     */
    private void setLearningListMemoryHolder(ListName listName, Map<String, WordsEntry> map) {
        switch (listName) {
            case TESTING_LIST:
                testingList = map;
                break;
            case DAILY_LIST:
                daily2000List = map;
                break;
            case GRE_LIST:
                greList = map;
                break;
            case CUSTOM_LIST:
            default:
                break;
        }
    }
}
