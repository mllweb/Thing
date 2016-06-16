package com.mllweb.thing.ui.activity.main.message;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.mllweb.model.Message;
import com.mllweb.model.MessageLog;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.receiver.ChatReceiver;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.message.ChatAdapter;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {
    @InjectView(R.id.chat_list)
    RecyclerView mChatListView;
    @InjectView(R.id.et_edit)
    @NotEmpty(message = "不能为空")
    EditText mEditText;
    @InjectView(R.id.tv_send)
    TextView mSend;
    @InjectView(R.id.tv_chat_nick_name)
    TextView mChatNickName;
    private UserInfo mChatUser;
    private UserInfo mCurrentUser;
    private ChatAdapter mChatAdapter;
    private List<MessageLog> mData = new ArrayList<>();
    private ChatReceiver receiver = new ChatReceiver();

    @Override
    protected int initLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        registerReceiver(receiver, new IntentFilter(ChatReceiver.ACTION));
        mCurrentUser = UserInfoManager.get(mActivity);
        mChatUser = (UserInfo) getIntent().getSerializableExtra("user");
        mChatNickName.setText(mChatUser.getNickName());
        mRealm.clearMessageCount(mChatUser.getId());
        getMessageList();
    }

    @Override
    protected void initEvent() {
        receiver.setOnChatListener(intent -> {
            UserInfo user = (UserInfo) intent.getSerializableExtra("user");
            if (user.getMobile().equals(mChatUser.getMobile())) {
                String content = intent.getStringExtra("content");
                MessageLog log = new MessageLog();
                log.setToUserId(mCurrentUser.getId());
                log.setContent(content);
                log.setFile(content.getBytes());
                log.setFromMobile(user.getMobile());
                log.setFromUserHeadImage(user.getHeadImage());
                log.setFromNickName(user.getNickName());
                log.setFromUserId(user.getId());
                log.setSendDate(System.currentTimeMillis());
                log.setToMobile(mCurrentUser.getMobile());
                log.setToNickName(mCurrentUser.getNickName());
                log.setType(1);
                mChatAdapter.addData(log);
                mChatListView.smoothScrollToPosition(mData.size());
                mRealm.clearMessageCount(mChatUser.getId());
            }else{

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * 获取聊天信息列表
     */
    private void getMessageList() {
        mData = mRealm.selectMessageLog(mChatUser.getMobile(), mCurrentUser.getMobile());
        mChatAdapter = new ChatAdapter(mData, mActivity);
        mChatListView.setLayoutManager(new LinearLayoutManager(mActivity));
        mChatListView.setItemAnimator(new DefaultItemAnimator());
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.smoothScrollToPosition(mData.size());
    }


    @OnClick(R.id.tv_send)
    public void clickSend() {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        sendService(null, 1);
        sendEaseIM();
        insertLocal(null, 1);
        mEditText.setText("");
    }

    private void sendService(String filePath, int type) {
        mHttp.requestPostDomain(API.INSERT_MESSAGE_LOG, new OkHttpClientManager.RequestCallback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response, String body) {

                    }
                }, OkHttpClientManager.Params.get("content", mEditText.getText().toString())
                , OkHttpClientManager.Params.get("filePath", filePath)
                , OkHttpClientManager.Params.get("fromUserId", mCurrentUser.getId() + "")
                , OkHttpClientManager.Params.get("toUserId", mChatUser.getId() + "")
                , OkHttpClientManager.Params.get("type", type + ""));
    }

    private void sendEaseIM() {
        EMMessage message = EMMessage.createTxtSendMessage(mEditText.getText().toString(), mChatUser.getMobile());
        message.setChatType(EMMessage.ChatType.Chat);
        message.setMsgTime(new Date().getTime());
        message.setAttribute("nickName", mCurrentUser.getNickName());
        message.setAttribute("userId", mCurrentUser.getId());
        message.setAttribute("headImage", mCurrentUser.getHeadImage());
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    private void insertLocal(byte[] file, int type) {
        MessageLog log = new MessageLog();
        log.setToUserId(mChatUser.getId());
        log.setContent(mEditText.getText().toString());
        log.setFile(file);
        log.setFilePath(null);
        log.setFromMobile(mCurrentUser.getMobile());
        log.setFromUserHeadImage(mCurrentUser.getHeadImage());
        log.setFromNickName(mCurrentUser.getNickName());
        log.setFromUserId(mCurrentUser.getId());
        log.setSendDate(System.currentTimeMillis());
        log.setToMobile(mChatUser.getMobile());
        log.setToNickName(mChatUser.getNickName());
        log.setType(type);
        mRealm.insertMessageLog(log);
        Message msg = new Message();
        msg.setUnreadCount(0);
        msg.setFromMobile(mChatUser.getMobile());
        msg.setFromUserId(mChatUser.getId());
        msg.setFromNickName(mChatUser.getNickName());
        msg.setFromHeadImage(mChatUser.getHeadImage());
        msg.setLastSendContent(mEditText.getText().toString());
        msg.setLastSendDate(System.currentTimeMillis());
        mRealm.insertMessage(msg);
        mChatAdapter.addData(log);
        mChatListView.smoothScrollToPosition(mData.size());
    }
}
