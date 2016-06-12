package com.mllweb.thing.ui.adapter.main.post;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;
import com.mllweb.thing.utils.Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2016/5/20.
 */
public class ChooseFileAdapter extends BaseRecyclerAdapter<String> {
    public Map<Integer, Boolean> checkMap = new HashMap<>();
    private int count;
    private OnCheckListener mListener;

    public void setOnCheckListener(OnCheckListener listener) {
        mListener = listener;
    }

    public ChooseFileAdapter(List<String> mData, Activity activity, int count) {
        super(mData, activity);
        this.count = count;
        mData.add(0, "");
    }


    public void resetData(List<String> data) {
        if (data != null) {
            mData = data;
            mData.add(0, "");
            notifyDataSetChanged();
        }
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_file;
    }

    @Override
    protected void onBind(BaseHolder holder, String filePath) {
        ImageView iv = holder.getView(R.id.iv_image);
        CheckBox check = holder.getView(R.id.check);
        if (holder.getPosition() == 0) {
            check.setVisibility(View.GONE);
            iv.setImageResource(R.drawable.take_photo);
            iv.setOnClickListener(v -> {
                takePhoto();
            });
        } else {
            check.setVisibility(View.VISIBLE);
            iv.setOnClickListener(null);
            ImageLoader.getInstance().loadImage(filePath, iv);
        }
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMap.put(holder.getPosition(), isChecked);
                int getCount = getCount();
                if (getCount > count) {
                    Utils.toast(mActivity, String.format("最多选择%d张", count));
                    buttonView.setChecked(false);
                    checkMap.put(holder.getPosition(), false);
                    getCount--;
                }
                if (mListener != null) {
                    mListener.check(getCount);
                }
            }
        });
        int position = holder.getPosition();
        if (!checkMap.containsKey(position)) {
            checkMap.put(position, false);
        }
        check.setChecked(checkMap.get(position));
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(intent, 1);
    }

    private int getCount() {
        int count = 0;
        Iterator<Integer> keys = checkMap.keySet().iterator();
        while (keys.hasNext()) {
            int key = keys.next();
            boolean value = checkMap.get(key);
            if (value) {
                count++;
            }
        }
        return count;
    }

    public interface OnCheckListener {
        void check(int count);
    }
}
