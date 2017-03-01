package com.wordplat.quickstart.ui.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * 广告轮播图
 *
 * Created by afon on 2017/2/5.
 */

public class AdDataHolder {

    @ViewInject(R.id.imagePager) private ViewPager imagePager;
    @ViewInject(R.id.imageMark) private LinearLayout imageMark;

    private Context mContext;
    private AdImageAdapter imageAdapter;
    private OnItemClickListener onClickListener;

    private int autoPlay = 5000; // 自动播放间隔，单位毫秒

    public AdDataHolder(View itemView) {
        mContext = itemView.getContext();

        x.view().inject(this, itemView);

        imageAdapter = new AdImageAdapter();
    }

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void display(List<String> urlList) {
        if (imageAdapter.getCount() > 0 || urlList == null || urlList.isEmpty()) {
            return;
        }

        for (int i = 0 ; i < urlList.size() ; i++) {
            String url = urlList.get(i);
            final int pos = i;
            if (!TextUtils.isEmpty(url)) {
                ImageView img = new ImageView(mContext);
                img.setTag(url);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onClickListener != null) {
                            onClickListener.onItemClick(pos);
                        }
                    }
                });

                img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Picasso.with(mContext).load(url).placeholder(R.mipmap.image_placeholder).into(img);

                imageAdapter.addItem(img);
            }
        }

        imagePager.setAdapter(imageAdapter);
        int size = imageAdapter.getCount();

        if (size > 1) {
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_ad_image_mark));
                if (i != 0) {
                    imageView.setPadding(AppUtils.dpTopx(mContext, 10), 0, 0, 0);
                } else {
                    imageView.setSelected(true);
                    imageView.setPadding(0, 0, 0, 0);
                }
                imageView.setId(i);
                imageView.setClickable(true);

                imageMark.addView(imageView);
            }

            imagePager.removeCallbacks(run);
            imagePager.postDelayed(run, autoPlay);
            imagePager.addOnPageChangeListener(pageChangeListener);
        }
    }

    private int pos;

    /**
     * 自动轮播
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            pos = imagePager.getCurrentItem();

            if (++pos >= imageAdapter.getCount()) {
                imagePager.setCurrentItem(0);
            } else {
                imagePager.setCurrentItem(pos);
            }
        }
    };

    /**
     * 设置小圆点下标
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageMark.getChildCount(); i++) {
                ImageView imageView = (ImageView) imageMark.getChildAt(i);
                if (i == position) {
                    imageView.setSelected(true);
                } else {
                    imageView.setSelected(false);
                }
            }

            imagePager.removeCallbacks(run);
            imagePager.postDelayed(run, autoPlay);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private static class AdImageAdapter extends PagerAdapter {

        private ArrayList<ImageView> imgList = null;

        public AdImageAdapter() {
            imgList = new ArrayList<>();
        }

        public void addItem(ImageView img) {
            imgList.add(img);
            notifyDataSetChanged();
        }

        public ImageView getItem(int position) {
            return imgList.get(position);
        }

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = imgList.get(position);
            container.addView(img);
            return img;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
