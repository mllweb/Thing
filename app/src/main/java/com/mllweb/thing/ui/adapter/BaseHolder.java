package com.mllweb.thing.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android on 2016/5/12.
 */
public class BaseHolder extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    private Map<Integer, View> mViews = new HashMap<>();

    public <T extends View> T getView(int resId) {
        return (T) getViewByMap(resId);
    }

    public View getItemView() {
        return itemView;
    }

    private View getViewByMap(int resId) {
        if (mViews.containsKey(resId)) {
            return mViews.get(resId);
        } else {
            View v = itemView.findViewById(resId);
            mViews.put(resId, v);
            return v;
        }
    }

    public void setText(int resId, String text) {
        TextView tv = getView(resId);
        tv.setText(text);
    }

    public void setText(int resId, int stringId) {
        TextView tv = getView(resId);
        tv.setText(stringId);
    }

    public interface OnItemClickListener {
        void itemClick(View rootView, int position);
    }
}
