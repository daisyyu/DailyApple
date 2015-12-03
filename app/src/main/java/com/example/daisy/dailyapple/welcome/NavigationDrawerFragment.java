package com.example.daisy.dailyapple.welcome;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.R;
import com.example.daisy.dailyapple.learn.LearnListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements
        ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private String currentTitle;
    public NavigationDrawerDataPump navigationDrawerDataPump;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationDrawerDataPump = new NavigationDrawerDataPump
                (getChildFragmentManager());
        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);

//        mCallbacks = new NavigationDrawerCallbacks() {
//            @Override
//            public void onNavigationDrawerItemSelected(int position) {
//                Intent intent;
//                if (position == mCurrentSelectedPosition) {
//                    Log.d("Daisy", "NavigationDrawerFragment we are currenlty at " +
//                            "" + position + " no change is made");
//                    return;
//                }
//                mCurrentSelectedPosition = position;
//                switch (position) {
//                    case 0:
//                        // TODO: need optimization Home
//                        intent = new Intent(getActivity(),
//                                WelcomeActivity.class);
//                        startActivity(intent);
//                        break;
//                    case 1:
//                        // Learn
//                        break;
//                    case 2:
//                        // Learn List
//                        intent = new Intent(getActivity(),
//                                LearnListActivity.class);
//                        intent.putExtra(LearnListActivity.LIST_NAME_EXTRA,
//                                WordsListHolder.ListName.TESTING_LIST);
//                        startActivity(intent);
//                        break;
//                    case 3:
//                        // Learn daily 100
//                        break;
//                    case 4:
//                        // Learn gre
//                        break;
//                    case 5:
//                        // review
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ExpandableListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnChildClickListener(this);
        mDrawerListView.setOnGroupClickListener(this);
        mDrawerListView.setAdapter(new ExpandableListAdapter(getActivity(),
                navigationDrawerDataPump.getListDataHeader(),
                navigationDrawerDataPump.getListChildData()));
        // TODO
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.cat1,             /* nav drawer image to replace 'Up'
                 caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
                // This is a bug. Drawer icon should have been set by
                // syncState(), however it is not.
                actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(getActivity
                        (), R.drawable.ic_drawer));
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

//    private void selectItem(int position) {
//        // set current title
//        currentTitle = "hello Daisy";
//        if (mDrawerListView != null) {
//            mDrawerListView.setItemChecked(position, true);
//        }
//        if (mDrawerLayout != null) {
//            mDrawerLayout.closeDrawer(mFragmentContainerView);
//        }
//        if (mCallbacks != null) {
//            mCallbacks.onNavigationDrawerItemSelected(position);
//        }
//    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        } else {
            // inflat fragment specific menu
            inflater.inflate(R.menu.global, menu);
            getActionBar().setTitle(currentTitle);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        closeDrawer();
        NavigationDrawerDataPump.GroupItem groupItem =
                navigationDrawerDataPump.getListDataHeader().get(groupPosition);
        NavigationDrawerDataPump.ChildItem childItem = navigationDrawerDataPump
                .getListChildData().get(groupItem.parentTitle).get(childPosition);
        Intent intent;
        switch (groupItem.parentTitle) {

            case HOME:
                break;
            case LEARNING:
                intent = new Intent(getActivity(), LearnListActivity.class);
                intent.putExtra(LearnListActivity.IS_REVIEW_BOOLEAN_EXTRA,
                        false);
                intent.putExtra(LearnListActivity.LIST_NAME_EXTRA,
                        childItem.listName);
                startActivity(intent);
                break;
            case REVIEW:
                intent = new Intent(getActivity(), LearnListActivity.class);
                intent.putExtra(LearnListActivity.IS_REVIEW_BOOLEAN_EXTRA,
                        true);
                intent.putExtra(LearnListActivity.LIST_NAME_EXTRA,
                        childItem.listName);
                startActivity(intent);
                break;
            case TEST:
                break;
            case SETTING:
                break;
        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        NavigationDrawerDataPump.GroupItem groupItem =
                navigationDrawerDataPump.getListDataHeader().get(groupPosition);
        if (!groupItem.isExpandable) {
            closeDrawer();
            if (groupItem.parentTitle == NavigationDrawerDataPump.ParentTitle.HOME) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(intent);
            }
            // TODO: launch settings activity
            return true;
        }
        return false;
    }

    public static void injectNavigationDrawer(Activity activity) {
        FragmentManager fragmentManager = ((AppCompatActivity) activity)
                .getSupportFragmentManager();
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                fragmentManager.findFragmentById(R.id
                        .navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) activity.findViewById(R.id
                        .drawer_layout));
    }
}
