package com.mllweb.thing.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mllweb.cache.ACache;
import com.mllweb.network.OkHttpClientManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Android on 2016/5/18.
 */
public abstract class BaseActivity extends AppCompatActivity implements Validator.ValidationListener {
    protected final String DOMAIN = OkHttpClientManager.DOMAIN;
    protected Activity mActivity;
    protected Resources mResources;
    protected OkHttpClientManager mHttp;
    protected ImageLoader mImageLoader;
    protected ACache mCache;
    protected Validator mValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initFiled();
        initData(savedInstanceState);
        initEvent();
    }

    /**
     * 初始化布局文件
     *
     * @return
     */
    protected abstract int initLayout();

    /**
     * 初始化属性
     */
    private void initFiled() {
        mResources = getResources();
        ButterKnife.inject(this);
        mActivity = this;
        mHttp = OkHttpClientManager.getInstance();
        mImageLoader = ImageLoader.getInstance();
        mCache = ACache.get(mActivity);
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    protected void clickBack(View v) {
        finish();
    }

    protected void startActivity(Class cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }

    /**
     * 输入框验证成功
     */
    @Override
    public void onValidationSucceeded() {

    }

    /**
     * 输入框验证失败
     */
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
            break;
        }
    }
}
