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
import com.example.daisy.dailyapple.R;
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
    private String mp3Address = null;
    private static final String ARG_PARAM1 = "param1";
    MediaPlayer mediaPlayer;
    private Button mp3PlayButton;
    private boolean isFragmentReady = false;
    private static String PRONOUNCE ="pronounce";

    public TranslationFragment() {
        // Required empty public constructor
    }

    public static TranslationFragment newInstance(final String searchTarget){
        TranslationFragment fragment = new TranslationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, searchTarget);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("Daisy", "TranslationFragment onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchTarget = getArguments().getString(ARG_PARAM1);
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
        // Inflate the layout for this fragment
        Log.d("Daisy", "TranslationFragment onCreateView");
        return inflater.inflate(R.layout.fragment_translation, container, false);
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
        initLoaderCallBack();
        isFragmentReady = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateViewOnQueryChange(searchTarget);
    }

    @Override
    public void updateViewOnQueryChange(String queryString) {
        if (!isFragmentReady){
            Log.d("Daisy","updateViewOnQueryChange called when " +
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
                textView.setText(translation + " " + phonetic + " " + explains);
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
                TranslationFragment.this.mp3PlayButton.setText(PRONOUNCE);
                Log.d("Daisy", "phonetic onloadFinished with address of: " + mp3Address);
                TranslationFragment.this.mp3Address = mp3Address;
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

            @Override
            public void onLoaderReset(Loader<IResult> loader) {

            }
        };
    }

    public void playPhonetic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        mediaPlayer.prepareAsync();

    }

}
