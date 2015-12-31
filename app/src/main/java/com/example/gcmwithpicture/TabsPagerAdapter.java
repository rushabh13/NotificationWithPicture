package com.example.gcmwithpicture;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {


    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override

    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new DemoFragment();
            case 1:
                return new DemoFragment();
            case 2:
                return new DemoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}