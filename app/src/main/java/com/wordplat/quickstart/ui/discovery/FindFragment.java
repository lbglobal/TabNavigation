package com.wordplat.quickstart.ui.discovery;

import android.os.Bundle;
import android.widget.Toast;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.fragment.BaseFragment;
import com.wordplat.quickstart.fragment.HomeTabClickListener;
import com.wordplat.quickstart.widget.TabButton;

import org.xutils.view.annotation.ContentView;

/**
 * 发现
 *
 * Created by afon on 2017/2/4.
 */

@ContentView(R.layout.fragment_find)
public class FindFragment extends BaseFragment implements HomeTabClickListener {

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
