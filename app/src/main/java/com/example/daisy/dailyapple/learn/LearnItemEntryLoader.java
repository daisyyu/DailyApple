package com.example.daisy.dailyapple.learn;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.DAO.WordsListHolder;

import java.util.Map;

/**
 * Created by Daisy on 10/6/15.
 */
public class LearnItemEntryLoader extends AsyncTaskLoader<Map<String, WordsEntry>> {
    //    private ProgressDialog dialog;
    private Context context;
    private WordsListHolder.ListName listName;
    private boolean isReview;


    public LearnItemEntryLoader(Context context, final WordsListHolder.ListName
            listName, final boolean isReview) {
        super(context);
        this.isReview = isReview;
        this.context = context;
        this.listName = listName;
    }

    @Override
    protected void onStartLoading() {
        Log.d("Daisy", "onStratLoading LearnItemEntryLoader");
//        dialog = ProgressDialog.show(context, "", "Please wait...");
        forceLoad();
    }

    @Override
    public Map<String, WordsEntry> loadInBackground() {
        Log.d("Daisy", "loadInBackground LearnItemEntryLoader");
        return getWordsEntryMap(context);

    }


    private Map<String, WordsEntry> getWordsEntryMap(final Context context) {
        Map<String, WordsEntry> map;
        WordsListHolder listHolder = new WordsListHolder(context);
        map = listHolder.getList(listName,isReview);
        return map;
    }

    @Override
    public void deliverResult(Map<String, WordsEntry> data) {

        Log.d("Daisy", "LearnItemEntryLoader deliverResult");
//            if (dialog.isShowing()) {
//                Log.d("Daisy", "Diglog is showing Daisy");
//                dialog.cancel();
//            }
        super.deliverResult(data);


    }
}
