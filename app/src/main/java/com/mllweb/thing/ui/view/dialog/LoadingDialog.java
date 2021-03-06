package com.mllweb.thing.ui.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mllweb.thing.R;
import com.mllweb.thing.utils.Utils;

/**
 * Created by Android on 2016/6/1.
 */
public class LoadingDialog extends DialogFragment {
    private View mView;
    TextView mHintText;
    ProgressBar mProgress;
    ProgressBar mLoadProgress;
    private String showText;

    public LoadingDialog() {

    }

    public void setText(String text) {
        showText = text;
    }

    public void setProgress(float progress) {
        if (mLoadProgress != null) {
            if (mLoadProgress.getVisibility() == View.GONE) {
                mLoadProgress.setVisibility(View.VISIBLE);
            }
            mLoadProgress.setProgress((int)progress);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        mView = inflater.inflate(R.layout.dialog_loading, container, false);
        mLoadProgress= (ProgressBar) mView.findViewById(R.id.load_progress);
        mProgress= (ProgressBar) mView.findViewById(R.id.progress);
        mHintText = (TextView) mView.findViewById(R.id.tv_hint);
        mHintText.setText(showText);
        return mView;
    }

}
