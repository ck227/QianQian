package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ck.adapter.MainAdapter;
import com.ck.bean.credit.CreditDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            code = intent.getIntExtra("code",0);
            creditDetail = intent.getParcelableExtra("creditDetail");
        }
        setViews();

    }

    private void setViews() {
        if(state == 2){
            adapter = new MainAdapter(getSupportFragmentManager(), MainActivity.this, state,code,creditDetail);
        }else{
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
