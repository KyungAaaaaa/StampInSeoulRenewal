package com.example.stampinseoul2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private MapLocateActivity mapLocateActivity;
    private GpsActivity gpsActivity;
    private AlbumActivity albumActivity;
    private MoreActivity moreActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);

        mapLocateActivity = new MapLocateActivity();
        moreActivity = new MoreActivity();
        gpsActivity = new GpsActivity();
        albumActivity = new AlbumActivity();

        setChangeFragment(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_gps:
                        setChangeFragment(0);
                        break;
                    case R.id.action_map:
                        setChangeFragment(1);
                        break;
                    case R.id.action_review:
                        setChangeFragment(2);
                        break;
                    case R.id.action_more:
                        setChangeFragment(3);
                        break;
                }
                return true;
            }
        });

    }

    private void setChangeFragment(int position) {
        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();

        switch (position) {

            case 0:
                fragmentTransaction.replace(R.id.frameLayout, gpsActivity);
                break;
            case 1:
                MapLocateActivity mainFragment = new MapLocateActivity();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFragment, "main").commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.frameLayout, albumActivity);
                break;
            case 3:
                fragmentTransaction.replace(R.id.frameLayout, moreActivity);
                break;
        }//switch
        fragmentTransaction.commit();
        return;

    }
}
