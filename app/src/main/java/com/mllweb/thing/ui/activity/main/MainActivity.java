package com.mllweb.thing.ui.activity.main;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;
import com.mllweb.model.Thing;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.activity.main.post.PostActivity;
import com.mllweb.thing.ui.adapter.main.MainPagerAdapter;
import com.mllweb.thing.ui.fragment.main.find.FindFragment;
import com.mllweb.thing.ui.fragment.main.home.HomeFragment;
import com.mllweb.thing.ui.fragment.main.message.MessageFragment;
import com.mllweb.thing.ui.fragment.main.mine.MineFragment;
import com.mllweb.thing.utils.Utils;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @InjectView(R.id.vp_main)
    ViewPager mMainPager;
    @InjectView(R.id.iv_home)
    ImageView mHomeIcon;
    @InjectView(R.id.iv_find)
    ImageView mFindIcon;
    @InjectView(R.id.iv_message)
    ImageView mMessageIcon;
    @InjectView(R.id.iv_mine)
    ImageView mMineIcon;
    @InjectView(R.id.tv_home)
    TextView mHomeText;
    @InjectView(R.id.tv_find)
    TextView mFindText;
    @InjectView(R.id.tv_message)
    TextView mMessageText;
    @InjectView(R.id.tv_mine)
    TextView mMineText;
    private int[] mIconNormals = new int[]{R.drawable.main_bottom_essence_normal, R.drawable.main_bottom_my_normal, R.drawable.main_bottom_latest_normal, R.drawable.main_bottom_news_normal};
    private int[] mIconSelecteds = new int[]{R.drawable.main_bottom_essence_press_night, R.drawable.main_bottom_my_press_night, R.drawable.main_bottom_latest_press_night, R.drawable.main_bottom_news_press_night};
    private MainPagerAdapter mMainPagerAdapter;
    private List<Fragment> mFragmentData = new ArrayList<>();
    private long exitTime = 0;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Thing thing = (Thing) intent.getSerializableExtra("thing");
        if (thing != null && mFragmentData != null && mFragmentData.size() > 0) {
            HomeFragment f = (HomeFragment) mFragmentData.get(0);
            f.insert(thing);
        }
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        mFragmentData.add(new HomeFragment());
        mFragmentData.add(new FindFragment());
        mFragmentData.add(new MessageFragment());
        mFragmentData.add(new MineFragment());
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentData);
        mMainPager.setAdapter(mMainPagerAdapter);
        mMainPager.setOffscreenPageLimit(mFragmentData.size());
        clickHome(null);
    }

    @OnClick(R.id.iv_add_post)
    public void clickAddPost() {
        if (mRealm.isLogged()) {
            startActivity(PostActivity.class);
            overridePendingTransition(R.anim.post_start, R.anim.post_end);
        } else {
            Utils.toast(mActivity, "请先登录");
        }
    }

    @OnClick(R.id.home_layout)
    public void clickHome(View v) {
        if (mMainPager.getCurrentItem() == 0 && v != null) {
            HomeFragment home = (HomeFragment) mFragmentData.get(0);
            home.loadMore();
        } else {
            resetIcon();
            mHomeIcon.setImageResource(mIconSelecteds[0]);
            mHomeText.setTextColor(mResources.getColor(R.color.theme_color_green));
            mMainPager.setCurrentItem(0, false);
        }
    }

    @OnClick(R.id.find_layout)
    public void clickFind() {
        resetIcon();
        mFindIcon.setImageResource(mIconSelecteds[1]);
        mFindText.setTextColor(mResources.getColor(R.color.theme_color_green));
        mMainPager.setCurrentItem(1, false);
    }

    @OnClick(R.id.message_layout)
    public void clickMessage() {
        resetIcon();
        mMessageIcon.setImageResource(mIconSelecteds[2]);
        mMessageText.setTextColor(mResources.getColor(R.color.theme_color_green));
        mMainPager.setCurrentItem(2, false);
    }

    @OnClick(R.id.mine_layout)
    public void clickMine() {
        resetIcon();
        mMineIcon.setImageResource(mIconSelecteds[3]);
        mMineText.setTextColor(mResources.getColor(R.color.theme_color_green));
        mMainPager.setCurrentItem(3, false);
    }

    private void resetIcon() {
        mHomeIcon.setImageResource(mIconNormals[0]);
        mFindIcon.setImageResource(mIconNormals[1]);
        mMessageIcon.setImageResource(mIconNormals[2]);
        mMineIcon.setImageResource(mIconNormals[3]);
        mHomeText.setTextColor(mResources.getColor(R.color.main_tab_gray));
        mFindText.setTextColor(mResources.getColor(R.color.main_tab_gray));
        mMessageText.setTextColor(mResources.getColor(R.color.main_tab_gray));
        mMineText.setTextColor(mResources.getColor(R.color.main_tab_gray));
    }


    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getKeyCode() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 1000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
