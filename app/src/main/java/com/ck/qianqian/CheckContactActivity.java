package com.ck.qianqian;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckContactActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_contact);
        ButterKnife.bind(this);
        titleName.setText("通信认证");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
    }
}
