package com.wordplat.quickstart.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/5.
 */

public class HorizontalViewHolder extends BaseViewHolder {

    @ViewInject(R.id.image) ImageView image;

    public HorizontalViewHolder(View itemView) {
        super(itemView);
    }
}
