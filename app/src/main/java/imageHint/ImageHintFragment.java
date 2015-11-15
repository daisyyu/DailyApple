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

import java.util.ArrayList;
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
    LoaderManager.LoaderCallbacks<List<CatEntry>> callback;
    String searchTarget;
    CatAdapter adapter;
    ListView listView;
    List<String> checkedImages = new ArrayList<>();
    List<CatEntry> catEntries;
    private boolean isFragmentReady = false;

    public ImageHintFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLoaderCallBack();

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
//      specify what layout each row has
        adapter = new CatAdapter(getActivity(), R.layout.listview_item_row);
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
        if (catEntries == null) {
            Log.d("Daisy", "catEntries are not ready, ignore onListitemClick ");
            return;
        }
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
        if (checkBox.isChecked()) {
            //remove from list
            checkedImages.remove(catEntries.get(position).getIcon());
        } else {
            checkedImages.add(catEntries.get(position).getIcon());
        }
        checkBox.performClick();
        Log.d("Daisy", "ImageHintFragment,after onItemClick, checkedImages are" +
                " now: " + checkedImages.toString());
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
        Toast.makeText(getActivity(), "query is changed", Toast
                .LENGTH_SHORT).show();
        // store checked image and memory hint aync
        //reset values for checked images and memory hint
        checkedImages.clear();
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

    private void initLoaderCallBack() {
        LoaderManager loaderManager = getLoaderManager();
        callback = new LoaderManager.LoaderCallbacks<List<CatEntry>>() {
            @Override
            public Loader<List<CatEntry>> onCreateLoader(int id, Bundle args) {
                CatEntryLoader loader = new CatEntryLoader(getActivity());
                loader.setSearchStr(searchTarget);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<CatEntry>> loader, List<CatEntry> data) {
                Log.d("Daisy", "onLoadFinished ImageHintFragment");
                adapter.setData(data);
                catEntries = data;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<List<CatEntry>> loader) {

            }
        };
    }

}
