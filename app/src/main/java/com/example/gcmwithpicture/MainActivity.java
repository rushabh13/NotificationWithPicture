package com.example.gcmwithpicture;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    public CustomViewPager customViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customViewPager = (CustomViewPager) findViewById(R.id.activity_main_viewpager);
        customViewPager.setPagingEnabled(false);

        final TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        customViewPager.setAdapter(tabsPagerAdapter);

    }

}
