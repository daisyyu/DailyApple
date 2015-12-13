package imageHint;


import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.translation.SearchQueryChangeListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageHintFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageHintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageHintFragment extends ListFragment implements
        SearchQueryChangeListener {
    public static final String SPACE = " ";
    private OnFragmentInteractionListener mListener;
    LoaderManager.LoaderCallbacks<List<ImageEntry>> callback;
    String searchTarget;
    ImageHintAdapter adapter;
    ListView listView;

    private String checkedImage;
    private RadioButton checkedRadioButton;

    List<ImageEntry> imageEntries;
    private boolean isFragmentReady = false;

    public ImageHintFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_hint, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//      specify what layout each row has
        adapter = new ImageHintAdapter(getActivity(), R.layout.listview_item_row);
//        View header = (View) getActivity().getLayoutInflater().inflate(R.layout
//                .listview_header_row, null);
//        listView.addHeaderView(header);
        setListAdapter(adapter);
        initLoaderCallBack();
        isFragmentReady = true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("Daisy", "ImageHintFragment,onItemClick: " + position);
        if (imageEntries == null) {
            Log.d("Daisy", "imageEntries are not ready, ignore onListitemClick ");
            return;
        }
        RadioButton clickedRadioButton = (RadioButton) v.findViewById(R.id.radioButton);
        // uncheck previously checked radio buttons
        if (checkedRadioButton != null) {
            checkedRadioButton.setChecked(false);
        }
        checkedRadioButton = clickedRadioButton;
        checkedRadioButton.setChecked(true);
        checkedImage = imageEntries.get(position).getIcon();
        Log.d("Daisy", "ImageHintFragment,after onItemClick, checkedImages are" +
                " now: " + checkedImage);
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
        Log.d("Daisy", "ImageHintFragment updateViewOnQueryChange");
        //reset values for checked images and memory hint
        checkedImage = null;
        getLoaderManager().restartLoader(0, null, callback);
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

    public String getCheckedImage() {
        return checkedImage;
    }

    private void initLoaderCallBack() {
        LoaderManager loaderManager = getLoaderManager();
        callback = new LoaderManager.LoaderCallbacks<List<ImageEntry>>() {
            @Override
            public Loader<List<ImageEntry>> onCreateLoader(int id, Bundle args) {
                ImageEntryLoader loader = new ImageEntryLoader(getActivity());
                loader.setSearchStr(searchTarget);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<ImageEntry>> loader, List<ImageEntry> data) {
                Log.d("Daisy", "onLoadFinished ImageHintFragment");
                adapter.setData(data);
                imageEntries = data;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<List<ImageEntry>> loader) {

            }
        };
    }

}
