package com.mllweb.thing.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.ui.adapter.main.MainPagerAdapter;
import com.mllweb.thing.ui.activity.main.post.PostActivity;
import com.mllweb.thing.ui.fragment.main.find.FindFragment;
import com.mllweb.thing.ui.fragment.main.home.HomeFragment;
import com.mllweb.thing.ui.fragment.main.message.MessageFragment;
import com.mllweb.thing.ui.fragment.main.mine.MineFragment;

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
    private int[] mIconNormals = new int[]{R.drawable.main_tab_add, R.drawable.main_tab_add, R.drawable.main_tab_add, R.drawable.main_tab_add};
    private int[] mIconSelecteds = new int[]{R.drawable.main_tab_add, R.drawable.main_tab_add, R.drawable.main_tab_add, R.drawable.main_tab_add};
    private MainPagerAdapter mMainPagerAdapter;
    private List<Fragment> mFragmentData = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mFragmentData.add(new HomeFragment());
        mFragmentData.add(new FindFragment());
        mFragmentData.add(new MessageFragment());
        mFragmentData.add(new MineFragment());
        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragmentData);
        mMainPager.setAdapter(mMainPagerAdapter);
        mMainPager.setOffscreenPageLimit(mFragmentData.size());
        clickHome();
    }

    @OnClick(R.id.iv_add_post)
    public void clickAddPost() {
        Intent intent = new Intent(mActivity, PostActivity.class);
        mActivity.startActivity(intent);
        overridePendingTransition(R.anim.post_start,R.anim.post_end);
    }

    @OnClick(R.id.home_layout)
    public void clickHome() {
        resetIcon();
        mHomeIcon.setImageResource(mIconSelecteds[0]);
        mHomeText.setTextColor(mResources.getColor(R.color.theme_color_green));
        mMainPager.setCurrentItem(0, false);
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
}
