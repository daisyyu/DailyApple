package com.example.daisy.dailyapple.learn;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.daisy.dailyapple.R;

import java.util.Arrays;
import java.util.List;

public class LearningActivity extends FragmentActivity {

    private Fragment learnFragment;
    private LearningViewPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private static List<String> list = Arrays.asList(new String[]{"cat",
            "luna", "poodle", "Daisy", "Roc"});
    private String tmp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Daisy", "LearningActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        pagerAdapter = new LearningViewPagerAdapter(getSupportFragmentManager
                (), list);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        tmp = "Daisy";

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Daisy", "LearningActivity onStart: "+tmp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learning, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Daisy", "LearningActivity on stop"+tmp);
    }

    @Override
    protected void onDestroy() {
        Log.d("Daisy", "LearningActivity onDestroy");
        super.onDestroy();
        Log.d("Daisy", "LearningActivity onPostDestroy");
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
