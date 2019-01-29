# design
类似于新闻客户端：使用Material Design设计support库,DrawerLayout侧滑菜单,swipeRefreshLayout下拉刷新,Toolbar+TabLayout实现标题栏+切换Tab,Retrofix2+Rxjava2

使用Material Design设计support库
一、DrawerLayout实现侧滑菜单
1.布局见activity_main.xml
打开菜单：drawerLayout.openDrawer(Gravity.LEFT);
关闭菜单：drawerLayout.closeDrawer(Gravity.LEFT);

二、Toolbar+TabLayout 实现 标题栏+三个切换Tab
标题栏用Toolbar。
三个切换的Tab用TabLayout。 
1.布局文件:layout_fragment_main_content.xml
最外层是CoordinatorLayout，里面主要就分两块，AppBarLayout+ViewPager（AppBarLayout里面包含标题栏的Toolbar+TabLayout，ViewPager用来切换Fragment显示）

为了使得Toolbar有滑动效果，必须做到如下三点: 
1. CoordinatorLayout作为布局的父布局容器。CoordinatorLayout本身可以看做一个FrameLayout，其内部控件的布局和FrameLayout一致。在需要动态关联的控件上分别加上app:layout_scrollFlags和app:layout_behavior属性
2. 给需要滑动的组件设置 app:layout_scrollFlags=”scroll|enterAlways” 属性。 
3. 滑动的组件必须是AppBarLayout顶部组件。 
4. 给滑动的组件设置app:layout_behavior属性
5.ViewPager显示的Fragment里面不能是ListView，必须是RecyclerView。

2.toolbar
private void initToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.id_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        toolbar.setTitle("我的头条");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(navigationOnClick);
    }

3.TabLayout切换
	TabLayout tabLayout = view.findViewById(R.id.id_tablayout);
        tabLayout.setupWithViewPager(viewPager);
        //tab标题栏的文字取自viewPager的adapter中的getPageTitle()

三、轮播
FragmentTop250.class中的initRollImage()

四、swipeRefreshLayout 下拉刷新
FragmentTop250.class中的initPullRefresh()
swipeRefreshLayout = view.findViewById(R.id.id_swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){});

五、Retrofix2+Rxjava2
HttpMethods.class及MovieServer.class
使用:HttpMethods.getInstance().getTop250(new DisposableObserver<Top250Entry.Subject>() {});
