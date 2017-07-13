package com.ck.qianqian;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPwdActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.findPwd)
    TextView findPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        titleName.setText("找回密码");
    }

    @OnClick({R.id.getCode, R.id.findPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                break;
            case R.id.findPwd:
                break;
        }
    }
}
