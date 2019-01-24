package com.bao.design.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bao.design.Adapter.FragmentMainContentAdapter;
import com.bao.design.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentMainContent extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.layout_fragment_main_content,null);
        initView(view);
        initToolbar(view);
        return view;
    }

    private void initView(View view) {
        ViewPager viewPager = view.findViewById(R.id.id_viewPager);
        List<Fragment> fragmentList = new ArrayList<>();

        Fragment fragmentTop250 = new FragmentTop250();
        ((MyFragment) fragmentTop250).setTitle("Top250");

        Fragment fragmenSelect = new FragmentSelect();
        ((MyFragment) fragmenSelect).setTitle("精选");
        Fragment fragmenFind = new FragmentFind();
        ((MyFragment) fragmenFind).setTitle("发现");

        fragmentList.add(fragmentTop250);
        fragmentList.add(fragmenSelect);
        fragmentList.add(fragmenFind);
        viewPager.setAdapter(new FragmentMainContentAdapter(getActivity().getSupportFragmentManager(),fragmentList));

        TabLayout tabLayout = view.findViewById(R.id.id_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.id_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        toolbar.setTitle("我的头条");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(navigationOnClick);
    }
    public interface   FragmentNavigationOnClick{
        public void NavigationonClick(View v);
    }
    private View.OnClickListener navigationOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getActivity()instanceof FragmentNavigationOnClick)
                ((FragmentNavigationOnClick)getActivity()).NavigationonClick(v);
        }
    };
}
