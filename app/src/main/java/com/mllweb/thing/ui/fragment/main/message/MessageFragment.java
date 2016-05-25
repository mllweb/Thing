package com.mllweb.thing.ui.fragment.main.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.mllweb.model.Message;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.main.message.ChatActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.main.message.MessageAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
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
        mHttp.requestGet("http://192.168.1.191:8080/Thing/SelectMessage", new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response,String body) {
                try {
                    JSONArray jsonArray = new JSONArray(body);
                    mMessageList.add(new Gson().fromJson(jsonArray.get(0).toString(), Message.class));
                    mMessageList.add(new Gson().fromJson(jsonArray.get(1).toString(), Message.class));
                    mMessageList.add(new Gson().fromJson(jsonArray.get(2).toString(), Message.class));
                    mMessageAdapter.resetData(mMessageList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
