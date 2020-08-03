package com.example.stampinseoul2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ThemeViewPagerAdapter extends FragmentStatePagerAdapter {
    private int count;

    public ThemeViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    //프래그먼트 교체를 보여주는 역할
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
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }


    //상단의 탭 레이아웃 인디케이터에 텍스트를 선언해주는것
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "축제";
            case 1:
                return "문화";
            case 2:
                return "맛집";
            case 3:
                return "액티비티";
            case 4:
                return "쇼핑";
            default:
                return null;
        }
    }

}
