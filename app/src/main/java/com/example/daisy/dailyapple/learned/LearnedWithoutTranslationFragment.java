package com.example.daisy.dailyapple.learned;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.learn.LearningWithoutTranslationFragment;
import com.example.daisy.dailyapple.learn.WordsEntry;
import com.example.daisy.dailyapple.learn.WordsListHolder;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;
import imageHint.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWithoutTranslationFragment extends Fragment implements SearchQueryChangeListener {

    private WordsEntry wordsEntry;
    private TextView personalHint;
    private ImageLoader imageLoader;
    private static final String ARG_PARAM1 = "param1";

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    private String queryString;
    private ImageView imageHint;

    public LearnedWithoutTranslationFragment() {
    }

    public static LearnedWithoutTranslationFragment newInstance(final String
                                                                        queryString) {
        LearnedWithoutTranslationFragment fragment = new LearnedWithoutTranslationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, queryString);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryString = getArguments().getString(ARG_PARAM1);
        }
        imageLoader = new ImageLoader(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Daisy", "LearnedWithoutTranslationFragment onCreateView");
        View rootView = inflater.inflate(R.layout
                .fragment_learned_without_translation, container, false);
        personalHint = (TextView) rootView.findViewById(R.id.personalHint);
        imageHint = (ImageView) rootView.findViewById(R.id.imgIcon);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateViewOnQueryChange(queryString);
    }

    public WordsEntry getWordsEntry() {
        return wordsEntry;
    }

    public void setWordsEntry(WordsEntry wordsEntry) {
        this.wordsEntry = wordsEntry;
    }

    @Override
    public void updateViewOnQueryChange(String queryString) {
        Log.d("Daisy", "LearnedWithoutTranslationFragment updateViewOnQueryChange");
        if (queryString == null) {
            Log.d("Daisy", "LearnedWithoutTranslationFragment queryString is " +
                    "null,not updating view");
            return;
        }
        wordsEntry = WordsListHolder.testingList.get(queryString);
        personalHint.setText(wordsEntry.getPersonalHint());
        imageLoader.DisplayImage(wordsEntry.getIconHint(), imageHint);
    }
}
