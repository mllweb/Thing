package com.mllweb.thing.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mllweb.thing.R;

import java.util.LinkedList;

import butterknife.InjectView;

public class WebActivity extends BaseActivity {
    @InjectView(R.id.tv_title)
    TextView mActionBarTitle;
    @InjectView(R.id.web_load_progress)
    ProgressBar mWebLoadProgress;
    @InjectView(R.id.web_view)
    WebView mWebView;
    private String mURI;
    private LinkedList<String> mVisitHistory = new LinkedList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mURI = getIntent().getStringExtra("URI");
        webConfig();
        mWebView.loadUrl(mURI);
    }

    private void webConfig() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mVisitHistory.size() == 0 || (!url.equals(mVisitHistory.getLast()))) {
                    mVisitHistory.add(url);
                }
                super.onPageFinished(view, url);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mWebLoadProgress.setVisibility(View.GONE);
                } else {
                    if (mWebLoadProgress.getVisibility() == View.GONE) {
                        mWebLoadProgress.setVisibility(View.VISIBLE);
                    }
                    mWebLoadProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mActionBarTitle.setText(title);
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mVisitHistory.size() > 1) {
                mVisitHistory.removeLast();
                mVisitHistory.removeLast();
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
