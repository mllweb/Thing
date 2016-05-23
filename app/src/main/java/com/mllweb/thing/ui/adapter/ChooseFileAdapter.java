package com.mllweb.thing.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
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
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhoto();
                }
            });
        } else {
            check.setVisibility(View.VISIBLE);
            iv.setOnClickListener(null);
            ImageLoader.getInstance().loadImage(filePath, iv);
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(intent, 1);
    }
}
