package com.wordplat.quickstart.widget.custom;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by afon on 2017/1/16.
 */

public final class AppBarHeaderBehavior extends AppBarLayout.Behavior {
    public static final String TAG = "AppBarHeaderBehavior";

    private int pinHeight = 0;
    private int titleHeight = 0;

    private int minVerticalOffset = 0;
    private int currentVerticalOffset = 0;

    private RecyclerView currentScrollTarget;

    private ScrollerCompat scroller;

    /**
     * 持有 scroll 监听器 map，自定义的 scroll 监听器同样持有当前 scroll Y 的位置
     */
    private Map<RecyclerView, RecyclerViewScrollListener> scrollListenerMap = new HashMap<>();

    public AppBarHeaderBehavior() {
    }

    public AppBarHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getCurrentVerticalOffset() {
        return currentVerticalOffset;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        boolean ret = super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);

        if (titleHeight == 0 && child.getChildCount() >= 1) {
            titleHeight = child.getChildAt(0).getMinimumHeight();
        }
        if (pinHeight == 0 && child.getChildCount() >= 2 ) {
            pinHeight = child.getChildAt(1).getMeasuredHeight();
        }

        if (minVerticalOffset == 0) {
            minVerticalOffset = -(child.getMeasuredHeight() - titleHeight - pinHeight);

            child.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    currentVerticalOffset = verticalOffset;

                    if (currentVerticalOffset != minVerticalOffset) {
                        scrollToPositionZero(currentScrollTarget);
                    }
                }
            });
        }

        return ret;
    }

    /**
     * 适用于 ViewPager 中多个 RecyclerView 滚动位置的定位
     */
    private void scrollToPositionZero(RecyclerView recyclerView) {
        if (scrollListenerMap.get(recyclerView) != null && scrollListenerMap.get(recyclerView).getScrolledY() <= 0) {
            Set<RecyclerView> sets = scrollListenerMap.keySet();
            for (RecyclerView otherRecyclerView : sets) {
                if (otherRecyclerView == recyclerView) {
                    continue;
                }
                if (scrollListenerMap.get(otherRecyclerView) != null && scrollListenerMap.get(otherRecyclerView).getScrolledY() != 0) {
                    otherRecyclerView.scrollToPosition(0);
                    scrollListenerMap.get(otherRecyclerView).resetScrollY();
                }
            }
        }
    }

    private ScrollerCompat getScroller() {
        ScrollerCompat scroller = null;
        try {
            Field scrollerField = getClass().getSuperclass().getSuperclass().getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scroller = (ScrollerCompat) scrollerField.get(this);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return scroller;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        if (currentVerticalOffset > minVerticalOffset) {
            parent.onStopNestedScroll(target);

            if (scroller == null) {
                scroller = getScroller();
            }
            if (scroller != null) {
                scroller.abortAnimation();
            }
        }

        if (target instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) target;

            if (scrollListenerMap.get(recyclerView) == null) {
                RecyclerViewScrollListener recyclerViewScrollListener = new RecyclerViewScrollListener(parent, child, this);
                scrollListenerMap.put(recyclerView, recyclerViewScrollListener);
                recyclerView.addOnScrollListener(recyclerViewScrollListener);
            }

            if (scrollListenerMap.get(recyclerView) != null) {
                scrollListenerMap.get(recyclerView).setVelocity(0);

                // 这里的意思是当 TabLayout 没有滚动到顶部时，经过实际测试，scrollY 会不能正常归零，所以要手工重置 RecyclerView 的 scrollY 值
                if (currentVerticalOffset > minVerticalOffset) {
                    scrollListenerMap.get(recyclerView).resetScrollY();
                }
            }
        }

        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        // RecyclerView 只有在 TabLayout 滚动到顶部时才继续执行 fling 操作
        final boolean consumed = currentVerticalOffset == minVerticalOffset;

        if (target instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) target;
            currentScrollTarget = recyclerView;

            if (scrollListenerMap.get(recyclerView) != null) {
                scrollListenerMap.get(recyclerView).setVelocity(velocityY);
            }
        }

        onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);

        // 返回 true 让 RecyclerView 不要立即 fling，只有在 TabLayout 滚动到顶部时才继续执行 fling 操作
        return currentVerticalOffset > minVerticalOffset;
    }

    private static class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        private int scrolledY; // 当前滚动的 Y 值
        private boolean dragging;
        private float velocity;
        private WeakReference<CoordinatorLayout> coordinatorLayoutRef;
        private WeakReference<AppBarLayout> childRef;
        private WeakReference<AppBarHeaderBehavior> behaviorWeakReference;

        public RecyclerViewScrollListener(CoordinatorLayout coordinatorLayout, AppBarLayout child, AppBarHeaderBehavior barBehavior) {
            coordinatorLayoutRef = new WeakReference<>(coordinatorLayout);
            childRef = new WeakReference<>(child);
            behaviorWeakReference = new WeakReference<>(barBehavior);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            dragging = newState == RecyclerView.SCROLL_STATE_DRAGGING;
        }

        public void setVelocity(float velocity) {
            this.velocity = velocity;
        }

        public void resetScrollY() {
            scrolledY = 0;
        }

        public int getScrolledY() {
            return scrolledY;
        }

        private void manualFling(RecyclerView recyclerView) {
            if (childRef.get() != null && coordinatorLayoutRef.get() != null && behaviorWeakReference.get() != null) {
                behaviorWeakReference.get().onNestedFling(coordinatorLayoutRef.get(), childRef.get(), recyclerView, 0, velocity * 0.5f, false);
            }
            // 向上 fling 操作时，缩小加速度以获得更平滑的滚动体验
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            scrolledY += dy;

            // 当 TabLayout 滚动到顶部时手工触发 fling 操作
            if (!dragging && scrolledY <= 0) {
                manualFling(recyclerView);
            }
        }
    }
}