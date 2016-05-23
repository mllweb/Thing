package com.mllweb.thing.ui.adapter.popup;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mllweb.loader.ImageLoader;
import com.mllweb.model.ImageFloder;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2016/5/20.
 */
public class ChooseImageAdapter extends BaseRecyclerAdapter<ImageFloder> {
    private Map<Integer, Boolean> mCheckMap = new HashMap<>();
    private OnCheckChangeListener mCheckChangeListener;
    private RadioGroup mGroup;
    private List<RadioButton> mRadioList = new ArrayList<>();

    public void setOnCheckChangeListener(OnCheckChangeListener checkChangeListener) {
        this.mCheckChangeListener = checkChangeListener;
    }

    public ChooseImageAdapter(List<ImageFloder> mData, Activity activity) {
        super(mData, activity);
        mGroup = new RadioGroup(activity);
        mCheckMap.put(0, true);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_image_dir;
    }

    @Override
    protected void onBind(final BaseHolder holder, final ImageFloder imageFloder) {
        holder.setText(R.id.tv_dir_name, imageFloder.getName());
        holder.setText(R.id.tv_dir_count, imageFloder.getCount() + "张");
        ImageView cover = holder.getView(R.id.iv_cover);
        final RadioButton check = holder.getView(R.id.check);
        mRadioList.add(check);
        ImageLoader loader = ImageLoader.getInstance();
        loader.loadImage(imageFloder.getFirstImagePath(), cover);
        if (mCheckMap.containsKey(holder.getPosition())) {
            check.setChecked(mCheckMap.get(holder.getPosition()));
        } else {
            check.setChecked(false);
        }
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCheckMap.put(holder.getPosition(), true);
                checkChange(check);
                if (mCheckChangeListener != null) {
                    mCheckChangeListener.checkChange(imageFloder.getDir());
                }
            }
        });

    }

    private void checkChange(RadioButton check) {
        for (RadioButton radio : mRadioList) {
            radio.setOnCheckedChangeListener(null);
            if (check != radio) {
                radio.setChecked(false);
            } else {
                radio.setChecked(true);
            }
        }
    }

    public interface OnCheckChangeListener {
        void checkChange(String path);
    }
}
