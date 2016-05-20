package com.mllweb.thing.ui.adapter;

import android.app.Activity;
import android.widget.ImageView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.thing.R;

import java.util.List;

/**
 * Created by Android on 2016/5/20.
 */
public class ChooseFileAdapter extends BaseRecyclerAdapter<String> {
    public ChooseFileAdapter(List<String> mData, Activity activity) {
        super(mData, activity);
        mData.add(0, "");
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_file;
    }

    @Override
    protected void onBind(BaseHolder holder, String filePath) {
        ImageView iv = holder.getView(R.id.iv_image);
        if (holder.getPosition() == 0) {
            iv.setImageResource(R.drawable.take_photo);
        } else {
            ImageLoader.getInstance().loadImage(filePath, iv);
        }
    }
}
