package com.ck.qianqian;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.feedback)
    EditText feedback;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        titleName.setText("意见反馈");
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
    }
}
