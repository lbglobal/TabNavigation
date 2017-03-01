package com.wordplat.quickstart.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/5.
 */

public class Grid3x3ViewHolder extends BaseViewHolder {

    @ViewInject(R.id.image) ImageView image;

    public Grid3x3ViewHolder(View itemView) {
        super(itemView);
    }
}
