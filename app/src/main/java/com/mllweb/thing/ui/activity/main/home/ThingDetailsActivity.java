package com.mllweb.thing.ui.activity.main.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.mllweb.model.Comment;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.home.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ThingDetailsActivity extends BaseActivity {
    @InjectView(R.id.comment_list)
    RecyclerView mCommentListView;
    private RecyclerViewHeader mCommentListViewHeader;
    private CommentAdapter mCommmentAdapter;
    private List<Comment> mCommentData = new ArrayList<>();

    @Override
    protected int initLayout() {
        mCommentListViewHeader = RecyclerViewHeader.fromXml(this, R.layout.adapter_comment_header);
        return R.layout.activity_thing_details;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 20; i++) {
            mCommentData.add(new Comment());
        }
        mCommmentAdapter = new CommentAdapter(mCommentData, mActivity);
        mCommentListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mCommentListViewHeader.attachTo(mCommentListView);
        mCommentListView.setAdapter(mCommmentAdapter);
    }
}
