package com.bao.design.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bao.design.Adapter.FragmentTop250RecyclerViewAdapter;
import com.bao.design.Adapter.FragmentTop250ViewPagerAdapter;
import com.bao.design.Entry.Top250Entry;
import com.bao.design.R;
import com.bao.design.http.HttpMethods;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class FragmentTop250 extends MyFragment {
    private final static String TAG = "FragmentTop250";
    private List<Top250Entry.Subject> movieSubjects;
    private RecyclerView.Adapter recyclerViewAdapteradapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String mTitle;
    private int viewPagerCurItem=0;
    private int curMovieIndex = 0;
    private ContentLoadingProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.layout_fragment_top250,null);
        initView(view);
        initPullRefresh(view);
       return view;
    }

    private void initPullRefresh(View view) {
        swipeRefreshLayout = view.findViewById(R.id.id_swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateTop250();
            }
        });
    }
    private void updateTop250()
    {
        HttpMethods.getInstance().getTop250(new DisposableObserver<Top250Entry.Subject>() {
            @Override
            public void onNext(Top250Entry.Subject subject) {
                movieSubjects.add(subject);
                Log.d(TAG,subject.getTitle());
                recyclerViewAdapteradapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                progressBarDismis();
            }

            @Override
            public void onComplete() {
                Toast.makeText(getActivity(), "加载完成!", Toast.LENGTH_SHORT).show();
                progressBarDismis();
            }
        },curMovieIndex+=5,5);
    }

    private void progressBarDismis() {
        swipeRefreshLayout.setRefreshing(false);//进度条消失
        Observable.timer(2,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        progressBar.hide();
                    }
                });
    }

    private void initView(View view) {
        progressBar = view.findViewById(R.id.id_progressbar);
        Toast.makeText(getActivity(), "加载中，请稍候!", Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = view.findViewById(R.id.id_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieSubjects = new ArrayList<>();
        recyclerViewAdapteradapter = new FragmentTop250RecyclerViewAdapter(getActivity(),R.layout.layout_fragment_top250_item,movieSubjects);
        recyclerView.setAdapter(recyclerViewAdapteradapter);
        recyclerView.addOnScrollListener(pullUpRefresh);

        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_fragment_top250_header,null);
        initRollImage(headView);
        ((FragmentTop250RecyclerViewAdapter) recyclerViewAdapteradapter).addHeader(headView);

        updateTop250();
    }

    //上拉刷新
    private RecyclerView.OnScrollListener pullUpRefresh = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager mLayoutManager = recyclerView.getLayoutManager();
            int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = mLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount-1 && dy > 0) {//最后一项显示且是下滑的时候调用加载
                if(progressBar.getVisibility() != View.VISIBLE)
                    updateTop250();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.show();
                //需要自己设置排除多次重复调用
            }
        }
    };

    private void initRollImage(final View view) { //图片轮播
        final ViewPager viewPager = view.findViewById(R.id.id_viewPager);
        int imageRes[] = new int[]{R.drawable.c1,R.mipmap.ic_item_like};
        final String imageUrl[] = new String[]{"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"
        ,"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p1910813120.webp"
        ,"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p511118051.webp"};
        initIndicator(view,imageUrl.length);
        viewPager.setAdapter(new FragmentTop250ViewPagerAdapter(getActivity(),imageUrl));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        Observable.interval(2,5,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        viewPagerCurItem++;
                        if(viewPagerCurItem > imageUrl.length)
                            viewPagerCurItem = 0;
                        viewPager.setCurrentItem(viewPagerCurItem);
                    }
                });
    }
    List<ImageView> mIndicatorImageList = null;
    private void initIndicator(View view,int len) {
        mIndicatorImageList = new ArrayList<>();
        LinearLayout layoutIndicator = view.findViewById(R.id.id_indicater);
        for(int i=0;i<len;i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 5;
            lp.bottomMargin = 5;
            ImageView imageView = new ImageView(getActivity());
            //imageView.setImageResource(android.R.drawable.presence_online);
            layoutIndicator.addView(imageView,lp);
            mIndicatorImageList.add(imageView);
        }
        setIndicator(0);
    }
    private void setIndicator(int pos){
        for(int i=0;i<mIndicatorImageList.size();i++){
            ImageView imageView = mIndicatorImageList.get(i);
            if(i == pos){
                imageView.setImageResource(android.R.drawable.presence_online);
            }else {
                imageView.setImageResource(android.R.drawable.presence_invisible);
            }
        }
    }
}
