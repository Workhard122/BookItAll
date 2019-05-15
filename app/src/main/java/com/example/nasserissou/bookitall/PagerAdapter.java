package com.example.nasserissou.bookitall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter (FragmentManager fm, int NumberofTabs){

        super(fm);
        this.mNoOfTabs = NumberofTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                Restaurant restaurant = new Restaurant();
                return restaurant;

            case 1:
                Events events = new Events();
                return events;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}