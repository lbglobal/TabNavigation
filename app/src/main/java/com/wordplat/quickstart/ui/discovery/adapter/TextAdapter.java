package com.wordplat.quickstart.ui.discovery.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;

/**
 * Created by afon on 2017/2/10.
 */

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private Activity mActivity;

    private int count;

    public TextAdapter(Activity activity, int itemCount) {
        mActivity = activity;

        count = itemCount;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_discovery_text, parent, false);

        return new TextViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.text.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
