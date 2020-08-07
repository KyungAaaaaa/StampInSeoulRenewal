package com.example.stampinseoul2.Theme;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ThemeViewPagerAdapter extends FragmentStatePagerAdapter {
    int count = 0;

    public ThemeViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ThemeFestivalFragment.newInstance();
            case 1:
                return ThemeCultureFragment.newInstance();
            case 2:
                return ThemeFoodFragment.newInstance();
            case 3:
                return ThemeActiFragment.newInstance();
            case 4:
                return ThemeShopFragment.newInstance();
            case 5:
                return ThemeShopFragment.newInstance();
            case 6:
                return ThemeShopFragment.newInstance();
            case 7:
                return ThemeShopFragment.newInstance();
            case 8:
                return ThemeSearchFragment.newInstance();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 9;
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
                return "충청";
            case 5:
                return "전라";
            case 6:
                return "경상";
            case 7:
                return "제주";
            default:
                return null;
        }
    }

}
