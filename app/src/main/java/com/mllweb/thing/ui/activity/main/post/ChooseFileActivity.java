package com.mllweb.thing.ui.activity.main.post;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mllweb.model.ImageFloder;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.post.ChooseFileAdapter;
import com.mllweb.thing.ui.adapter.popup.ChooseImageAdapter;
import com.mllweb.thing.ui.view.popup.ChooseImagePopup;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseFileActivity extends BaseActivity {
    @InjectView(R.id.file_list)
    RecyclerView mFileListView;
    @InjectView(R.id.tv_image_dir)
    TextView mImageDir;
    @InjectView(R.id.mask)
    View mMask;
    private ChooseFileAdapter mChooseFileAdapter;
    private final int mColumnCount = 3;
    private ChooseImagePopup mDirPopup;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    private int mPhotoCount;
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<String> mData = new ArrayList<>();
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageFloder floder = new ImageFloder();
            floder.setDir("/所有图片");
            floder.setCount(mPhotoCount);
            floder.setFirstImagePath(mImageFloders.get(0).getFirstImagePath());
            mImageFloders.add(0, floder);
            setImageData("");
        }

    };

    @Override
    protected int initLayout() {
        return R.layout.activity_choose_file;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDirPopup = new ChooseImagePopup(mActivity, mImageFloders);
        getImages();
    }

    @Override
    protected void initEvent() {
        mDirPopup.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideMask();
            }
        });
        mDirPopup.getmChooseImageAdapter().setOnCheckChangeListener(new ChooseImageAdapter.OnCheckChangeListener() {
            @Override
            public void checkChange(String path) {
                setImageData(path);
                hideMask();
                mDirPopup.getPopupWindow().dismiss();
            }
        });
    }

    @OnClick(R.id.tv_confirm)
    public void clickConfirm() {
        int position = 1;
        Iterator<Integer> keys = mChooseFileAdapter.checkMap.keySet().iterator();
        while (keys.hasNext()) {
            int key = keys.next();
            boolean value = mChooseFileAdapter.checkMap.get(key);
            if (value) {
                position = key;
                break;
            }
        }
        Intent intent = getIntent();
        intent.putExtra("file", new File(mData.get(position)));
        setResult(0, intent);
        finish();
    }

    private void setImageData(String path) {
        mData.clear();
        if (mGruopMap.containsKey(path)) {
            List<String> list = mGruopMap.get(path);
            mData.addAll(list);
        } else {
            Iterator<String> iterator = mGruopMap.keySet().iterator();
            while (iterator.hasNext()) {
                List<String> list = mGruopMap.get(iterator.next());
                mData.addAll(list);
            }
        }
        if (mChooseFileAdapter == null) {
            mChooseFileAdapter = new ChooseFileAdapter(mData, mActivity);
            mFileListView.setLayoutManager(new GridLayoutManager(mActivity, mColumnCount));
            mFileListView.setAdapter(mChooseFileAdapter);
        } else {
            mChooseFileAdapter.resetData(mData);
        }
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    ContentResolver contentResolver = getContentResolver();
                    // 只查询jpeg和png的图片
                    Cursor cursor = contentResolver.query(imageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                    while (cursor.moveToNext()) {
                        mPhotoCount++;
                        // 获取图片的路径
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        // 获取该图片的父路径名
                        File parentFile = new File(path).getParentFile();
                        if (parentFile == null) continue;
                        String dirPath = parentFile.getAbsolutePath();
                        ImageFloder imageFloder = null;
                        // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                        if (mGruopMap.containsKey(dirPath)) {
                            List<String> list = mGruopMap.get(dirPath);
                            list.add(path);
                        } else {
                            List<String> list = new ArrayList<String>();
                            list.add(path);
                            mGruopMap.put(dirPath, list);
                        }
                        if (mDirPaths.contains(dirPath)) {
                            continue;
                        } else {
                            mDirPaths.add(dirPath);
                            // 初始化imageFloder
                            imageFloder = new ImageFloder();
                            imageFloder.setDir(dirPath);
                            imageFloder.setFirstImagePath(path);
                        }
                        int picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg"))
                                    return true;
                                return false;
                            }
                        }).length;
                        imageFloder.setCount(picSize);
                        mImageFloders.add(imageFloder);
                    }
                    cursor.close();
                    // 扫描完成，辅助的HashSet也就可以释放内存了
                    mDirPaths = null;
                    // 通知Handler扫描图片完成
                    mHandler.sendEmptyMessage(0x110);

                }
            }).start();
        }
    }

    @OnClick(R.id.tv_image_dir)
    public void clickImageDir() {
        if (mDirPopup.getPopupWindow().isShowing()) {
            hideMask();
            mDirPopup.getPopupWindow().dismiss();
        } else {
            showMask();
            mDirPopup.showAsDropDown(mImageDir);
        }

    }

    @OnClick(R.id.mask)
    public void clickMask() {
        hideMask();
        mDirPopup.getPopupWindow().dismiss();
    }

    /**
     * 显示渐变背景
     */
    private void showMask() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(500);
        mMask.setAnimation(animation);
        mMask.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏渐变背景
     */
    private void hideMask() {
        AlphaAnimation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        mMask.setAnimation(animation);
        mMask.setVisibility(View.GONE);
    }
}
