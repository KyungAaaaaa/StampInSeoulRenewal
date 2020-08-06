package com.example.stampinseoul2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "r5SONxjKf67vRjWSB5VkCHjhlvpWtAAcXV8IEJumquZL3SfuS9eazbphf2%2BSprq0iO6PVT1MVcC70enAwCeLOA%3D%3D";
    private long backButtonTime = 0;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;

    //public static DBHelper dbHelper;
    public static SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_viewpager);

        viewPager = findViewById(R.id.viewPager);

        FragmentPagerAdapter fragmentPagerAdapter = new tutorialViewPagerAdapter(getSupportFragmentManager());

        setupViewPager(viewPager);

        CircleIndicator indicator = findViewById(R.id.indicator);

        indicator.setViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
       tutorialViewPagerAdapter adapter = new tutorialViewPagerAdapter(getSupportFragmentManager());
     /*  adapter.addFragment(new tutorial_fragment1());
       adapter.addFragment(new tutorial_fragment2());
       adapter.addFragment(new tutorial_fragment3());
       adapter.addFragment(new tutorial_fragment4());
       adapter.addFragment(new tutorial_fragment5());
       adapter.addFragment(new tutorial_fragment6());
       adapter.addFragment(new tutorial_fragment7());*/
       //viewPager.setAdapter(adapter);
    }
}
