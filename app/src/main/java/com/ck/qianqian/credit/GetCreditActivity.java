package com.ck.qianqian.credit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetCreditActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.timeList)
    RecyclerView timeList;
    @BindView(R.id.trialFee)
    TextView trialFee;
    @BindView(R.id.interestFee)
    TextView interestFee;
    @BindView(R.id.accountFee)
    TextView accountFee;
    @BindView(R.id.arrivalFee)
    TextView arrivalFee;
    @BindView(R.id.amountFee)
    TextView amountFee;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_credit);
        ButterKnife.bind(this);
        titleName.setText("选择借款");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {

    }
}
