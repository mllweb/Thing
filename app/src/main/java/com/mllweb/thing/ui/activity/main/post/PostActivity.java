package com.mllweb.thing.ui.activity.main.post;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mllweb.model.Thing;
import com.mllweb.model.ThingFile;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.post.PostImageAdapter;
import com.mllweb.thing.ui.view.dialog.BackDialog;
import com.mllweb.thing.ui.view.dialog.LinkDialog;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class PostActivity extends BaseActivity implements LinkDialog.OnAddListener {
    @InjectView(R.id.et_content)
    @NotEmpty(message = "请输入帖子内容")
    EditText mContent;
    @InjectView(R.id.tv_link)
    TextView mLink;
    @InjectView(R.id.link_layout)
    RelativeLayout mLinkLayout;
    @InjectView(R.id.iv_close_link)
    ImageView mCloseLink;
    @InjectView(R.id.post_layout)
    LinearLayout mPostLayout;
    @InjectView(R.id.image_list)
    RecyclerView mImageListView;
    private final int imageCount = 9;
    private PostImageAdapter mImageAdapter;
    private List<File> mData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_post;
    }

    @OnClick(R.id.tv_publish)
    public void clickPublish() {
        mValidator.validate();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mImageAdapter = new PostImageAdapter(mData, mActivity);
        mImageListView.setLayoutManager(new GridLayoutManager(mActivity, 4));
        mImageListView.setAdapter(mImageAdapter);
    }


    @Override
    protected void clickBack(View v) {
        if (mContent.getText().toString().trim().equals("")) {
            finish();
        } else {
            BackDialog dialog = new BackDialog();
            dialog.setOnBackListener(back -> {
                if (back) {
                    finish();
                } else {
                    dialog.dismiss();
                }
            });
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void onValidationSucceeded() {
        Thing thing = new Thing();
        thing.setContent(mContent.getText().toString());
        List<ThingFile> list = new ArrayList<>();
        if (!mLink.getText().toString().equals("")) {
            thing.setLink(mLink.getText().toString());
        }
        if (mData != null && mData.size() > 0) {
            for (int i = 0; i < mData.size(); i++) {
                list.add(new ThingFile(1, mData.get(i).getAbsolutePath()));
            }
        }
        thing.setThingFiles(list);
        Intent intent = new Intent(mActivity, ChooseTopicActivity.class);
        intent.putExtra("thing", (Serializable) thing);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            clickBack(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.choose_image)
    public void clickChooseImage() {
        if (mData.size() >= 9) {
            Utils.toast(mActivity, String.format("最多选择%d张", imageCount));
        } else {
            Intent intent = new Intent(mActivity, ChooseFileActivity.class);
            intent.putExtra("count", imageCount - mData.size());
            startActivityForResult(intent, 0);
        }
    }

    @OnClick(R.id.choose_link)
    public void clickChooseLink() {
        LinkDialog dialog = new LinkDialog();
        dialog.setOnAddListener(this);
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            File[] files = (File[]) data.getSerializableExtra("files");
            for (int i = 0; i < files.length; i++) {
                if (files[i] != null) {
                    mData.add(files[i]);
                }
            }
            mImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void add(String link) {
        mLinkLayout.setVisibility(View.VISIBLE);
        mLink.setText(link);
    }

    @OnClick(R.id.iv_close_link)
    public void cliclCloseLink() {
        mLinkLayout.setVisibility(View.GONE);
        mLink.setText("");
    }


}
