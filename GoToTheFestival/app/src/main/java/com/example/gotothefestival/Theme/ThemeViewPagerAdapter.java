package com.example.gotothefestival.Theme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ThemeViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    int count = 0;
    ArrayList<Fragment> items = new ArrayList<Fragment>();
    String check = null;

    public ThemeViewPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }


    //Adapter갱신
    @Override
    public int getItemPosition(Object object) {
        //check => 교체시 가져온 fm.toString();
       // if (check.equals(object.toString())) {
            return POSITION_NONE;
//        } else {
//            return super.getItemPosition(object);
//        }
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
                return null;
        }
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

    //fm교체
    public void set(Fragment fm, int position) {
        check = getItem(position).toString();
        items.set(position, fm);
    }

}
