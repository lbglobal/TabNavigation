package com.wordplat.quickstart.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.widget.dialog.CouponDialog;

/**
 * Created by afon on 2017/2/5.
 */

public class Grid5x2Adapter extends RecyclerView.Adapter<Grid5x2ViewHolder> {

    private Activity mActivity;

    public Grid5x2Adapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Grid5x2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_home_grid5x2, parent, false);

        return new Grid5x2ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Grid5x2ViewHolder holder, int position) {
        holder.actionBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponDialog.from(mActivity).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
