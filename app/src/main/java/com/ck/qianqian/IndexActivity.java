package com.ck.qianqian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ck.util.MyApplication;

public class IndexActivity extends Activity implements Runnable {

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

        String username = MyApplication.getInstance().getUserName();
        if (username != null && username.length() > 0) {
            intent = new Intent(IndexActivity.this, MainActivity.class);
        } else {
            intent = new Intent(IndexActivity.this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
