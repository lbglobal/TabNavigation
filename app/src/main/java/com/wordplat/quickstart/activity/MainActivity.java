package com.wordplat.quickstart.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.ui.discovery.FindFragment;
import com.wordplat.quickstart.ui.home.HomeFragment;
import com.wordplat.quickstart.ui.news.NewsFragment;
import com.wordplat.quickstart.ui.user.UserFragment;
import com.wordplat.quickstart.widget.TabButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.home_tabGroup) private RadioGroup home_tabGroup = null;
    @ViewInject(R.id.home_homePageTab) private TabButton home_homePageTab = null;
    @ViewInject(R.id.home_newsTab) private TabButton home_newsTab = null;
    @ViewInject(R.id.home_findTab) private TabButton home_findTab = null;
    @ViewInject(R.id.home_userTab) private TabButton home_userTab = null;

    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private FindFragment findFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null); // 清空保存 fragment 的状态数据，防止低内存时程序被系统回收再打开时 fragment 重叠
        }
        super.onCreate(savedInstanceState);

        initUI();
    }

    private void initUI() {
        setTranslucentStatus(true);

        homeFragment = HomeFragment.newInstance();
        newsFragment = NewsFragment.newInstance();
        findFragment = FindFragment.newInstance();
        userFragment = UserFragment.newInstance();

        home_tabGroup.setOnCheckedChangeListener(onTabCheckedChangeListener);
        home_homePageTab.setOnClickListener(onTabClicklistener);
        home_newsTab.setOnClickListener(onTabClicklistener);
        home_findTab.setOnClickListener(onTabClicklistener);
        home_userTab.setOnClickListener(onTabClicklistener);

        home_userTab.setShowTag(true);

        addFragment(getSupportFragmentManager(), homeFragment);
    }

    /**
     * 由于设置了透明状态栏，不需要再设置状态栏颜色，重写它让它不设置状态栏颜色
     * 引起了兼容性的问题，已经在 fragment 布局里面加了一个假的自定义“状态栏”解决这个问题）
     */
    @Override
    protected void setStatusBarColor(int colorResId) {
    }

    ///////////////////////////////////////////////////////////////////////////
    // 应用内跳转
    ///////////////////////////////////////////////////////////////////////////

    public void goToHomePage() {
        home_tabGroup.clearCheck();
        home_homePageTab.setChecked(true);
    }

    public void goToNews() {
        home_tabGroup.clearCheck();
        home_newsTab.setChecked(true);
    }

    public void goToFind() {
        home_tabGroup.clearCheck();
        home_findTab.setChecked(true);
    }

    public void goToUser() {
        home_tabGroup.clearCheck();
        home_userTab.setChecked(true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 结束：应用内跳转
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // fragment 显示隐藏
    ///////////////////////////////////////////////////////////////////////////

    private void hideAllFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        if (!homeFragment.isHidden()) {
            ft.hide(homeFragment);
        }
        if (!newsFragment.isHidden()) {
            ft.hide(newsFragment);
        }
        if (!findFragment.isHidden()) {
            ft.hide(findFragment);
        }
        if (!userFragment.isHidden()) {
            ft.hide(userFragment);
        }
        ft.commit();
    }

    private void showFragment(FragmentManager fm, Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    private void addFragment(FragmentManager fm, Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_content, fragment);
            ft.commit();
        }
    }

    private View.OnClickListener onTabClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_homePageTab:
                    if (homeFragment.isAdded() && !homeFragment.isHidden()) {
                        homeFragment.onTabClick((TabButton) v);
                    }
                    break;

                case R.id.home_newsTab:
                    if (newsFragment.isAdded() && !newsFragment.isHidden()) {
                        newsFragment.onTabClick((TabButton) v);
                    }
                    break;

                case R.id.home_findTab:
                    if (findFragment.isAdded() && !findFragment.isHidden()) {
                        findFragment.onTabClick((TabButton) v);
                    }
                    break;

                case R.id.home_userTab:
                    if (userFragment.isAdded() && !userFragment.isHidden()) {
                        userFragment.onTabClick((TabButton) v);
                    }
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener onTabCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentManager fm = getSupportFragmentManager();
            hideAllFragment(fm);
            switch (checkedId) {
                case R.id.home_homePageTab:
                    showFragment(fm, homeFragment);
                    break;

                case R.id.home_newsTab:
                    addFragment(fm, newsFragment);
                    showFragment(fm, newsFragment);
                    break;

                case R.id.home_findTab:
                    addFragment(fm, findFragment);
                    showFragment(fm, findFragment);
                    break;

                case R.id.home_userTab:
                    addFragment(fm, userFragment);
                    showFragment(fm, userFragment);
                    break;
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    // 结束：fragment 显示隐藏
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 点击两次退出
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
