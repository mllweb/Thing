package com.mllweb.model;

/**
 * Created by Android on 2016/5/20.
 */
public class Banner {
    private int drawableResId;

    public Banner(int drawableResId) {
        this.drawableResId = drawableResId;
    }

    public int getDrawableResId() {
        return drawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.drawableResId = drawableResId;
    }
}
