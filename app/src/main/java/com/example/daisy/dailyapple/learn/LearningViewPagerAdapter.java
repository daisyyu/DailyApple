package com.example.daisy.dailyapple.learn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import com.example.daisy.dailyapple.learn.LearningActivityFragment;

import java.util.List;

/**
 * Created by Daisy on 11/11/15.
 */
public class LearningViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> dictionary;
    public LearningViewPagerAdapter(FragmentManager fm, List<String>
            dictionary) {
        super(fm);
        this.dictionary = dictionary;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new LearningActivityFragment();
        Bundle args = new Bundle();
        args.putString(LearningActivityFragment.SEARCH_QUERY_KEY, dictionary
                .get(position));
        Log.d("Daisy", "LearningViewPagerAdapter getItem postition:" + position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return dictionary.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dictionary.get(position);
    }
}
