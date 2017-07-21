package com.ck.qianqian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;

public class IndexActivity extends Activity implements Runnable {

    private LoadingDialog dialog;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent;

        username = MyApplication.getInstance().getUserName();
        if (username != null && username.length() > 0) {
            //进入之前要判断一下
            getHomeState();
        } else {
            intent = new Intent(IndexActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void getHomeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", username);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                int code = response.code;
                if (code == 3) {
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    intent.putExtra("needPay", true);//还款
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    intent.putExtra("needPay", false);//借款
                    startActivity(intent);
                }
                finish();
            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }
}
