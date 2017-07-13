package com.ck.qianqian;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckCenterActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.check_phone_ic)
    ImageView checkPhoneIc;
    @BindView(R.id.check_phone_text)
    TextView checkPhoneText;
    @BindView(R.id.check_phone_ll)
    LinearLayout checkPhoneLl;
    @BindView(R.id.check_msg_ic)
    ImageView checkMsgIc;
    @BindView(R.id.check_msg_text)
    TextView checkMsgText;
    @BindView(R.id.check_msg_ll)
    LinearLayout checkMsgLl;
    @BindView(R.id.check_id_ic)
    ImageView checkIdIc;
    @BindView(R.id.check_id_text)
    TextView checkIdText;
    @BindView(R.id.check_id_ll)
    LinearLayout checkIdLl;
    @BindView(R.id.check_card_ic)
    ImageView checkCardIc;
    @BindView(R.id.check_card_text)
    TextView checkCardText;
    @BindView(R.id.check_card_ll)
    LinearLayout checkCardLl;
    @BindView(R.id.check_contact_ic)
    ImageView checkContactIc;
    @BindView(R.id.check_contact_text)
    TextView checkContactText;
    @BindView(R.id.check_contact_ll)
    LinearLayout checkContactLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_center);
        ButterKnife.bind(this);
        titleName.setText("认证中心");
    }

    @OnClick({R.id.check_phone_ll, R.id.check_msg_ll, R.id.check_id_ll, R.id.check_card_ll, R.id.check_contact_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_phone_ll:
                break;
            case R.id.check_msg_ll:
                break;
            case R.id.check_id_ll:
                break;
            case R.id.check_card_ll:
                break;
            case R.id.check_contact_ll:
                break;
        }
    }
}
