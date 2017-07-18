package com.ck.qianqian;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
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


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.findPwd)
    TextView findPwd;

    private LoadingDialog dialog;
    private Transition explode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        account.setText("18507104251");
        password.setText("123456");
        account.setSelection(account.getText().toString().length());
    }

    @OnClick({R.id.login, R.id.register, R.id.findPwd})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.login:
                if (checkValue()) {
                    login();
                }
                break;
            case R.id.register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.findPwd:
                intent = new Intent(LoginActivity.this, FindPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", account.getText().toString());
        map.put("passWord", password.getText().toString());
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
                if (response.code == 0) {
                    MyApplication.getInstance().setUserName(account.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().login(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(account.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(account.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

