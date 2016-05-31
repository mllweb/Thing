package com.mllweb.thing.ui.adapter.main.home;

import android.app.Activity;

import com.mllweb.model.Comment;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Android on 2016/5/31.
 */
public class CommentAdapter extends BaseRecyclerAdapter<Comment> {
    public CommentAdapter(List<Comment> mData, Activity activity) {
        super(mData, activity);
    }

    @Override
    protected int onCreate() {
        return R.layout.adapter_comment;
    }

    @Override
    protected void onBind(BaseHolder holder, Comment comment) {

    }
}
