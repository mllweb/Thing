package com.mllweb.thing.ui.activity.main.message;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.mllweb.model.MessageLog;
import com.mllweb.model.UserInfo;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.message.ChatAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {
    @InjectView(R.id.chat_list)
    RecyclerView mChatListView;
    @InjectView(R.id.et_edit)
    EditText mEditText;
    @InjectView(R.id.tv_send)
    TextView mSend;
    private UserInfo mChatUser;
    private UserInfo mCurrentUser;
    private ChatAdapter mChatAdapter;
    private List<MessageLog> mData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mCurrentUser = UserInfoManager.get(mCache);
        mChatUser = new UserInfo();
        mChatUser.setUserName("admin");
        getMessageList();
    }

    /**
     * 获取聊天信息列表
     */
    private void getMessageList() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(mChatUser.getUserName());
        if (conversation != null) {
            //获取此会话的所有消息
            List<EMMessage> messages = conversation.getAllMessages();
            // List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
            for (EMMessage m : messages) {
                mData.add(new MessageLog(m.getFrom(), m.getTo(), mChatUser.getHeadImage(), m.getBody().toString()));
            }
        }
        mChatAdapter = new ChatAdapter(mData, mActivity);
        mChatListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mChatListView.setItemAnimator(new DefaultItemAnimator());
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.smoothScrollToPosition(mData.size());

    }

    @Override
    protected void initEvent() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @OnClick(R.id.tv_send)
    public void clickSend() {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(mEditText.getText().toString(), mChatUser.getUserName());
        //如果是群聊，设置chattype，默认是单聊
        message.setChatType(EMMessage.ChatType.Chat);
        message.setMsgTime(new Date().getTime());
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        mChatAdapter.addData(new MessageLog(mCurrentUser.getUserName(), mChatUser.getUserName(), mChatUser.getHeadImage(), mEditText.getText().toString()));
        mChatListView.smoothScrollToPosition(mData.size());
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            mChatListView.smoothScrollToPosition(mData.size());
            for (EMMessage m : messages) {
                mChatAdapter.addData(new MessageLog(m.getFrom(), m.getTo(), mChatUser.getHeadImage(), m.getBody().toString()));
                mChatListView.smoothScrollToPosition(mData.size());
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息  mChatAdapter.notifyDataSetChanged();
            Log.d("ddddddddddddddddd", "收到透传消息");
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
            Log.d("ddddddddddddddddd", "收到已读回执");
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执

            Log.d("ddddddddddddddddd", "收到已送达回执");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.d("ddddddddddddddddd", "消息状态变动");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
