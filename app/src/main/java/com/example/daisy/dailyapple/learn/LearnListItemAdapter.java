package com.example.daisy.dailyapple.learn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.R;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Daisy on 10/5/15.
 */
public class LearnListItemAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    Map<String, WordsEntry> data = null;
    String[] keyArray;
    boolean isReview;

    public LearnListItemAdapter(Context context, int layoutResourceId, final boolean isReview) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.isReview = isReview;
    }

    public void setData(Map<String, WordsEntry> wordsEntries, String[]
            keyArray) {
        Log.d("Daisy", "LearnListItemAdapter setData start");
        if (wordsEntries == null) {
            return;
        }
        addAll(Arrays.asList(keyArray));
        this.data = wordsEntries;
        this.keyArray = keyArray;
        Log.d("Daisy", "LearnListItemAdapter setData done");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (data == null) {
            return ((Activity) context).getLayoutInflater().inflate(layoutResourceId, parent, false);
        }
        View row = convertView;
        WordsHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new WordsHolder();
            holder.wordsTextView = (TextView) row.findViewById(R.id.words);
            holder.isLearnedTextView = (TextView) row.findViewById(R.id.isLearned);
            row.setTag(holder);
        } else {
            holder = (WordsHolder) row.getTag();
        }
        WordsEntry entry = data.get(keyArray[position]);
        holder.wordsTextView.setText(keyArray[position]);
        String wordsTextDisplayableText;
        if (isReview) {
//            wordsTextDisplayableText = "Review it!";
            wordsTextDisplayableText = entry.getTranslation();
            row.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
            holder.isLearnedTextView.setGravity(Gravity.START);
        } else {
            if (entry.isLearned()) {
//                wordsTextDisplayableText = "You already leanred it";
                wordsTextDisplayableText = entry.getTranslation();
                row.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
                holder.isLearnedTextView.setGravity(Gravity.START);
            } else {
                // Not learned
                wordsTextDisplayableText = "Lean it now";
                row.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                holder.isLearnedTextView.setGravity(Gravity.END);
            }
        }
        holder.isLearnedTextView.setText(wordsTextDisplayableText);
        return row;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
//        Log.v("Daisy","getCount size is "+data.size());
        return data.size();
    }

    static class WordsHolder {
        private TextView wordsTextView;
        private TextView isLearnedTextView;
    }
}
