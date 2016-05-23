package com.mllweb.thing.ui.activity.start;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

public class AdvertActivity extends BaseActivity {
    @InjectView(R.id.iv_ad)
    ImageView mAdvert;
    private Handler mHandler = new Handler();

    @Override
    protected int initLayout() {
        return R.layout.activity_advert;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Picasso.with(mActivity).load("http://192.168.1.191:8080/Thing/ADVERT/images201510271633551_info320X480.jpg" ).into(mAdvert);

//        mHttp.requestGet("http://192.168.1.191:8080/Thing/SelectAdvert", new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d("ddddddd", "onFailure" + e.getLocalizedMessage() + " ");
//            }
//
//            @Override
//            public void onResponse(final Response response) throws IOException {
//                Log.d("ddddddd", "onFailure" + response.code() + " ");
//                final String path = response.body().string();
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Picasso.with(mActivity).load("http://192.168.1.191:8080/Thing" + path).into(mAdvert);
//                    }
//                });
//
//
//            }
//        });
    }
}
