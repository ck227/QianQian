package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.CheckPhone;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CheckPhoneActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.submit)
    TextView submit;

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.service)
    EditText service;
    @BindView(R.id.password)
    EditText password;

    private LoadingDialog dialog;
    private CheckPhone checkPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
        ButterKnife.bind(this);
        titleName.setText("手机认证");
        getData();
    }

    private void getData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.CheckPhoneResponse>() {
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
            public void onNext(HttpResult.CheckPhoneResponse response) {
                if (response.code == 0) {
                    checkPhone = response.obj;
                    phone.setText(checkPhone.getPhone());
                    service.setText(checkPhone.getPhonePperator());
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        HttpMethods.getInstance().checkPhone(subscriber, map);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (checkPwd()) {
            sendData();
        }
    }

    private void sendData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("phone", checkPhone.getPhone());
        map.put("servicePassWord", password.getText().toString());
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
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("success", true);
                    setResult(0, intent);
                    finish();
                }
            }
        };
        HttpMethods.getInstance().addCheckPhone(subscriber, map);
    }

    private Boolean checkPwd() {
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入服务密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
