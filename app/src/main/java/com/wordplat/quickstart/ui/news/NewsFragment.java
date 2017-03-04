package com.wordplat.quickstart.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.wordplat.easydivider.RecyclerViewCornerRadius;
import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.fragment.HomeTabClickListener;
import com.wordplat.quickstart.ui.news.adapter.TextAdapter;
import com.wordplat.quickstart.widget.TabButton;
import com.wordplat.quickstart.widget.pulllistview.LoadMoreViewFooter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 新闻
 *
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_news)
public class NewsFragment extends BaseFragment implements HomeTabClickListener {

    @ViewInject(R.id.ptrFrameLayout) private PtrFrameLayout ptrFrameLayout = null;
    @ViewInject(R.id.textList) private RecyclerView textList = null;

    private TextAdapter textAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
    }

    private void initUI() {
        textAdapter = new TextAdapter(mActivity);

        textList.setLayoutManager(new LinearLayoutManager(mActivity));
        textList.setAdapter(new RecyclerAdapterWithHF((RecyclerView.Adapter) textAdapter));

        ptrFrameLayout.setAutoLoadMoreEnable(true);
        ptrFrameLayout.setLoadMoreEnable(true);
        LoadMoreViewFooter loadMoreViewFactory = new LoadMoreViewFooter();
        ptrFrameLayout.setFooterView(loadMoreViewFactory);
        ptrFrameLayout.setPullToRefresh(false);
        ptrFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textAdapter.add(5);
                        textAdapter.notifyDataSetChanged();
                        ptrFrameLayout.loadMoreComplete(true);
                    }
                }, 2000);
            }
        });

        RecyclerViewCornerRadius recyclerViewCornerRadius = new RecyclerViewCornerRadius(textList);
        recyclerViewCornerRadius.setCornerRadius(30);

        RecyclerViewLinearDivider recyclerViewLinearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.VERTICAL);
        recyclerViewLinearDivider.setDividerSize(1);
        recyclerViewLinearDivider.setDividerColor(0xff888888);
        recyclerViewLinearDivider.setDividerMargin(30, 30);
        recyclerViewLinearDivider.setDividerBackgroundColor(0xffffffff);
        recyclerViewLinearDivider.setShowHeaderDivider(false);
        recyclerViewLinearDivider.setShowFooterDivider(false);

        textList.addItemDecoration(recyclerViewCornerRadius);
        textList.addItemDecoration(recyclerViewLinearDivider);
    }

    @Override
    public void onTabClick(TabButton tabButton) {
        Toast.makeText(mActivity, "点击了新闻", Toast.LENGTH_SHORT).show();
    }

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
