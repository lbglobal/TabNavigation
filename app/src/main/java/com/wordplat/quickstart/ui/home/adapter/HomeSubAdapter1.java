package com.wordplat.quickstart.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;

/**
 * Created by afon on 2017/2/5.
 */

public class HomeSubAdapter1 extends RecyclerView.Adapter<HomeSubViewHolder1> {

    private Activity mActivity;

    public HomeSubAdapter1(Activity activity) {
        mActivity = activity;
    }

    @Override
    public HomeSubViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_homesub1, parent, false);

        return new HomeSubViewHolder1(itemView);
    }

    @Override
    public void onBindViewHolder(HomeSubViewHolder1 holder, int position) {
        holder.title.setText("position " + position);
    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
