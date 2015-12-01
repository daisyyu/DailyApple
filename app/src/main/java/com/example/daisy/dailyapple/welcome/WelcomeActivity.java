package com.example.daisy.dailyapple.welcome;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daisy.dailyapple.R;

public class WelcomeActivity extends AppCompatActivity implements
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavigationDrawerDataPump navigationDrawerPump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        navigationDrawerPump = mNavigationDrawerFragment
                .navigationDrawerDataPump;
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        FragmentManager fragmentManager = getSupportFragmentManager();
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentManager.beginTransaction().add(R.id.container, welcomeFragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.welcome, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(WelcomeActivity.this, "groupPosition: " + groupPosition
                + " childPosition: " + childPosition
                , Toast
                .LENGTH_SHORT)
                .show();
        mNavigationDrawerFragment.closeDrawer();
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        Toast.makeText(WelcomeActivity.this, "groupPosition: " + groupPosition
                , Toast
                .LENGTH_SHORT)
                .show();
        if (!navigationDrawerPump.getListDataHeader().get(groupPosition)
                .isExpandable) {
            mNavigationDrawerFragment.closeDrawer();
            return true;
        } else {
        }
        return false;
    }
}
