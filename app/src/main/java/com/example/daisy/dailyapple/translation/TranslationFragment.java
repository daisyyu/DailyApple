package com.example.daisy.dailyapple.translation;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import com.example.daisy.dailyapple.DAO.WordsDAO;
import com.example.daisy.dailyapple.DAO.WordsEntry;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.database.MySQLiteHelper;
import com.example.daisy.dailyapple.learn.LearningActivity;
import com.example.daisy.dailyapple.learn.LearningActivityFragment;
import com.example.daisy.dailyapple.translation.loaders.*;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslationFragment extends Fragment implements SearchQueryChangeListener {
    public String searchTarget;
    public LoaderManager.LoaderCallbacks<IResult> translationCallback;
    public LoaderManager.LoaderCallbacks<IResult> phoneticCallback;
    public static final String SPACE = " ";
    private TextView textView;
    private WordsEntry wordsEntry;
    private WordsListHolder.ListName listName;
    private boolean isLearned;
    private WordsDAO wordsDAO;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    MediaPlayer mediaPlayer;
    private Button mp3PlayButton;
    private boolean isFragmentReady = false;
    private static String PRONOUNCE = "pronounce";

    private String translationBundle;
    private String mp3Address = null;

    private View translationLinearLayout;
    private Button showTranslationButton;
    private LearningActivity.LearningStatus learningStatus;

    public TranslationFragment() {
        // Required empty public constructor
    }

    public static TranslationFragment newInstance(final String searchTarget, final String mp3, final String translation, final LearningActivity.LearningStatus learningStatus) {
        TranslationFragment fragment = new TranslationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, searchTarget);
        bundle.putString(ARG_PARAM2, mp3);
        bundle.putString(ARG_PARAM3, translation);
        bundle.putSerializable(ARG_PARAM4, learningStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Daisy", "TranslationFragment onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchTarget = getArguments().getString(ARG_PARAM1);
            mp3Address = getArguments().getString(ARG_PARAM2);
            translationBundle = getArguments().getString(ARG_PARAM3);
            learningStatus = (LearningActivity.LearningStatus) getArguments().get(ARG_PARAM4);
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
            }
        });
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_translation, container, false);
        this.translationLinearLayout = root.findViewById(R.id.translationLinearLayout);
        this.showTranslationButton = (Button) root.findViewById(R.id.showTranslationButton);
        if (learningStatus == LearningActivity.LearningStatus.TEST) {
            showTranslationButton.setVisibility(View.VISIBLE);
            translationLinearLayout.setVisibility(View.INVISIBLE);
            showTranslationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    translationLinearLayout.setVisibility(View.VISIBLE);
                }
            });
        } else {
            showTranslationButton.setVisibility(View.INVISIBLE);
            translationLinearLayout.setVisibility(View.VISIBLE);
        }
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("Daisy", "TranslationFragment onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getView().findViewById(R.id.translationText);
        mp3PlayButton = (Button) getView().findViewById(R.id.button);
        mp3PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp3Address != null) {
                    playPhonetic();
                }
            }
        });
        LearningActivityFragment parentFragment = (LearningActivityFragment) getParentFragment();
        this.listName = parentFragment.getListName();
        this.wordsDAO = new WordsDAO(getActivity(), listName);
        this.wordsEntry = parentFragment.getWordsEntry();
        this.isLearned = wordsEntry.isLearned();
        if (TextUtils.isEmpty(translationBundle) || TextUtils.isEmpty(mp3Address)) {
            initLoaderCallBack();
        }
        isFragmentReady = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(translationBundle) && !TextUtils.isEmpty(mp3Address)) {
            setPhonetic();
            setTranslation();
        } else {
            updateViewOnQueryChange(searchTarget);
        }
    }

    @Override
    public void updateViewOnQueryChange(String queryString) {
        if (!isFragmentReady) {
            Log.d("Daisy", "updateViewOnQueryChange called when " +
                    "TranslationFragment is not yet ready");
            return;
        }
        searchTarget = !TextUtils.isEmpty(queryString) ? queryString.replace(SPACE, "+") :
                null;
        getLoaderManager().restartLoader(0, null, translationCallback);
        getLoaderManager().restartLoader(1, null, phoneticCallback);
        // release mediaPlayer to idle, release button to inactive
        this.mp3Address = null;
        this.mediaPlayer.reset();
        this.mp3PlayButton.setText("Loading Phonetic");
        this.textView.setText("Loading translation");
    }

    private void initLoaderCallBack() {
        LoaderManager loaderManager = getLoaderManager();
        translationCallback = new LoaderManager.LoaderCallbacks<IResult>() {
            @Override
            public Loader<IResult> onCreateLoader(int id, Bundle args) {
                Log.d("Daisy", "TranslationFragment Translation loader " +
                        "onCreateLoader");
                TranslationLoader loader = new TranslationLoader(getActivity());
                loader.setSearchStr(searchTarget);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<IResult> loader, IResult data) {
                Log.d("Daisy", "onLoadFinished TranslationFragment Youdao");
                if (!data.isOK()) {
                    Log.d("Daisy", "onLoadFinished TranslationFragment Youdao " +
                            "data status is not ok, ignoring response");
                    TranslationFragment.this.textView.setText("No Tranlsation" +
                            " found :(");
                    return;
                }
                YoudaoJSONParserImpl.Result result = (YoudaoJSONParserImpl
                        .Result) data;
                String translation = result.getTranslations();
                String phonetic = result.getPhonetic();
                String explains = result.getExplains();
                TranslationFragment.this.translationBundle = translation + " " + phonetic + " " + explains;
                setTranslation();
            }

            @Override
            public void onLoaderReset(Loader<IResult> loader) {

            }
        };
        phoneticCallback = new LoaderManager.LoaderCallbacks<IResult>() {
            @Override
            public Loader<IResult> onCreateLoader(int id, Bundle args) {
                Log.d("Daisy", "TranslationFragment Phonetic loader " +
                        "onCreateLoader");
                PhoneticLoader loader = new PhoneticLoader(getActivity());
                loader.setSearchStr(searchTarget);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<IResult> loader, IResult data) {
                ForvoJSONParserImpl.Result result = (ForvoJSONParserImpl
                        .Result) data;
                if (!data.isOK()) {
                    Log.d("Daisy", "onLoadFinished TranslationFragment Forvo " +
                            "data status is not ok, ignoring response");
                    TranslationFragment.this.mp3PlayButton.setText("No " +
                            "Phonetic found :(");
                    return;
                }
                String mp3Address = result.getPhoneticMp3();
                TranslationFragment.this.mp3Address = mp3Address;
                Log.d("Daisy", "phonetic onloadFinished with address of: " + mp3Address);
                setPhonetic();
            }

            @Override
            public void onLoaderReset(Loader<IResult> loader) {

            }
        };
    }

    public String getTranslationBundle() {
        return translationBundle;
    }

    public void setTranslationBundle(String translationBundle) {
        this.translationBundle = translationBundle;
    }

    public String getMp3Address() {
        return mp3Address;
    }

    public void setMp3Address(String mp3Address) {
        this.mp3Address = mp3Address;
    }

    private void setPhonetic() {
        // if isLearned and we are still loading the loader, that means phonetic wasn't able to be saved to DB or mem in the first shoot. Try writing to both.
        // write to mem hashmap
        wordsEntry.setPhoneticMP3Address(mp3Address);
        // wite to db
        wordsDAO.updateWordsWithColumn(wordsEntry.getWord(), MySQLiteHelper.Column.COLUMN_MP3, mp3Address);
        TranslationFragment.this.mp3PlayButton.setText(PRONOUNCE);
        try {
            TranslationFragment.this.mediaPlayer.setDataSource
                    (mp3Address);
        } catch (IOException e) {
            Log.e("Daisy", e.getMessage());
        } catch (IllegalStateException e) {
            Log.e("Daisy", "IllegalStateException setDataSource " +
                    "overflow");
        }
    }

    private void setTranslation() {
        // if isLearned and we are still loading the loader, that means translation wasn't able to be saved to DB or mem in the first shoot. Try writing to both.
        // write to mem hashmap
        wordsEntry.setTranslation(translationBundle);
        // wite to db
        wordsDAO.updateWordsWithColumn(wordsEntry.getWord(), MySQLiteHelper.Column.COLUMN_TRANSLATION, translationBundle);
        if (isLearned) {

        }
        textView.setText(translationBundle);
    }

    public void playPhonetic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        try {
            mediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            Log.d("Daisy", "IllegalStateException: " + e + " User trying to lick Phonetic too often");
        }

    }

}
