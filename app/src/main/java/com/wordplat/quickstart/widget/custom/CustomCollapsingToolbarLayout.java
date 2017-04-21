package com.wordplat.quickstart.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.wordplat.quickstart.R;

import java.lang.reflect.Field;

/**
 * Created by afon on 2017/1/3.
 */

public class CustomCollapsingToolbarLayout extends CollapsingToolbarLayout {
    public static final String TAG = "CustomCollapsingLayout";

    private int scrimAlpha = 0;
    private int topInset = 0;

    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener;

    private LinearLayout titleLayout;

    public CustomCollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initUI(context);
    }

    private void initUI(Context context) {
        setStatusBarScrim(null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            if (onOffsetChangedListener == null) {
                onOffsetChangedListener = new OffsetUpdateListener();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(onOffsetChangedListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        final ViewParent parent = getParent();
        if (onOffsetChangedListener != null && parent instanceof AppBarLayout) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(onOffsetChangedListener);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        try {
            Field ScrimAlpha = CollapsingToolbarLayout.class.getDeclaredField("mScrimAlpha");
            ScrimAlpha.setAccessible(true);
            scrimAlpha = ScrimAlpha.getInt(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        drawTitleLayout(canvas);
    }

    private class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            setScrimsShown(getHeight() + verticalOffset < getMinimumHeight() * 2 + topInset);
            ViewCompat.postInvalidateOnAnimation(CustomCollapsingToolbarLayout.this);
        }

    }

    private void drawTitleLayout(Canvas canvas) {
        if (titleLayout != null) {
            if (scrimAlpha > 10) { // 为什么不设置为零 因为有的手机将此滑动到顶部的时候 透明度并不一定为零 经过测试全在10一下（也可能有的手机不在），
                titleLayout.setVisibility(VISIBLE);

                titleLayout.getBackground().mutate().setAlpha(scrimAlpha);
            } else {
                titleLayout.setVisibility(GONE);
            }
        }
    }

    public void setTitleLayout(LinearLayout titleLayout) {
        this.titleLayout = titleLayout;

        View statusBar = this.titleLayout.findViewById(R.id.statusBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = CustomStatusBarView.getStatusBarHeight(getContext());
            statusBar.getLayoutParams().height = statusBarHeight;
            statusBar.setVisibility(VISIBLE);

            topInset = statusBarHeight;
        } else {
            statusBar.setVisibility(GONE);

            topInset = 0;
        }
    }
}
