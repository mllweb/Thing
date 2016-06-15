package com.mllweb.thing.ui.fragment.main.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mllweb.model.Message;
import com.mllweb.model.UserInfo;
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

        mMessageAdapter = new MessageAdapter(mMessageList, mActivity);
        mMessageView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessageView.setAdapter(mMessageAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRealm.isLogged()) {
            mMessageList = mRealm.selectMessage();
            mMessageAdapter.resetData(mMessageList);
        }else{

        }
    }

    @Override
    protected void initEvent() {
        mMessageAdapter.setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void itemClick(View rootView, int position) {
                Intent intent = new Intent(mActivity, ChatActivity.class);
                Message m = mMessageList.get(position);
                UserInfo user = new UserInfo();
                user.setId(m.getFromUserId());
                user.setMobile(m.getFromMobile());
                user.setHeadImage(m.getFromHeadImage());
                user.setNickName(m.getFromNickName());
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
