package com.mllweb.thing.ui.fragment.main.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mllweb.model.Message;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.message.ChatActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.main.message.MessageAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Android on 2016/5/18.
 */
public class MessageFragment extends BaseFragment {
    @InjectView(R.id.message_list)
    RecyclerView mMessageView;
    private MessageAdapter mMessageAdapter;
    private List<Message> mMessageList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData(Bundle arguments) {
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageList.add(new Message());
        mMessageAdapter = new MessageAdapter(mMessageList, mActivity);
        mMessageView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageView.setAdapter(mMessageAdapter);
    }

    @Override
    protected void initEvent() {
        mMessageAdapter.setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void itemClick(View rootView, int position) {
                startActivity(ChatActivity.class);
            }
        });
    }
}
