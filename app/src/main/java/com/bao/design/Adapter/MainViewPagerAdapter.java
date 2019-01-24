package com.bao.design.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bao.design.Fragment.FragmentMainContent;

import java.util.ArrayList;
import java.util.List;

class MainViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    String[] titles;
    List<Fragment> fragments = new ArrayList<>();
    public MainViewPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        FragmentMainContent fragementMainContent = new FragmentMainContent();
        fragments.add(fragementMainContent);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "title " + position;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
