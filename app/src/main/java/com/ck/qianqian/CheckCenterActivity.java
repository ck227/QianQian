package com.ck.qianqian;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.CheckStatus;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.widget.LoadingDialog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

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
    @BindView(R.id.check_alipay_ic)
    ImageView checkAlipayIc;
    @BindView(R.id.check_alipay_text)
    TextView checkAlipayText;
    @BindView(R.id.check_alipay_ll)
    LinearLayout checkAlipayLl;
    @BindView(R.id.check_taobao_ic)
    ImageView checkTaobaoIc;
    @BindView(R.id.check_taobao_text)
    TextView checkTaobaoText;
    @BindView(R.id.check_taobao_ll)
    LinearLayout checkTaobaoLl;

    private CheckStatus status;

    private int hasCheckPhone, hasCheckMsg, hasCheckId, hasCheckCard, hasCheckContact, hasCheckAlipay, hasCheckTaobao;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_center);
        ButterKnife.bind(this);
        titleName.setText("认证中心");

        status = getIntent().getParcelableExtra("status");
        hasCheckPhone = status.getUser_phone_service_audit();
        hasCheckMsg = status.getUser_info_audit();
        hasCheckId = status.getUser_card_audit();
        hasCheckCard = status.getUser_bank_audit();
        hasCheckContact = status.getUser_book_phone_audit();

        hasCheckAlipay = status.getUser_zfb_audit();
        hasCheckTaobao = status.getUser_tb_audit();
        setViews();

        rxPermissions = new RxPermissions(this);
    }

    /**
     * TODO: 设置图标和文字的颜色和点击事件
     */
    private void setViews() {
        //手机认证
        if (hasCheckPhone == 2) {
            checkPhoneIc.setImageResource(R.mipmap.check_phone2);
            checkPhoneText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkPhoneIc.setImageResource(R.mipmap.check_phone);
            checkPhoneText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckPhone == 0) {
            checkPhoneText.setText("手机认证(未认证)");
        } else if (hasCheckPhone == 1) {
            checkPhoneText.setText("手机认证(待认证)");
        } else if (hasCheckPhone == 2) {
            checkPhoneText.setText("手机认证(已认证)");
        } else if (hasCheckPhone == 3) {
            checkPhoneText.setText("手机认证(未通过)");
        }
        //信息认证
        if (hasCheckMsg == 2) {
            checkMsgIc.setImageResource(R.mipmap.check_msg2);
            checkMsgText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkMsgIc.setImageResource(R.mipmap.check_msg);
            checkMsgText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckMsg == 0) {
            checkMsgText.setText("信息认证(未认证)");
        } else if (hasCheckMsg == 1) {
            checkMsgText.setText("信息认证(待认证)");
        } else if (hasCheckMsg == 2) {
            checkMsgText.setText("信息认证(已认证)");
        } else if (hasCheckMsg == 3) {
            checkMsgText.setText("信息认证(未通过)");
        }
        //身份认证
        if (hasCheckId == 2) {
            checkIdIc.setImageResource(R.mipmap.check_id2);
            checkIdText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkIdIc.setImageResource(R.mipmap.check_id);
            checkIdText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckId == 0) {
            checkIdText.setText("身份认证(未认证)");
        } else if (hasCheckId == 1) {
            checkIdText.setText("身份认证(待认证)");
        } else if (hasCheckId == 2) {
            checkIdText.setText("身份认证(已认证)");
        } else if (hasCheckId == 3) {
            checkIdText.setText("身份认证(未通过)");
        }
        //银行卡认证
        if (hasCheckCard == 2) {
            checkCardIc.setImageResource(R.mipmap.check_card2);
            checkCardText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkCardIc.setImageResource(R.mipmap.check_card);
            checkCardText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckCard == 0) {
            checkCardText.setText("银行卡认证(未认证)");
        } else if (hasCheckCard == 1) {
            checkCardText.setText("银行卡认证(待认证)");
        } else if (hasCheckCard == 2) {
            checkCardText.setText("银行卡认证(已认证)");
        } else if (hasCheckCard == 3) {
            checkCardText.setText("银行卡认证(未通过)");
        }
        //通信认证
        if (hasCheckContact == 2) {
            checkContactIc.setImageResource(R.mipmap.check_contact2);
            checkContactText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkContactIc.setImageResource(R.mipmap.check_contact);
            checkContactText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckContact == 0) {
            checkContactText.setText("通信认证(未认证)");
        } else if (hasCheckContact == 1) {
            checkContactText.setText("通信认证(待认证)");
        } else if (hasCheckContact == 2) {
            checkContactText.setText("通信认证(已认证)");
        } else if (hasCheckContact == 3) {
            checkContactText.setText("通信认证(未通过)");
        }
        //支付宝认证
        if (hasCheckAlipay == 2) {
            checkAlipayIc.setImageResource(R.mipmap.check_contact2);
            checkAlipayText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkAlipayIc.setImageResource(R.mipmap.check_contact);
            checkAlipayText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckAlipay == 0) {
            checkAlipayText.setText("支付宝认证(未认证)");
        } else if (hasCheckAlipay == 1) {
            checkAlipayText.setText("支付宝认证(待认证)");
        } else if (hasCheckAlipay == 2) {
            checkAlipayText.setText("支付宝认证(已认证)");
        } else if (hasCheckAlipay == 3) {
            checkAlipayText.setText("支付宝认证(未通过)");
        }
        //淘宝认证
        if (hasCheckTaobao == 2) {
            checkTaobaoIc.setImageResource(R.mipmap.check_contact2);
            checkTaobaoText.setTextColor(getResources().getColor(R.color.text_blue));
        } else {
            checkTaobaoIc.setImageResource(R.mipmap.check_contact);
            checkTaobaoText.setTextColor(getResources().getColor(R.color.second_text_color));
        }
        if (hasCheckTaobao == 0) {
            checkTaobaoText.setText("淘宝认证(未认证)");
        } else if (hasCheckTaobao == 1) {
            checkTaobaoText.setText("淘宝认证(待认证)");
        } else if (hasCheckTaobao == 2) {
            checkTaobaoText.setText("淘宝认证(已认证)");
        } else if (hasCheckTaobao == 3) {
            checkTaobaoText.setText("淘宝认证(未通过)");
        }

    }

    @OnClick({R.id.check_phone_ll, R.id.check_msg_ll, R.id.check_id_ll, R.id.check_card_ll, R.id.check_contact_ll, R.id.check_alipay_ll, R.id.check_taobao_ll})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.check_phone_ll:
                if (hasCheckPhone == 0 || hasCheckPhone == 3) {
                    intent = new Intent(this, CheckPhoneActivity.class);
                    startActivityForResult(intent, 1);
                } else if (hasCheckPhone == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckPhone == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_msg_ll:
                if (hasCheckMsg == 0 || hasCheckMsg == 3) {
                    getInfos();
                } else if (hasCheckMsg == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckMsg == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_id_ll:
                if (hasCheckId == 0 || hasCheckId == 3) {
                    intent = new Intent(this, CheckIdActivity.class);
                    startActivityForResult(intent, 3);
                } else if (hasCheckId == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckId == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_card_ll:
                if (hasCheckCard == 0 || hasCheckCard == 3) {
                    intent = new Intent(this, CheckCardActivity.class);
                    startActivityForResult(intent, 4);
                } else if (hasCheckCard == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckCard == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_contact_ll:
                if (hasCheckContact == 0 || hasCheckContact == 3) {
                    intent = new Intent(this, CheckContactActivity.class);
                    startActivityForResult(intent, 5);
                } else if (hasCheckContact == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckContact == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_alipay_ll:
                if (hasCheckAlipay == 0 || hasCheckAlipay == 3) {
                    rxPermissions
                            .request(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里申请了两组权限
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean granted) {
                                    if (granted) {
                                        Intent intent = new Intent(CheckCenterActivity.this, WebViewActivity.class);
                                        intent.putExtra("title", "支付宝认证");
                                        intent.putExtra("url", "https://auth.alipay.com/login/index.htm");
                                        intent.putExtra("type", 2);
                                        startActivityForResult(intent, 6);
                                    } else {
                                        Toast.makeText(CheckCenterActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                                    }

                                    /*if (permission.granted) {
                                        Intent intent = new Intent(CheckCenterActivity.this, WebViewActivity.class);
                                        intent.putExtra("title", "支付宝认证");
                                        intent.putExtra("url", "https://auth.alipay.com/login/index.htm");
                                        intent.putExtra("type", 2);
                                        startActivityForResult(intent, 6);
                                    } else if(permission.shouldShowRequestPermissionRationale){
                                        // Denied permission without ask never again
                                        Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "请手动开启权限", Toast.LENGTH_SHORT).show();
                                    }*/
                                }
                            });

                } else if (hasCheckAlipay == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckAlipay == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.check_taobao_ll:
                if (hasCheckTaobao == 0 || hasCheckTaobao == 3) {
                    intent = new Intent(this, WebViewActivity.class);
                    intent.putExtra("title", "淘宝认证");
                    intent.putExtra("url", "https://login.taobao.com/");
                    intent.putExtra("type", 3);
                    startActivityForResult(intent, 7);
                } else if (hasCheckTaobao == 1) {
                    Toast.makeText(getApplicationContext(), "待认证", Toast.LENGTH_SHORT).show();
                } else if (hasCheckTaobao == 2) {
                    Toast.makeText(getApplicationContext(), "已认证", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckPhone = 1;
                checkPhoneText.setText("手机认证(待认证)");
            }
        } else if (requestCode == 2 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckMsg = 1;
                checkMsgText.setText("信息认证(待认证)");
            }
        } else if (requestCode == 3 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckId = 1;
                checkIdText.setText("身份认证(待认证)");
            }
        } else if (requestCode == 4 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckCard = 1;
                checkCardText.setText("银行卡认证(待认证)");
            }
        } else if (requestCode == 5 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckContact = 1;
                checkContactText.setText("通信认证(待认证)");
            }
        } else if (requestCode == 6 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckAlipay = 1;
                checkAlipayText.setText("支付宝认证(待认证)");
            }
        } else if (requestCode == 7 && data != null) {
            if (data.getBooleanExtra("success", false)) {
                hasCheckTaobao = 1;
                checkTaobaoText.setText("淘宝认证(待认证)");
            }
        }
    }

    private LoadingDialog dialog;

    private void getInfos() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Subscriber subscriber = new Subscriber<HttpResult.InfoResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.InfoResponse response) {
                if (response.code == 0) {
                    Intent intent = new Intent(CheckCenterActivity.this, CheckMsgActivity.class);
                    intent.putParcelableArrayListExtra("educations", response.obj.getListEducation());
                    intent.putParcelableArrayListExtra("marriages", response.obj.getListMarriage());
                    intent.putParcelableArrayListExtra("incomes", response.obj.getListIncome());
                    intent.putParcelableArrayListExtra("contacts", response.obj.getListContacts());
                    intent.putParcelableArrayListExtra("contactsOne", response.obj.getListContactsOne());
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getInfos(subscriber);
    }


}
