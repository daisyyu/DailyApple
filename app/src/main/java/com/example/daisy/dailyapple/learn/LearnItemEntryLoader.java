package com.example.daisy.dailyapple.learn;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.example.daisy.dailyapple.Exceptions.NetworkExceptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 10/6/15.
 */
public class LearnItemEntryLoader extends AsyncTaskLoader<Map<String, WordsEntry>> {
    private ProgressDialog dialog;
    private Context context;


    public LearnItemEntryLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        Log.d("Daisy", "onStratLoading LearnItemEntryLoader");
        dialog = ProgressDialog.show(context, "", "Please wait...");
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
        map = listHolder.getList(WordsListHolder.ListName.TESTING_LIST);
        return map;
    }

    @Override
    public void deliverResult(Map<String, WordsEntry> data) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        super.deliverResult(data);
    }
}
