package com.mllweb.thing.ui.fragment.main.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.mllweb.model.Message;
import com.mllweb.model.UserInfo;
import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.manager.UserInfoManager;
import com.mllweb.thing.ui.activity.main.message.ChatActivity;
import com.mllweb.thing.ui.adapter.BaseHolder;
import com.mllweb.thing.ui.adapter.main.message.MessageAdapter;
import com.mllweb.thing.ui.fragment.BaseFragment;
import com.mllweb.thing.utils.Utils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        if (mRealm.isLogged()) {
            mHttp.requestPostDomain(API.SELECT_MESSAGE, new OkHttpClientManager.RequestCallback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response, String body) {
                    try {
                        Utils.log(body);
                        JSONObject object = new JSONObject(body);
                        JSONObject responseObject = object.optJSONObject("response");
                        if (responseObject.optString("state").equals(API.SUCC)) {
                            JSONArray jsonArray = responseObject.optJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Message message = new Gson().fromJson(jsonArray.optJSONObject(i).toString(), Message.class);
                                mMessageList.add(message);
                            }
                            mMessageAdapter.resetData(mMessageList);
                        } else {
                            Utils.toast(mActivity, responseObject.optString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, OkHttpClientManager.Params.get("userId", UserInfoManager.get(mActivity).getId() + ""));
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
                user.setId(m.getUserId());
                user.setHeadImage(m.getHeadImage());
                user.setNickName(m.getNickName());
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
}
