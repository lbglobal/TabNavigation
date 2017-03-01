package com.wordplat.quickstart.widget.custom;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * 适用于有垂直滑动的布局下识别横向滑动用的
 *
 * 这里面的 {@link #onTouchEvent} 方法源至 {@link android.support.v4.view.GestureDetectorCompat.GestureDetectorCompatImplBase#onTouchEvent}
 * 可以看作是 GestureDetectorCompat 的阉割版，本类只识别到横向滑动的 onScroll、 onFling、onClick(onSingleTab) ,不能用于识别垂直滑动的 onScroll、 onFling、onClick(onSingleTab)
 *
 * Created by afon on 2017/1/23.
 */

public class GestureMoveDetectorCompat extends GestureMoveActionCompat {
    public static final String TAG = "GestureMoveDetector";

    private OnGestureDetectListener mGestureDetectListener;

    private int mTouchSlopSquare;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;

    private boolean mAlwaysInTapRegion;

    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;

    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;

    public GestureMoveDetectorCompat(Context context, OnGestureDetectListener onGestureDetectListener) {
        super(onGestureDetectListener);
        enableClick(false);

        mGestureDetectListener = onGestureDetectListener;

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        final int touchSlop = configuration.getScaledTouchSlop();
        mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        mTouchSlopSquare = touchSlop * touchSlop;
    }

    public boolean onTouchEvent(MotionEvent e) {
        final int action = e.getAction();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(e);

        final boolean pointerUp =
                (action & MotionEventCompat.ACTION_MASK) == MotionEventCompat.ACTION_POINTER_UP;
        final int skipIndex = pointerUp ? MotionEventCompat.getActionIndex(e) : -1;

        // Determine focal point
        float sumX = 0, sumY = 0;
        final int count = MotionEventCompat.getPointerCount(e);
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) continue;
            sumX += MotionEventCompat.getX(e, i);
            sumY += MotionEventCompat.getY(e, i);
        }
        final int div = pointerUp ? count - 1 : count;
        final float focusX = sumX / div;
        final float focusY = sumY / div;

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;

                // Check the dot product of current velocities.
                // If the pointer that left was opposing another velocity vector, clear.
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                final int upIndex = MotionEventCompat.getActionIndex(e);
                final int id1 = MotionEventCompat.getPointerId(e, upIndex);
                final float x1 = VelocityTrackerCompat.getXVelocity(mVelocityTracker, id1);
                final float y1 = VelocityTrackerCompat.getYVelocity(mVelocityTracker, id1);
                for (int i = 0; i < count; i++) {
                    if (i == upIndex) continue;

                    final int id2 = MotionEventCompat.getPointerId(e, i);
                    final float x = x1 * VelocityTrackerCompat.getXVelocity(mVelocityTracker, id2);
                    final float y = y1 * VelocityTrackerCompat.getYVelocity(mVelocityTracker, id2);

                    final float dot = x + y;
                    if (dot < 0) {
                        mVelocityTracker.clear();
                        break;
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                mDownFocusX = mLastFocusX = focusX;
                mDownFocusY = mLastFocusY = focusY;
                if (mCurrentDownEvent != null) {
                    mCurrentDownEvent.recycle();
                }
                mCurrentDownEvent = MotionEvent.obtain(e);
                mAlwaysInTapRegion = true;

                if (mGestureDetectListener != null) {
                    mGestureDetectListener.onDown(e, e.getRawX(), e.getRawY());
                }
                break;

            case MotionEvent.ACTION_MOVE:
                final float scrollX = mLastFocusX - focusX;
                final float scrollY = mLastFocusY - focusY;
                if (mAlwaysInTapRegion) {
                    final int deltaX = (int) (focusX - mDownFocusX);
                    final int deltaY = (int) (focusY - mDownFocusY);
                    int distance = (deltaX * deltaX) + (deltaY * deltaY);
                    if (distance > mTouchSlopSquare) {
                        if (mGestureDetectListener != null) {
                            mGestureDetectListener.onScroll(mCurrentDownEvent, e, scrollX, scrollY);
                        }

                        mLastFocusX = focusX;
                        mLastFocusY = focusY;
                        mAlwaysInTapRegion = false;
                    }
                } else if ((Math.abs(scrollX) >= 1) || (Math.abs(scrollY) >= 1)) {
                    if (mGestureDetectListener != null) {
                        mGestureDetectListener.onScroll(mCurrentDownEvent, e, scrollX, scrollY);
                    }
                    mLastFocusX = focusX;
                    mLastFocusY = focusY;
                }
                break;

            case MotionEvent.ACTION_UP:
                MotionEvent currentUpEvent = MotionEvent.obtain(e);
                if (mAlwaysInTapRegion) {
                    mGestureDetectListener.onClick(e, e.getRawX(), e.getRawY());
                } else {
                    // A fling must travel the minimum tap distance
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    final int pointerId = MotionEventCompat.getPointerId(e, 0);
                    velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                    final float velocityY = VelocityTrackerCompat.getYVelocity(
                            velocityTracker, pointerId);
                    final float velocityX = VelocityTrackerCompat.getXVelocity(
                            velocityTracker, pointerId);

                    if ((Math.abs(velocityY) > mMinimumFlingVelocity)
                            || (Math.abs(velocityX) > mMinimumFlingVelocity)){
                        if (mGestureDetectListener != null) {
                            mGestureDetectListener.onFling(mCurrentDownEvent, e, velocityX, velocityY);
                        }
                    }
                }
                if (mPreviousUpEvent != null) {
                    mPreviousUpEvent.recycle();
                }
                // Hold the event we obtained above - listeners may have changed the original.
                mPreviousUpEvent = currentUpEvent;
                if (mVelocityTracker != null) {
                    // This may have been cleared when we called out to the
                    // application above.
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                mAlwaysInTapRegion = false;
                break;
        }

        return super.onTouchEvent(e, e.getRawX(), e.getRawY());
    }

    public interface OnGestureDetectListener extends OnGestureMoveListener {

        /**
         * Notified when a tap occurs with the down {@link MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every down event. All other events should be preceded by this.
         *
         * @param e The down motion event.
         * @param x 本次事件的坐标 x。实际是 e.getRawX() 获取的值
         * @param y 本次事件的坐标 y。实际是 e.getRawY() 获取的值
         */
        void onDown(MotionEvent e, float x, float y);

        /**
         * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
         * current move {@link MotionEvent}. The distance in x and y is also supplied for
         * convenience.
         *
         * @param e1 The first down motion event that started the scrolling.
         * @param e2 The move motion event that triggered the current onScroll.
         * @param distanceX The distance along the X axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         * @param distanceY The distance along the Y axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         */
        void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        /**
         * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
         * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
         * the x and y axis in pixels per second.
         *
         * @param e1 The first down motion event that started the fling.
         * @param e2 The move motion event that triggered the current onFling.
         * @param velocityX The velocity of this fling measured in pixels per second
         *              along the x axis.
         * @param velocityY The velocity of this fling measured in pixels per second
         *              along the y axis.
         */
        void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    }
}
