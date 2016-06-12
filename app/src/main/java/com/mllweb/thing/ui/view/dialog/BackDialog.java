package com.mllweb.thing.ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mllweb.thing.R;

/**
 * Created by Android on 2016/6/1.
 */
public class BackDialog extends DialogFragment {
    private View mView;
    private String showText;

    TextView mCancel;
    TextView mConfirm;
    private OnBackListener listener;

    public void setOnBackListener(OnBackListener listener) {
        this.listener = listener;
    }

    public BackDialog() {

    }

    public void setText(String text) {
        showText = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        mView = inflater.inflate(R.layout.dialog_back, container, false);
        mCancel = (TextView) mView.findViewById(R.id.cancel);
        mConfirm = (TextView) mView.findViewById(R.id.confirm);
        mConfirm.setOnClickListener(v -> {
            if (listener != null) {
                listener.back(true);
            }
        });
        mCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.back(false);
            }
        });
        return mView;
    }

    public interface OnBackListener {
        void back(boolean back);
    }
}
