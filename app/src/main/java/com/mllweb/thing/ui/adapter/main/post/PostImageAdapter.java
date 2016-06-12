package com.mllweb.thing.ui.adapter.main.post;

import android.app.Activity;
import android.widget.ImageView;

import com.mllweb.loader.ImageLoader;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by Android on 2016/6/12.
 */
public class PostImageAdapter extends BaseRecyclerAdapter<File> {
    private ImageLoader imageLoader;

    public PostImageAdapter(List<File> mData, Activity activity) {
        super(mData, activity);
        imageLoader = ImageLoader.getInstance();
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_post_image;
    }

    @Override
    protected void onBind(BaseHolder holder, File file) {
        ImageView image = holder.getView(R.id.image);
        ImageView remove = holder.getView(R.id.remove);
        imageLoader.loadImage(file.getAbsolutePath(), image);
        remove.setOnClickListener(v -> {
            remove(holder.getPosition());
        });
    }
}
