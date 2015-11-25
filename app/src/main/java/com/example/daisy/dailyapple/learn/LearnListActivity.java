package com.example.daisy.dailyapple.learn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.daisy.dailyapple.R;

public class LearnListActivity extends AppCompatActivity {

    private WordsListHolder.ListName listName;
    FragmentManager fragmentManager;
    Fragment learnListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listName = WordsListHolder.ListName.TESTING_LIST;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_list);
        fragmentManager = getSupportFragmentManager();
        learnListFragment = LearnListFragment.newInstance(listName);
        fragmentManager.beginTransaction().add(R.id
                .learnListFragmentPlaceHolder, learnListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learn_list, menu);
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
}
