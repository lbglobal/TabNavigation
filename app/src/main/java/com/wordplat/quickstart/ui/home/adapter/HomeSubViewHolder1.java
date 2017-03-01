package com.wordplat.quickstart.ui.home.adapter;

import android.view.View;
import android.widget.TextView;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/5.
 */

public class HomeSubViewHolder1 extends BaseViewHolder {

    @ViewInject(R.id.title) TextView title;

    public HomeSubViewHolder1(View itemView) {
        super(itemView);
    }
}
