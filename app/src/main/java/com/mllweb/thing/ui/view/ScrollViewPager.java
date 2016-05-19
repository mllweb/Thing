package com.mllweb.thing.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Android on 2016/5/12.
 */
public class ScrollViewPager extends ViewPager {
    private boolean mIsScroll;
    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isScroll() {
        return mIsScroll;
    }

    public void setScroll(boolean isScroll) {
        this.mIsScroll = mIsScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsScroll == false) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScroll == false) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }

    }
}
