package com.ck.qianqian;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ck.adapter.MainAdapter;
import com.ck.bean.credit.CreditDetail;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    private MainAdapter adapter;

    //    private Boolean needPay;
    private int state;//0  是借款  1是还款  2是其它状态
    private int code;
    private CreditDetail creditDetail;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        needPay = getIntent().getBooleanExtra("needPay", false);//
        intent = getIntent();
        state = intent.getIntExtra("state", 0);
        if (state == 2) {
            code = intent.getIntExtra("code", 0);
            creditDetail = intent.getParcelableExtra("creditDetail");
        }
        setViews();

        RxPermissions.getInstance(MainActivity.this)
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS)//这里申请了两组权限
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            // 用户允许权限
                        } else {
                            //明天真机调试
//                            Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        showDialog();
    }

    private void showDialog() {

        RxPermissions.getInstance(MainActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS)//这里申请了两组权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
//                            handler.sendEmptyMessage(1);
                        } else {
//                            Toast.makeText(MainActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
//                            handler.sendEmptyMessage(0);
                        }
                    }
                });
    }

    /*private void showDialog2() {
        RxPermissions.getInstance(MainActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里申请了两组权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {

                        } else {
                            //不同意，给提示
                            Toast.makeText(MainActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
    }*/

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                showDialog();
            } else if (msg.what == 1) {
//                showDialog2();
            }
        }
    };

    private void setViews() {
        if (state == 2) {
            adapter = new MainAdapter(getSupportFragmentManager(), MainActivity.this, state, code, creditDetail);
        } else {
            adapter = new MainAdapter(getSupportFragmentManager(), MainActivity.this, state);
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);
    }
}
