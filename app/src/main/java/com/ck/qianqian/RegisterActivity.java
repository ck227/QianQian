package com.ck.qianqian;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password2)
    EditText password2;
    @BindView(R.id.getCode)
    TextView getCode;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.register)
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        titleName.setText("注册");
    }

    @OnClick({R.id.getCode, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                break;
            case R.id.register:
                break;
        }
    }
}
