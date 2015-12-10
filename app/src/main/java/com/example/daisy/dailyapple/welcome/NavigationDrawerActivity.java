package com.example.daisy.dailyapple.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.example.daisy.dailyapple.R;

public abstract class NavigationDrawerActivity extends AppCompatActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected FragmentManager fragmentManager;
    protected int childPosition = 0;
    protected int parentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        parentPosition = intent.getIntExtra(NavigationDrawerFragment.PARENT_POSITION_EXTRA, 0);
        childPosition = intent.getIntExtra(NavigationDrawerFragment.CHILD_POSITION_EXTRA, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        fragmentManager = getSupportFragmentManager();

        NavigationDrawerFragment.injectNavigationDrawer(this);
    }

    public int getChildPosition() {
        return childPosition;
    }

    public int getParentPosition() {
        return parentPosition;
    }


    protected abstract void showActivityContextActionBar();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        showActivityContextActionBar();
        return super.onCreateOptionsMenu(menu);
    }

}
