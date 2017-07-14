package com.ck.qianqian;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckCardActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.type)
    EditText type;
    @BindView(R.id.cardNo)
    EditText cardNo;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_card);
        ButterKnife.bind(this);
        titleName.setText("银行卡认证");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
    }
}
