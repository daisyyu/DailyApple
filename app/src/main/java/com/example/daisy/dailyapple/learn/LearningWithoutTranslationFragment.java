package com.example.daisy.dailyapple.learn;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;
import imageHint.ImageHintFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LearningWithoutTranslationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LearningWithoutTranslationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearningWithoutTranslationFragment extends Fragment implements SearchQueryChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button gotItButton;
    private EditText personalHintEditText;
    // TODO: Rename and change types of parameters
    private String queryString;

    private Fragment imageHintFragment;
    private FragmentManager fragmentManager;
    private Fragment parentFragment;
    private WordsListHolder.ListName listName;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param queryString Parameter 1.
     * @return A new instance of fragment LearningWithoutTranslationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearningWithoutTranslationFragment newInstance(final String
                                                                         queryString, final WordsListHolder.ListName listName) {
        LearningWithoutTranslationFragment fragment = new LearningWithoutTranslationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, queryString);
        args.putSerializable(ARG_PARAM2, listName);
        fragment.setArguments(args);
        return fragment;
    }

    public LearningWithoutTranslationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryString = getArguments().getString(ARG_PARAM1);
            listName = (WordsListHolder.ListName) getArguments()
                    .get(ARG_PARAM2);
        }
        fragmentManager = getChildFragmentManager();
        parentFragment = getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_learning_no_translation, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateViewOnQueryChange(queryString);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gotItButton = (Button) getView().findViewById(R.id.gotIt);
        personalHintEditText = (EditText) getView().findViewById(R.id.personalHint);
        imageHintFragment = fragmentManager.findFragmentById(R.id
                .imageHintFragment);
        gotItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageIcon = ((ImageHintFragment) imageHintFragment)
                        .getCheckedImage();
                String personalHint = personalHintEditText.getText().toString();
                if (!TextUtils.isEmpty(imageIcon)) {
                    ((OnGotitListener) parentFragment).onChildGotIt
                            (personalHint, imageIcon);
                } else {
                    Toast.makeText(getActivity(), "You need a image to " +
                            "refresh you mind, don't ya?", Toast
                            .LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void updateViewOnQueryChange(String queryString) {
        ((SearchQueryChangeListener) imageHintFragment)
                .updateViewOnQueryChange(queryString);
    }

    public interface OnGotitListener {
        public void onChildGotIt(final String personalHint, final String
                imageIcon);
    }

}
