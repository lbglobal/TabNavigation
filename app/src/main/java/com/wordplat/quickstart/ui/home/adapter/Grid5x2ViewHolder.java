package com.wordplat.quickstart.ui.home.adapter;

import android.view.View;
import android.widget.Button;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/5.
 */

public class Grid5x2ViewHolder extends BaseViewHolder {

    @ViewInject(R.id.actionBut) Button actionBut;

    public Grid5x2ViewHolder(View itemView) {
        super(itemView);
    }
}
