package com.mllweb.thing.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Android on 2016/6/16.
 */
public class ChatReceiver extends BroadcastReceiver {
    public static final String ACTION = "CHAT_MESSAGE_ACTION";
    private OnChatListener listener;

    public void setOnChatListener(OnChatListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.chat(intent);
        }
    }

    public interface OnChatListener {
        void chat(Intent intent);
    }
}
