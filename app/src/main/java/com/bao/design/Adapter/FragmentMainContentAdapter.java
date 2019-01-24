package com.bao.design.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.bao.design.Fragment.MyFragment;

import java.util.List;

public class FragmentMainContentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mFragmentList;
    public FragmentMainContentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ((MyFragment)mFragmentList.get(position)).getTitle();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
