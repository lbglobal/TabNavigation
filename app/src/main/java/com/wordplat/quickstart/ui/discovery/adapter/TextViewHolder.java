package com.wordplat.quickstart.ui.news.adapter;

import android.view.View;
import android.widget.TextView;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/10.
 */

public class TextViewHolder extends BaseViewHolder {

    @ViewInject(R.id.text) TextView text;

    public TextViewHolder(View itemView) {
        super(itemView);
    }
}
