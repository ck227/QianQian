package com.ck.qianqian;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckIdActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.idNumber)
    EditText idNumber;
    @BindView(R.id.frontImg)
    ImageView frontImg;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.personImg)
    ImageView personImg;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_id);
        ButterKnife.bind(this);
        titleName.setText("身份证认证");
    }

    @OnClick({R.id.frontImg, R.id.backImg, R.id.personImg, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frontImg:
                break;
            case R.id.backImg:
                break;
            case R.id.personImg:
                break;
            case R.id.submit:
                break;
        }
    }
}
