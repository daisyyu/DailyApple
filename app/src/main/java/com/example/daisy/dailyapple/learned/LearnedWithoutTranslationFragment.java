package com.example.daisy.dailyapple.learned;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.learn.LearningActivity;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;
import imageHint.ImageLoader;

import java.util.zip.Adler32;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWithoutTranslationFragment extends Fragment implements SearchQueryChangeListener {

    private WordsEntry wordsEntry;
    private TextView personalHint;
    private ImageLoader imageLoader;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private WordsListHolder.ListName listName;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    private String queryString;
    private ImageView imageHint;
    private View imageHintLinearLayout;
    private Button showHintButton;
    private LearningActivity.LearningStatus learningStatus;

    public LearnedWithoutTranslationFragment() {
    }

    public static LearnedWithoutTranslationFragment newInstance(final String
                                                                        queryString, final WordsListHolder.ListName listName, final LearningActivity.LearningStatus learningStatus) {
        LearnedWithoutTranslationFragment fragment = new LearnedWithoutTranslationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, queryString);
        bundle.putSerializable(ARG_PARAM2, listName);
        bundle.putSerializable(ARG_PARAM3, learningStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryString = getArguments().getString(ARG_PARAM1);
            listName = (WordsListHolder.ListName) getArguments()
                    .get(ARG_PARAM2);
            learningStatus = (LearningActivity.LearningStatus) getArguments().get(ARG_PARAM3);
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
        this.imageHintLinearLayout = rootView.findViewById(R.id.imageHintLinearLayout);
        this.showHintButton = (Button) rootView.findViewById(R.id.showHintButton);
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
        wordsEntry = new WordsListHolder(getActivity()).getList(listName, learningStatus)
                .get
                        (queryString);
        if (TextUtils.isEmpty(wordsEntry.getIconHint()) && TextUtils.isEmpty(wordsEntry.getPersonalHint())) {
            imageHintLinearLayout.setVisibility(View.INVISIBLE);
            showHintButton.setVisibility(View.INVISIBLE);
            return;
        }
        if (learningStatus == LearningActivity.LearningStatus.TEST) {
            imageHintLinearLayout.setVisibility(View.INVISIBLE);
            showHintButton.setVisibility(View.VISIBLE);
            showHintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageHintLinearLayout.setVisibility(View.VISIBLE);
                    showHintButton.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            showHintButton.setVisibility(View.INVISIBLE);
            imageHintLinearLayout.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(wordsEntry.getPersonalHint())) {
            personalHint.setVisibility(View.INVISIBLE);
        } else {
            personalHint.setText(wordsEntry.getPersonalHint());
        }
        if (TextUtils.isEmpty(wordsEntry.getIconHint())) {
            imageHint.setVisibility(View.INVISIBLE);
        } else {
            imageLoader.DisplayImage(wordsEntry.getIconHint(), imageHint);
        }
    }
}
