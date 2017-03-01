package com.wordplat.quickstart.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.utils.AppUtils;

/**
 * 自定义一个假的 状态栏
 *
 * Created by afon on 2016/12/29.
 */

public class CustomStatusBarView extends View {
    public static final String TAG = "CustomStatusBarView";

    private int statusBarHeight;
    private boolean showAlways = false;
    private int showAfterSdkVersionInt = 123454321;

    public CustomStatusBarView(Context context) {
        this(context, null);
    }

    public CustomStatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomStatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomStatusBarView, defStyleAttr, defStyleAttr);

        try {
            showAlways = a.getBoolean(R.styleable.CustomStatusBarView_showAlways, showAlways);

            if (!showAlways) {
                showAfterSdkVersionInt = a.getInt(R.styleable.CustomStatusBarView_showAfterSdkVersionInt, showAfterSdkVersionInt);
            }
        } finally {
            a.recycle();
        }

        initUI();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int h = resolveSize(statusBarHeight, heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    private void initUI() {
        if (isInEditMode()) {
            statusBarHeight = 0;
            return ;
        }

        if (!showAlways && Build.VERSION.SDK_INT < showAfterSdkVersionInt) {
            statusBarHeight = 0;
            return ;
        }

        statusBarHeight = getStatusBarHeight(getContext());

        setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        // 获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            // 根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        if (statusBarHeight == -1) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (statusBarHeight == -1) {
            statusBarHeight = AppUtils.dpTopx(context, 25);
        }

        return statusBarHeight;
    }
}
