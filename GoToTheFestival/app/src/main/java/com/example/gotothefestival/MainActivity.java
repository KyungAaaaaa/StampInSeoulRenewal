package com.example.gotothefestival;

import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.FragmentPagerAdapter;
        import androidx.viewpager.widget.ViewPager;

        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;

        import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "r5SONxjKf67vRjWSB5VkCHjhlvpWtAAcXV8IEJumquZL3SfuS9eazbphf2%2BSprq0iO6PVT1MVcC70enAwCeLOA%3D%3D";
    public static final String APP_NAME = "Apptest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_viewpager);
    }
}
