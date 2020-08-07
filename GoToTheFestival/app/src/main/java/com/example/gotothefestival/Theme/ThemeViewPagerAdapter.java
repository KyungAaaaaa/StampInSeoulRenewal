package com.example.gotothefestival.Theme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ThemeViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    int count = 0;
    ThemeViewPagerAdapter adapter;
    public ThemeViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return com.example.gotothefestival.Theme.ThemeSeoulFragment.newInstance();
            case 1:
                return com.example.gotothefestival.Theme.ThemeGyeonggiFragment.newInstance();
            case 2:
                return com.example.gotothefestival.Theme.ThemeIncheonFragment.newInstance();
            case 3:
                return com.example.gotothefestival.Theme.ThemeGangwonFragment.newInstance();
            case 4:
                return com.example.gotothefestival.Theme.ThemeJejuFragment.newInstance();
            case 5:
                return com.example.gotothefestival.Theme.ThemeSearchFragment.newInstance();
            default:
                return  null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "서울";
            case 1:
                return "경기";
            case 2:
                return "인천";
            case 3:
                return "강원";
            case 4:
                return "제주";
            default:
                return null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
