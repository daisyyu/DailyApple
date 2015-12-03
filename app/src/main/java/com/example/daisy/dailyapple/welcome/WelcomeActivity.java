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

public class WelcomeActivity extends AppCompatActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        NavigationDrawerFragment.injectNavigationDrawer(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        fragmentManager.beginTransaction().add(R.id.container, welcomeFragment)
                .commit();
    }
}
