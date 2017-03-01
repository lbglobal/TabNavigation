package com.wordplat.quickstart.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.wordplat.easydivider.RecyclerViewGridDivider;
import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.fragment.HomeTabClickListener;
import com.wordplat.quickstart.ui.home.adapter.AdDataHolder;
import com.wordplat.quickstart.ui.home.adapter.Grid3x3Adapter;
import com.wordplat.quickstart.ui.home.adapter.Grid5x2Adapter;
import com.wordplat.quickstart.ui.home.adapter.HorizontalAdapter;
import com.wordplat.quickstart.utils.AppUtils;
import com.wordplat.quickstart.widget.TabButton;
import com.wordplat.quickstart.widget.custom.AppBarHeaderBehavior;
import com.wordplat.quickstart.widget.custom.CustomCollapsingToolbarLayout;
import com.wordplat.quickstart.widget.pulllistview.PullListLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 *
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements HomeTabClickListener {
    public static final String TAG = "HomeFragment";

    @ViewInject(R.id.pullList) private PullListLayout pullList = null;
    @ViewInject(R.id.appBar) private AppBarLayout appBar = null;
    @ViewInject(R.id.toolBar) private CustomCollapsingToolbarLayout toolBar = null;
    @ViewInject(R.id.titleLayout) private LinearLayout titleLayout = null;
    @ViewInject(R.id.slidingTab) private SlidingTabLayout slidingTab = null;
    @ViewInject(R.id.viewPager) private ViewPager viewPager = null;

    private AppBarHeaderBehavior behavior;

    private HomeSubFragment1 homeSubFragment1;
    private HomeSubFragment2 homeSubFragment2;
    private HomeSubFragmentAdapter fragmentAdapter;

    @ViewInject(R.id.adList) private View adList = null;
    @ViewInject(R.id.horizontalList) private RecyclerView horizontalList = null;
    @ViewInject(R.id.grid3x3List) private RecyclerView grid3x3List = null;
    @ViewInject(R.id.grid5x2List) private RecyclerView grid5x2List = null;

    private HorizontalAdapter horizontalAdapter;
    private Grid3x3Adapter grid3x3Adapter;
    private Grid5x2Adapter grid5x2Adapter;

    private static List<String> adDataList = new ArrayList<>();

    static {
        adDataList.add("http://www.istartedsomething.com/bingimages/resize.php?i=TrakaiIslandCastle_EN-US13260881447_1366x768.jpg&w=1080");
        adDataList.add("http://www.istartedsomething.com/bingimages/resize.php?i=RossFountain_EN-US11490955168_1366x768.jpg&w=1080");
        adDataList.add("http://www.istartedsomething.com/bingimages/resize.php?i=EifelNPBelgium_EN-US13320978952_1366x768.jpg&w=1080");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
        initData();
    }

    private void initUI() {
        toolBar.setTitleLayout(titleLayout);
        pullList.setPtrHandler(ptrDefaultHandler);

        homeSubFragment1 = HomeSubFragment1.newInstance();
        homeSubFragment2 = HomeSubFragment2.newInstance();

        fragmentAdapter = new HomeSubFragmentAdapter(getChildFragmentManager(), homeSubFragment1, homeSubFragment2);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);

        slidingTab.setViewPager(viewPager);

        behavior = (AppBarHeaderBehavior) ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
    }

    private void initData() {
        AdDataHolder adDataHolder = new AdDataHolder(adList);
        adDataHolder.setOnClickListener(new AdDataHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mActivity, "点击了第 " + position + "个广告", Toast.LENGTH_SHORT).show();
            }
        });

        if (adDataList.isEmpty()) {
            adList.setVisibility(View.GONE);
        } else {
            adList.setVisibility(View.VISIBLE);
            adDataHolder.display(adDataList);
        }

        // 首页水平滚动列表
        horizontalAdapter = new HorizontalAdapter(mActivity);
        horizontalList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        horizontalList.setAdapter(horizontalAdapter);
        // 添加水平分割线
        RecyclerViewLinearDivider recyclerViewLinearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.HORIZONTAL, AppUtils.dpTopx(mActivity, 10), Color.parseColor("#eeeeee"));
        horizontalList.addItemDecoration(recyclerViewLinearDivider);

        // 首页九宫格列表
        grid3x3Adapter = new Grid3x3Adapter(mActivity);
        grid3x3List.setLayoutManager(new GridLayoutManager(mActivity, 3));
        grid3x3List.setAdapter(grid3x3Adapter);
        // 九宫格列表添加分割线
        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setRowDividerMargin(30, 30);
        recyclerViewGridDivider.setColDividerMargin(30, 30);
        recyclerViewGridDivider.setDividerColor(0xFFDDDDDD);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        recyclerViewGridDivider.setFillItemDivider(true);
        grid3x3List.addItemDecoration(recyclerViewGridDivider);

        // 10个按钮
        grid5x2Adapter = new Grid5x2Adapter(mActivity);
        grid5x2List.setLayoutManager(new GridLayoutManager(mActivity, 5));
        grid5x2List.setAdapter(grid5x2Adapter);
    }

    @Override
    public void onTabClick(TabButton tabButton) {
        Toast.makeText(mActivity, "点击了首页", Toast.LENGTH_SHORT).show();
        appBar.setExpanded(true, false);
    }

    /**
     * 下拉刷新监听
     */
    private PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return behavior.getCurrentVerticalOffset() == 0 && super.checkCanDoRefresh(frame, content, header);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            pullList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pullList.refreshComplete();
                }
            }, 2000);
        }
    };

    /**
     * ViewPager 的适配器
     */
    private static class HomeSubFragmentAdapter extends FragmentStatePagerAdapter {

        private BaseFragment[] fragment = null;
        private String[] titles = null;

        public HomeSubFragmentAdapter(FragmentManager fm, BaseFragment... fragment) {
            super(fm);
            this.fragment = fragment;
            this.titles = new String[] {"Fragment1", "Fragment2"};
        }

        @Override
        public Fragment getItem(int position) {
            return fragment[position];
        }

        @Override
        public int getCount() {
            return fragment.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
