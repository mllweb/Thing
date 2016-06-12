package com.mllweb.thing.ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mllweb.thing.R;

/**
 * Created by Android on 2016/6/1.
 */
public class LinkDialog extends DialogFragment {
    private View mView;
    EditText mLink;
    TextView mAdd;
    ImageView mClose;
    private String showText;
    private OnAddListener listener;

    public void setOnAddListener(OnAddListener listener) {
        this.listener = listener;
    }

    public LinkDialog() {

    }

    public void setText(String text) {
        showText = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        mView = inflater.inflate(R.layout.dialog_link, container, false);
        mLink = (EditText) mView.findViewById(R.id.et_link);
        mAdd = (TextView) mView.findViewById(R.id.tv_add);
        mClose = (ImageView) mView.findViewById(R.id.iv_close);
        mLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    mAdd.setEnabled(false);
                    mAdd.setTextColor(0xFF999999);
                } else {
                    mAdd.setEnabled(true);
                    mAdd.setTextColor(getResources().getColor(R.color.theme_color_green));
                }
            }
        });
        mClose.setOnClickListener(v -> {
            getDialog().dismiss();
        });
        mAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.add(mLink.getText().toString());
                getDialog().dismiss();
            }
        });
        return mView;
    }

    public interface OnAddListener {
        void add(String link);
    }
}
