package com.mllweb.network;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by Android on 2016/5/23.
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public synchronized static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpClientManager();
        }
        return mInstance;
    }

    public void requestGet(String url, final Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(callback);

    }


    public void requestPost(String url, final Callback callback, Params... paramses) {
        Request request = buildPostRequest(url, paramses);
        mOkHttpClient.newCall(request).enqueue(callback);
    }


    private Request buildPostRequest(String url, Params[] paramses) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (paramses == null) {
            paramses = new Params[0];
        }
        for (Params p : paramses) {
            builder.add(p.key, p.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url("").post(requestBody).build();
    }

    public class Params {
        String key, value;

        public Params(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
