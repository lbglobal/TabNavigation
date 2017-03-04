package com.wordplat.quickstart.ui.discovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wordplat.easydivider.RecyclerViewGridDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.fragment.HomeTabClickListener;
import com.wordplat.quickstart.ui.discovery.adapter.TextAdapter;
import com.wordplat.quickstart.utils.AppUtils;
import com.wordplat.quickstart.widget.TabButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 发现
 *
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_find)
public class FindFragment extends BaseFragment implements HomeTabClickListener {

    @ViewInject(R.id.gridList1) private RecyclerView gridList1 = null;
    @ViewInject(R.id.gridList2) private RecyclerView gridList2 = null;
    @ViewInject(R.id.gridList3) private RecyclerView gridList3 = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI1();
        initUI2();
        initUI3();
    }

    private void initUI1() {
        TextAdapter textAdapter = new TextAdapter(mActivity, 4);
        gridList1.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList1.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        gridList1.addItemDecoration(recyclerViewGridDivider);
    }

    private void initUI2() {
        TextAdapter textAdapter = new TextAdapter(mActivity, 6);
        gridList2.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList2.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setRowDividerMargin(60, 60);
        recyclerViewGridDivider.setColDividerMargin(30, 30);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(false);
        recyclerViewGridDivider.setShowTopDivider(false);
        recyclerViewGridDivider.setShowBottomDivider(false);
        recyclerViewGridDivider.setFillItemDivider(true);
        gridList2.addItemDecoration(recyclerViewGridDivider);
    }

    private void initUI3() {
        TextAdapter textAdapter = new TextAdapter(mActivity, 6);
        gridList3.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList3.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setRowDividerMargin(30, 30);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(false);
        recyclerViewGridDivider.setFillItemDivider(true);
        gridList3.addItemDecoration(recyclerViewGridDivider);
    }

    @Override
    public void onTabClick(TabButton tabButton) {
        Toast.makeText(mActivity, "点击了发现", Toast.LENGTH_SHORT).show();
    }

    public static FindFragment newInstance() {

        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
