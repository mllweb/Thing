package com.mllweb.thing.ui.activity.start;

import android.os.Bundle;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.MainActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    startActivity(MainActivity.class);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
