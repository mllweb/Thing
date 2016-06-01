package com.mllweb.thing.ui.activity.main.post;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import butterknife.InjectView;
import butterknife.OnClick;

public class PostActivity extends BaseActivity {
    @InjectView(R.id.et_content)
    @NotEmpty(message = "请输入帖子内容")
    EditText mContent;

    @Override
    protected int initLayout() {
        return R.layout.activity_post;
    }
    @OnClick(R.id.tv_publish)
    public void clickPublish() {
        mValidator.validate();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void clickBack(View v) {
        if (mContent.getText().toString().trim().equals("")) {
            finish();
        } else {
            Utils.toast(mActivity, "你有正在编辑的东西");
        }
    }

    @Override
    public void onValidationSucceeded() {
        startActivity(ChooseTopicActivity.class);
    }
}
