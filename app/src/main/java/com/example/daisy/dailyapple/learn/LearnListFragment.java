package com.example.daisy.dailyapple.learn;


import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.R;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageHintFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageHintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnListFragment extends ListFragment {
    public static final String SPACE = " ";
    private OnFragmentInteractionListener mListener;
    LoaderManager.LoaderCallbacks<Map<String, WordsEntry>> callback;
    LearnListItemAdapter adapter;
    ListView listView;
    LearningActivity.LearningStatus learningStatus;
    Map<String, WordsEntry> wordsEntryMap = null;
    String[] keyArray = null;
    private WordsListHolder.ListName listName;

    public static String EXTRA_WORDS_LIST = "extra_words_list";
    public static String EXTRA_CLICK_POSITION = "extra_click_position";
    public static String EXTRA_LIST_LEARNING_STATUS = "extraLearningStatus";
    public static String EXTRA_LIST_NAME = "extra_list_name";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public LearnListFragment() {
        // Required empty public constructor
    }

    public static LearnListFragment newInstance(final WordsListHolder
            .ListName listName, final LearningActivity.LearningStatus learningStatus) {
        LearnListFragment fragment = new LearnListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, listName);
        args.putSerializable(ARG_PARAM2, learningStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Daisy", "LearnListFragment onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listName = (WordsListHolder.ListName) getArguments().get
                    (ARG_PARAM1);
            learningStatus = (LearningActivity.LearningStatus) getArguments().get(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learn_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getListView();
//      specify what layout each row has
        adapter = new LearnListItemAdapter(getActivity(), R.layout.learn_listview_item_row, learningStatus);
        setListAdapter(adapter);
        initLoaderCallBack();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Daisy", "LearnListFragment onStart(), restartLoader");
        getLoaderManager().restartLoader(0, null, callback);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Daisy", "LearnListFragment,onItemClick: " + position);
        if (keyArray == null || wordsEntryMap == null) {
            Log.d("Daisy", "keyArray is null or wordsEntryMap is null, drop " +
                    "the lick intent");
            return;
        }
        Intent intent = new Intent(getActivity(), LearningActivity.class);
        intent.putExtra(EXTRA_WORDS_LIST, keyArray);
        intent.putExtra(EXTRA_CLICK_POSITION, position);
        intent.putExtra(EXTRA_LIST_NAME, listName);
        intent.putExtra(EXTRA_LIST_LEARNING_STATUS, learningStatus);
        startActivity(intent);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void initLoaderCallBack() {
        LoaderManager loaderManager = getLoaderManager();
        callback = new LoaderManager.LoaderCallbacks<Map<String, WordsEntry>>() {
            @Override
            public Loader<Map<String, WordsEntry>> onCreateLoader(int id,
                                                                  Bundle args) {
                Log.d("Daisy", "LearnListFragment onCreateLoader");
                return new LearnItemEntryLoader(getActivity(), listName, learningStatus);
            }

            @Override
            public void onLoadFinished(Loader<Map<String, WordsEntry>> loader, Map<String, WordsEntry> data) {
                Log.d("Daisy", "onLoadFinished LearnListFragment");
                if (data == null) {
                    Log.d("Daisy", "LearnListFragment onLoadFinished received " +
                            "null as map");
                    return;
                }
                wordsEntryMap = data;
                for (Map.Entry<String, WordsEntry> entry : data.entrySet()
                        ) {
                    Log.d("Daisy", "key: " + entry.getKey() + "value: " + entry
                            .getValue());
                }
                keyArray = new String[data.size()];
                final Set<String> keySet = data.keySet();
                keyArray = keySet.toArray(keyArray);
                // sort the key array so it is of arphabetical order
                Arrays.sort(keyArray);
                adapter.setData(data, keyArray);
                adapter.notifyDataSetChanged();
                Log.d("Daisy", "onLoadFinished reaches end LearnListFragment");
            }

            @Override
            public void onLoaderReset(Loader<Map<String, WordsEntry>> loader) {

            }
        };
    }

}
