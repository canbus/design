package com.bao.design;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bao.design.http.HttpMethods;
import com.bao.design.Fragment.FragmentMainContent;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        ,FragmentMainContent.FragmentNavigationOnClick {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoad();
        drawerLayout = findViewById(R.id.drawerlayout);
        initClickListener();
        initView();

        //initTitle();
    }

    private void initImageLoad() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(configuration);
    }

    private void initView() {
//        TabLayout tabLayout = findViewById(R.id.id_tablayout);
//        ViewPager viewPager = findViewById(R.id.id_viewPager);
//        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),new String[]{"精选","订阅","发现"}));
//        tabLayout.setupWithViewPager(viewPager);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.id_mainContentLayout,new FragmentMainContent());
        ft.commit();


    }

    private void initTitle() {
//        Toolbar toolbar = findViewById(R.id.id_tool_bar);
//        this.setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
//        toolbar.setTitle("我的头条");
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
    }

    private void initClickListener() {
        findViewById(R.id.id_nav_gift).setOnClickListener(this);
        //findViewById(R.id.id_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.id_tv:
//                drawerLayout.openDrawer(Gravity.LEFT);
//                break;
            case R.id.id_nav_gift:
                Toast.makeText(this, "礼物兑换", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void NavigationonClick(View v) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
