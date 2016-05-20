package com.mllweb.thing.ui.adapter.popup;

import android.app.Activity;
import android.widget.ImageView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.model.ImageFloder;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/5/20.
 */
public class ChooseImageAdapter extends BaseRecyclerAdapter<ImageFloder> {
    public ChooseImageAdapter(List<ImageFloder> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_choose_image_dir;
    }

    @Override
    protected void onBind(BaseHolder holder, ImageFloder imageFloder) {
        holder.setText(R.id.tv_dir_name, imageFloder.getName());
        holder.setText(R.id.tv_dir_count, imageFloder.getCount()+"张");
        ImageView cover = holder.getView(R.id.iv_cover);
        ImageLoader loader = ImageLoader.getInstance();
        loader.loadImage(imageFloder.getFirstImagePath(), cover);

    }
}
