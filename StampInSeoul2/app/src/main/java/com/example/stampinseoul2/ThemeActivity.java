package com.example.stampinseoul2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText edtSearch;
    private ImageButton ibSearch;
    private FloatingActionButton fab, fab1, fab2;
    private ThemeSearchFragment themeSearchFragment;
    private Animation fab_open, fab_close;
    private boolean isFabOpen;
    private boolean searchFlag;
    private boolean searchDisplayFlag;
    private FrameLayout searchFrame;
    String currentDisplay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        edtSearch = findViewById(R.id.edtSearch);
        ibSearch = findViewById(R.id.ibSearch);
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new ThemeViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentStatePagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabTextColors(Color.LTGRAY, Color.BLACK);
        searchFrame = findViewById(R.id.searchFrame);

        // == 플로팅 버튼, 드로어
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        ibSearch.setOnClickListener(this);

    }

    public ThemeSearchFragment getThemeSearchFragment() {
        return themeSearchFragment;
    }

    public void setThemeSearchFragment(ThemeSearchFragment themeSearchFragment) {
        this.themeSearchFragment = themeSearchFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(ThemeActivity.this, BottomMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.fab2:
                break;
            case R.id.ibSearch:
                String word = edtSearch.getText().toString().trim();
                if (word.length() > 1) {
                    searchData();
                    themeSearchFragment = new ThemeSearchFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", word);
                    themeSearchFragment.setArguments(bundle);
                    searchDisplayFlag = true;
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.searchFrame, themeSearchFragment).commit();
                } else {
                    Toast.makeText(getApplicationContext(), "두 글자 이상 입력해 주세요", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void searchData() {
        if (!searchFlag) {
            tabLayout.setVisibility(View.GONE);
            searchFrame.setVisibility(View.VISIBLE);
            currentDisplay = "search";
            //searchFlag=true;
        } else {
            tabLayout.setVisibility(View.VISIBLE);
            searchFrame.setVisibility(View.GONE);
            currentDisplay = "none";
           // searchFlag=false;
        }
    }

    public void anim() {
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    private long backKeyPressedTime = 0;//

    // 뒤로가기 버튼 이벤트
    @Override
    public void onBackPressed() {
        if (searchDisplayFlag) {
            searchFrame.setVisibility(View.GONE);
            searchFlag=false;
            tabLayout.setVisibility(View.VISIBLE);
            searchDisplayFlag=false;
            themeSearchFragment=null;
            return;
        }
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) finish();
    }
}
