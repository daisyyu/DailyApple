package com.example.daisy.dailyapple.learn;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class LearningActivityFragment extends Fragment {
    Fragment imageHintFragment;
    Fragment translationFragment;
    FragmentManager fragmentManager;
    private String searchQuery;

    static public String SEARCH_QUERY_KEY = "searchQuery";


    public LearningActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_learning, container, false);
        Bundle args = getArguments();
        searchQuery = args.getString(SEARCH_QUERY_KEY);
        return rooView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = getChildFragmentManager();
        imageHintFragment = fragmentManager.findFragmentById(R.id.imageHintFragmentLearn);
        translationFragment = fragmentManager.findFragmentById(R.id
                .translationFragmentLearn);

    }

    public void updateFragment(String query) {
        SearchQueryChangeListener[] searchQueryChangeListeners =
//        {(SearchQueryChangeListener) imageHintFragment};
                {(SearchQueryChangeListener) imageHintFragment, (SearchQueryChangeListener) translationFragment};

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
        if (searchQuery != null) {
            updateFragment(searchQuery);
        }
    }
}
