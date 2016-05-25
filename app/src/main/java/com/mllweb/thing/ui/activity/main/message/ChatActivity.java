package com.mllweb.thing.ui.activity.main.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.mllweb.model.MessageLog;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.message.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ChatActivity extends BaseActivity {
    @InjectView(R.id.chat_list)
    RecyclerView mChatListView;
    @InjectView(R.id.et_edit)
    EditText mEditText;
    private ChatAdapter mChatAdapter;
    private List<MessageLog> mData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mData.add(new MessageLog("小哩", "/IMAGE/a.jpg", "欢迎来到那点事儿，我是官方助手小哩，有什么事尽管问我哦", 0));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "你好呀，小哩", 1));
        mData.add(new MessageLog("小哩", "/IMAGE/a.jpg", "你好", 0));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "你多大，小哩", 1));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "男的女的，小哩", 1));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "爱好啥呀，小哩", 1));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "嘿嘿", 1));
        mData.add(new MessageLog("小哩", "/IMAGE/a.jpg", "你真烦", 0));
        mData.add(new MessageLog("小哩", "/IMAGE/a.jpg", "问这么多问题", 0));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "那我不问啦", 1));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "你都能干啥呀", 1));
        mData.add(new MessageLog("Eveller", "/IMAGE/head_image.jpg", "快说", 1));
        mChatAdapter = new ChatAdapter(mData, mActivity);
        mChatListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mChatListView.setAdapter(mChatAdapter);
    }

    @Override
    protected void initEvent() {
    }
}
