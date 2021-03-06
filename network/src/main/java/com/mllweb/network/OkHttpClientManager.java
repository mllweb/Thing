package com.mllweb.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

/**
 * Created by Android on 2016/5/23.
 */
public class OkHttpClientManager {
    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            HandlerParams params = (HandlerParams) msg.obj;
            switch (msg.what) {
                case FAILURE:
                    params.callback.onFailure(params.request, params.exception);
                    break;
                case SUCCESS:
                    Log.i("mllweb-http", params.body);
                    params.callback.onResponse(params.response, params.body);
                    break;
            }
        }
    };

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
    }

    public synchronized static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpClientManager();
        }
        return mInstance;
    }

    public void requestGetFileDomain(String url, Callback callback) {
        Request request = new Request.Builder().url(API.DOMAIN + url).build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public void requestGetDomain(String url, final RequestCallback callback) {
        requestGet(API.DOMAIN + url, callback);
    }

    /**
     * get请求
     *
     * @param url
     * @param callback
     */
    public void requestGet(String url, final RequestCallback callback) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    sendFailureCallback(callback, request, e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                sendSuccessCallback(callback, response);
            }
        });

    }

    public void requestPostDomain(String url, final RequestCallback callback, Params... paramses) {
        requestPost(API.DOMAIN + url, callback, paramses);
    }

    public void requestPostDomain(String url, final RequestCallback callback, File file, String fileName) {
        requestPost(API.DOMAIN + url, callback, file, fileName);
    }


    /**
     * post请求
     *
     * @param url
     * @param callback
     * @param paramses
     */
    public void requestPost(String url, final RequestCallback callback, Params... paramses) {
        Request request = buildPostRequest(url, paramses);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    sendFailureCallback(callback, request, e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                sendSuccessCallback(callback, response);
            }
        });
    }

    /**
     * post请求
     *
     * @param url
     * @param callback
     */
    public void requestPost(String url, final RequestCallback callback, File file, String fileName) {
        Request request = buildPostRequest(url, file, fileName);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    sendFailureCallback(callback, request, e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                sendSuccessCallback(callback, response);
            }
        });
    }

    /**
     * 构建Post请求体
     *
     * @param url
     * @param paramses
     * @return
     */
    private Request buildPostRequest(String url, Params[] paramses) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (paramses == null) {
            return new Request.Builder().url(url).build();
        } else {
            for (Params p : paramses) {
                if (p.value == null) p.value = "";
                builder.add(p.key, p.value);
            }
            RequestBody requestBody = builder.build();
            return new Request.Builder().url(url).post(requestBody).build();
        }

    }

    /**
     * 构建Post请求体
     *
     * @param url
     * @return
     */
    private Request buildPostRequest(String url, File file, String fileName) {
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; filename=\"" + fileName + "\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * 发送失败回调
     *
     * @param callback
     * @param request
     * @param exception
     */
    private void sendFailureCallback(RequestCallback callback, Request request, IOException exception) throws IOException {
        Message msg = mHandler.obtainMessage();
        msg.what = FAILURE;
        msg.obj = new HandlerParams(callback, request, exception);
        mHandler.sendMessage(msg);
    }

    /**
     * 发送成功回调
     *
     * @param callback
     * @param response
     */
    private void sendSuccessCallback(RequestCallback callback, Response response) throws IOException {
        Message msg = mHandler.obtainMessage();
        msg.what = SUCCESS;
        msg.obj = new HandlerParams(callback, response);
        mHandler.sendMessage(msg);
    }

    /**
     * 参数类
     */
    public static class Params {
        String key, value;

        private Params(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static Params get(String key, String value) {
            return new Params(key, value);
        }
    }

    /**
     * 回调类
     */
    private class HandlerParams {
        RequestCallback callback;
        Response response;
        Request request;
        IOException exception;
        String body;

        public HandlerParams(RequestCallback callback, Response response) throws IOException {
            this.callback = callback;
            this.response = response;
            this.body = response.body().string();
        }

        public HandlerParams(RequestCallback callback, Request request, IOException exception) throws IOException {
            this.callback = callback;
            this.request = request;
            this.exception = exception;
            if (response != null) {
                this.body = response.body().string();
            } else {
                this.body = "";
            }
        }
    }

    /**
     * 回调接口
     */
    public interface RequestCallback {
        void onFailure(Request request, IOException e);

        void onResponse(Response response, String body);
    }
}
