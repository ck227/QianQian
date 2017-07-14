package com.ck.qianqian.credit;

import android.os.Bundle;
import android.widget.TextView;

import com.ck.qianqian.BaseActivity;
import com.ck.qianqian.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreditDetailActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.login)
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_detail);
        ButterKnife.bind(this);
        titleName.setText("借款详情");
    }
}
