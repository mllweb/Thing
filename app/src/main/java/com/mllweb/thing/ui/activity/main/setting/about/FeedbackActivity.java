package com.mllweb.thing.ui.activity.main.setting.about;

import android.widget.EditText;

import com.mllweb.network.API;
import com.mllweb.network.OkHttpClientManager;
import com.mllweb.thing.R;
import com.mllweb.thing.ui.activity.BaseActivity;
import com.mllweb.thing.utils.Utils;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    @InjectView(R.id.et_feedback)
    @NotEmpty(message = "留点反馈吧")
    EditText mContent;

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @OnClick(R.id.tv_deliver)
    public void clickDeliver() {
        mValidator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        mHttp.requestPostDomain(API.INSERT_FEEDBACK, new OkHttpClientManager.RequestCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response, String body) {
                Utils.toast(mActivity, "谢谢你的反馈，我们会及时改进的");
                finish();
            }
        }, OkHttpClientManager.Params.get("content", mContent.getText().toString()));
    }
}
