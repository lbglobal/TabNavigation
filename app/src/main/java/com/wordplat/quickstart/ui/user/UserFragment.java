package com.wordplat.quickstart.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.fragment.HomeTabClickListener;
import com.wordplat.quickstart.ui.news.adapter.TextAdapter;
import com.wordplat.quickstart.widget.TabButton;
import com.wordplat.quickstart.widget.custom.AppBarHeaderBehavior;
import com.wordplat.quickstart.widget.custom.CustomCollapsingToolbarLayout;
import com.wordplat.quickstart.widget.pulllistview.PullListLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * 用户中心
 *
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_user)
public class UserFragment extends BaseFragment implements HomeTabClickListener {

    @ViewInject(R.id.pullList) private PullListLayout pullList = null;
    @ViewInject(R.id.appBar) private AppBarLayout appBar = null;
    @ViewInject(R.id.toolBar) private CustomCollapsingToolbarLayout toolBar = null;
    @ViewInject(R.id.titleLayout) private LinearLayout titleLayout = null;

    @ViewInject(R.id.userImage) private ImageView userImage = null;
    @ViewInject(R.id.textList) private RecyclerView textList = null;

    private AppBarHeaderBehavior behavior;

    private TextAdapter textAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initUI();
    }

    private void initUI() {
        toolBar.setTitleLayout(titleLayout);
        pullList.setPtrHandler(ptrDefaultHandler);

        behavior = (AppBarHeaderBehavior) ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();

        Picasso.with(mActivity)
                .load("http://www.istartedsomething.com/bingimages/resize.php?i=EifelNPBelgium_EN-US13320978952_1366x768.jpg&w=1080")
                .into(userImage);

        textAdapter = new TextAdapter(mActivity);

        textList.setLayoutManager(new LinearLayoutManager(mActivity));
        textList.setAdapter(textAdapter);

        RecyclerViewLinearDivider recyclerViewLinearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.VERTICAL);
        recyclerViewLinearDivider.setDividerSize(1);
        recyclerViewLinearDivider.setDividerColor(0xff888888);
        recyclerViewLinearDivider.setDividerMargin(30, 90);
        recyclerViewLinearDivider.setDividerBackgroundColor(0xffffffff);
        recyclerViewLinearDivider.setShowHeaderDivider(false);
        recyclerViewLinearDivider.setShowFooterDivider(false);

        textList.addItemDecoration(recyclerViewLinearDivider);
    }

    @Override
    public void onTabClick(TabButton tabButton) {
        Toast.makeText(mActivity, "点击了我的", Toast.LENGTH_SHORT).show();
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

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
