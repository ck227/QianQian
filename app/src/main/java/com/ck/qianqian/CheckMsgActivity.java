package com.ck.qianqian;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckMsgActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_msg);
        ButterKnife.bind(this);
        titleName.setText("个人详细信息认证");
    }
}
