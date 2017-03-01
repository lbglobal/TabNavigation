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

public class Grid3x3Adapter extends RecyclerView.Adapter<Grid3x3ViewHolder> {

    private static List<String> imageList = new ArrayList<>();

    static {
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=TrakaiIslandCastle_EN-US13260881447_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=RossFountain_EN-US11490955168_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=EifelNPBelgium_EN-US13320978952_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=NASAEgypt_EN-US11074181873_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=TempleOfValadier_EN-US13731018326_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=MacaquesWulingyuan_EN-US8705472129_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=KongdeRi_EN-US11829528696_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=GreatCourt_EN-US11131065922_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=YerbaBuenaGardens_EN-US14307470964_1366x768.jpg&w=300");
    }

    private Activity mActivity;

    public Grid3x3Adapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public Grid3x3ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_home_grid3x3, parent, false);

        return new Grid3x3ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Grid3x3ViewHolder holder, int position) {
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
