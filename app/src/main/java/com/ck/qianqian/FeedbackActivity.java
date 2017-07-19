package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.util.Utils;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.feedback)
    EditText feedback;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.submit)
    TextView submit;

    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        titleName.setText("意见反馈");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (checkValue()) {
            sendFeedback();
        }
    }

    private void sendFeedback() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("content", feedback.getText().toString());
        map.put("phone", phone.getText().toString());
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_LONG).show();
                if (response.code == 0) {
                    finish();
                }
            }
        };
        HttpMethods.getInstance().sendFeedback(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(feedback.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入反馈内容", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
