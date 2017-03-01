package com.wordplat.quickstart.ui.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by afon on 2017/2/5.
 */

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {

    private static List<String> imageList = new ArrayList<>();

    static {
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=WinterOwls_EN-US11633542284_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=CabinetClimber_EN-US9427872819_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=CarWash_EN-US12345682830_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=LakeWakapitu_EN-US11634817642_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=MacawFlight_EN-US9275204017_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=RoyalBarge_EN-US7484780716_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=CalevCoyote_EN-US7129927657_1366x768.jpg&w=300");
    }

    private Activity mActivity;

    public HorizontalAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_home_horizontal, parent, false);

        return new HorizontalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        Picasso.with(mActivity)
                .load(imageList.get(position))
                .placeholder(R.mipmap.image_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
