package com.mllweb.thing.ui.activity.main.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.message.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ChatActivity extends BaseActivity {
    @InjectView(R.id.chat_list)
    RecyclerView mChatListView;
    private ChatAdapter mChatAdapter;
    private List<Object> mData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");mData.add("");
        mChatAdapter = new ChatAdapter(mData, mActivity);
        mChatListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mChatListView.setAdapter(mChatAdapter);
    }
}
