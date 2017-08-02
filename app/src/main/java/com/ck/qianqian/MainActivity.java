package com.ck.qianqian;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ck.adapter.MainAdapter;
import com.ck.bean.Version;
import com.ck.bean.credit.CreditDetail;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
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

//    private RxPermissions rxPermissions;

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

        checkUpdate();

//        rxPermissions = new RxPermissions(this); // where this is an Activity instance

        /*rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS)//这里申请了两组权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            // 用户允许权限
                            Toast.makeText(getApplicationContext(), "已同意软件权限", Toast.LENGTH_SHORT).show();
                        } else {
                            //明天真机调试
                            Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
//        showDialog();
    }

   /* private void showDialog() {

        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS)//这里申请了两组权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            // 用户允许权限
                            Toast.makeText(getApplicationContext(), "已同意软件权限", Toast.LENGTH_SHORT).show();
                        } else {
                            //明天真机调试
                            Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

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

    /*private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                showDialog();
            } else if (msg.what == 1) {
//                showDialog2();
            }
        }
    };*/

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 && state == 2) {
                    adapter.refreshData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkUpdate() {
        String version = "1.0";
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("versionNumber", version);
        Subscriber subscriber = new Subscriber<HttpResult.VersionReponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final HttpResult.VersionReponse response) {
                if (response.code == 0 && response.obj != null) {
                    //弹窗之前检查版本
                    final Version version = response.obj;
                    AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                    b.setTitle("发现新的版本" + version.getVersioNumber());
                    b.setMessage(version.getVersionContent());
                    b.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //这里打开链接
                            Uri uri = Uri.parse(version.getVersionUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            if (version.getIsVersion() == 1) {
                                finish();
                            }
                        }
                    });
                    if (version.getIsVersion() == 1) {
                        b.setCancelable(false);
                    } else {
                        b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }
                    b.create().show();
                }

            }
        };
        HttpMethods.getInstance().getVersion(subscriber, map);
    }

}
