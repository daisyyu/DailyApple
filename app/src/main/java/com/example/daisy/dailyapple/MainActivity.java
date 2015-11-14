package com.example.daisy.dailyapple;


import android.app.FragmentManager;

import android.os.Bundle;
import android.app.Fragment;


import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.SearchView;
import com.example.daisy.dailyapple.global.Utils;

import com.example.daisy.dailyapple.translation.loaders.SearchQueryChangeListener;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView
        .OnQueryTextListener {

    Fragment imageHintFragment;
    Fragment translationFragment;

    public static final String TAG = "Daisy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate is called for MainActivity");
        FragmentManager fragmentManager = getFragmentManager();
        imageHintFragment = fragmentManager.findFragmentById(R.id.imageHintFragment);
        translationFragment = fragmentManager.findFragmentById(R.id
                .translationFragment);
        Utils.requestGlobalPermissionsUpfront(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        SearchView sv = new SearchView(this);
//        sv.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        sv.setOnQueryTextListener(this);
        sv.setQuery("Cat", false);
        item.setActionView(sv);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Daisy", "onQueryTextSubmit: " + query);
        SearchQueryChangeListener[] searchQueryChangeListeners =
//        {(SearchQueryChangeListener) imageHintFragment};
                {(SearchQueryChangeListener) imageHintFragment, (SearchQueryChangeListener) translationFragment};

        for (SearchQueryChangeListener listener : searchQueryChangeListeners
                ) {
            listener.updateViewOnQueryChange(query);

        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // no action on change
//        Log.v("Daisy", "onQueryTextChange: ");
        return false;
    }


}
