package com.wordplat.quickstart.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.ui.home.adapter.HomeSubAdapter2;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_homesub2)
public class HomeSubFragment2 extends BaseFragment {

    @ViewInject(R.id.recyclerView) private RecyclerView recyclerView = null;

    private HomeSubAdapter2 adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
    }

    private void initUI() {
        adapter = new HomeSubAdapter2(mActivity);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(adapter);

        RecyclerViewLinearDivider recyclerViewLinearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.VERTICAL);
        recyclerViewLinearDivider.setDividerSize(15);
        recyclerViewLinearDivider.setDividerColor(0xffcccccc);
        recyclerViewLinearDivider.setShowHeaderDivider(false);
        recyclerViewLinearDivider.setShowFooterDivider(false);

        recyclerView.addItemDecoration(recyclerViewLinearDivider);
    }

    public static HomeSubFragment2 newInstance() {

        Bundle args = new Bundle();

        HomeSubFragment2 fragment = new HomeSubFragment2();
        fragment.setArguments(args);
        return fragment;
    }
}
