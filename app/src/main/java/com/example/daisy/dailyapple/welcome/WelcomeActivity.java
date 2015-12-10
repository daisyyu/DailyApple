package com.example.daisy.dailyapple.welcome;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daisy.dailyapple.R;

public class WelcomeActivity extends NavigationDrawerActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentManager.beginTransaction().add(R.id.container, welcomeFragment)
                .commit();
    }

    @Override
    protected void showActivityContextActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Welcome");
        // Setting just setTitle doesn't work, has to set using actionBar
    }
}
