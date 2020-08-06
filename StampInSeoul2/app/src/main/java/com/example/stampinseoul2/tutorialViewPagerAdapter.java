package com.example.stampinseoul2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class tutorialViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentArrayList = new ArrayList<>();

    public tutorialViewPagerAdapter(FragmentManager fl) {
        super(fl);
    }

   @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentArrayList.add(fragment);
    }

}
