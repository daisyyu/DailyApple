package com.example.daisy.dailyapple.learn;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.daisy.dailyapple.DAO.WordsDAO;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.learned.LearnedWithoutTranslationFragment;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;
import com.example.daisy.dailyapple.translation.TranslationFragment;

import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LearningActivityFragment extends Fragment implements
        LearningWithoutTranslationFragment.OnGotitListener {
    Fragment learningWithoutTranslationFragment;
    Fragment translationFragment;
    Context context;
    /**
     * Fragment Holding the image and personalHint depending on isLearned or
     * not it could be learningWithoutTranslationFragment or
     * learnedWithoutTranslationFragment
     */
    Fragment bottomFragment;
    FragmentManager fragmentManager;
    SharedPreferences sharedPref;

    private WordsEntry wordsEntry;
    private String searchQuery;

    private WordsListHolder.ListName listName;

    private WordsListHolder wordsListHolder;
    private LearningActivity.LearningStatus learningStatus;
    static public String SEARCH_QUERY_KEY = "searchQuery";

    static public String LISTNAME_KEY = "listName";
    static public String LEARNING_STATUS_KEY = "learningStatus";

    public LearningActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        sharedPref = context.getSharedPreferences(context
                .getString(R.string
                        .preference_file_key_is_learned_testing_list)
                , Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
        View rooView = inflater.inflate(R.layout.fragment_learning, container, false);
        Bundle args = getArguments();
        searchQuery = args.getString(SEARCH_QUERY_KEY);
        listName = (WordsListHolder.ListName) args.get(LISTNAME_KEY);
        learningStatus = (LearningActivity.LearningStatus) args.get(LEARNING_STATUS_KEY);
        wordsListHolder = new WordsListHolder(getActivity());
        wordsEntry = wordsListHolder.getList(listName, learningStatus).get(searchQuery);
        if (learningStatus == LearningActivity.LearningStatus.LEARNING && !wordsEntry.isLearned()) {
            bottomFragment = LearningWithoutTranslationFragment.newInstance
                    (searchQuery, listName);
        } else {
            bottomFragment = LearnedWithoutTranslationFragment.newInstance
                    (searchQuery, listName, learningStatus);
        }
        fragmentManager.beginTransaction().add(R.id
                        .buttomFragmentPlaceHolder,
                bottomFragment).commit();
        translationFragment = TranslationFragment.newInstance(searchQuery, wordsEntry.getPhoneticMP3Address(), wordsEntry.getTranslation(), learningStatus);
        fragmentManager.beginTransaction().add(R.id
                .translationFragmentLearnPlaceHolder, translationFragment)
                .commit();
        return rooView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void updateFragment(String query) {
        SearchQueryChangeListener[] searchQueryChangeListeners =
                {(SearchQueryChangeListener) bottomFragment, (SearchQueryChangeListener) translationFragment};

        for (SearchQueryChangeListener listener : searchQueryChangeListeners
                ) {
            listener.updateViewOnQueryChange(query);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (searchQuery != null) {
//            updateFragment(searchQuery);
//        }
    }

    @Override
    public void onChildGotIt(final String personalHint, final String
            imageIcon) {
        Map<String, WordsEntry> reviewListMemHolder = wordsListHolder.getList(listName, LearningActivity.LearningStatus.REVIEW);


        wordsEntry.setIconHint(imageIcon);
        wordsEntry.setPersonalHint(personalHint);
        wordsEntry.setIsLearned(true);

        final String mp3Address = ((TranslationFragment) translationFragment).getMp3Address();
        final String translation = ((TranslationFragment) translationFragment).getTranslationBundle();
        wordsEntry.setPhoneticMP3Address(mp3Address);
        wordsEntry.setTranslation(translation);

        reviewListMemHolder.put(searchQuery,wordsEntry);
        bottomFragment = LearnedWithoutTranslationFragment.newInstance
                (searchQuery, listName, learningStatus);
        fragmentManager.beginTransaction().replace(R.id
                .buttomFragmentPlaceHolder, bottomFragment).commit();
        // TODO: make this an intent service
        // Write to database on the ASAP to prevent data loss
        WordsDAO wordsDAO = new WordsDAO(getActivity(), listName);
        wordsDAO.addWordsEntry(wordsEntry);
    }


    /**
     * For child Fragments to access the current WordsEntry in mem
     *
     * @return
     */
    public WordsEntry getWordsEntry() {
        return wordsEntry;
    }

    public WordsListHolder.ListName getListName() {
        return listName;
    }
}
