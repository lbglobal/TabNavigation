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

public class HomeSubAdapter2 extends RecyclerView.Adapter<HomeSubViewHolder2> {

    private Activity mActivity;

    public HomeSubAdapter2(Activity activity) {
        mActivity = activity;
    }

    @Override
    public HomeSubViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_homesub2, parent, false);

        return new HomeSubViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(HomeSubViewHolder2 holder, int position) {
        holder.title.setText("position " + position);
    }

    @Override
    public int getItemCount() {
        return 60;
    }
}
