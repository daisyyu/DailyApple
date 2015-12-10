package com.example.daisy.dailyapple.learn;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.welcome.NavigationDrawerActivity;
import com.example.daisy.dailyapple.welcome.NavigationDrawerFragment;

public class LearnListActivity extends NavigationDrawerActivity {

    private WordsListHolder.ListName listName;
    private boolean isReview;
    Fragment learnListFragment;
    public static final String LIST_NAME_EXTRA = "listNameExtra";
    public static final String IS_REVIEW_BOOLEAN_EXTRA = "isReviewBoolean";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        listName = (WordsListHolder.ListName) intent.getSerializableExtra
                (LIST_NAME_EXTRA);
        isReview = intent.getBooleanExtra(IS_REVIEW_BOOLEAN_EXTRA, false);

        super.onCreate(savedInstanceState);
        learnListFragment = LearnListFragment.newInstance(listName, isReview);
        fragmentManager.beginTransaction().add(R.id
                .container, learnListFragment).commit();
    }

    protected void showActivityContextActionBar() {
        String prefix = isReview ? "Review " : "Learning ";
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(prefix + listName.getListName());
    }
}
