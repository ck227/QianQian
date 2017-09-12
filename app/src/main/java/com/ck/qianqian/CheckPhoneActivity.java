package com.ck.qianqian;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.CheckPhone;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.util.Utils;
import com.ck.widget.InputCodeDialog;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CheckPhoneActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.submit)
    TextView submit;

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.service)
    EditText service;
    @BindView(R.id.password)
    EditText password;

//    @BindView(R.id.code)
//    EditText code;
//    @BindView(R.id.getCode)
//    TextView getCode;

    private LoadingDialog dialog;
    private CheckPhone checkPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone);
        ButterKnife.bind(this);
        titleName.setText("手机认证");
        getData();

//        getCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkValue()) {
//                    sendCode();
//                }
//            }
//        });
    }

    private String task_id;

    private void sendCode() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("phone", phone.getText().toString());
        map.put("password", password.getText().toString());
        map.put("real_name_required", real_name_required);
        Subscriber subscriber = new Subscriber<HttpResult.CheckSth2Response>() {
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
            public void onNext(HttpResult.CheckSth2Response response) {
                if (response.code == 0) {
                    task_id = response.task_id;
                    sendCode2();
                } else if (response.code == -4) {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckPhoneActivity.this, CheckIdActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        HttpMethods.getInstance().checkSth2(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入服务密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private int count;


    /**
     * 下面的是验证码的
     */
    private void sendCode2() {
//        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
//        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("task_id", task_id);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
//                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
//                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
//                if (count % 3 == 0)
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
//                    timerStart();
//                    noCode = true;
                    showDialog();
                } else if (response.code == -7) {

                } else if (response.code == -8) {
                    count++;
                    if (count < 51) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendCode2();
                    } else {

                    }
                } else if (response.code == -9) {
                    count++;
                    if (count < 51) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendCode2();
                    } else {

                    }
                } else if (response.code == -10) {
                    unNeedCode = true;
//                    sendData();
                    checkFuckingWhat();
                }
            }
        };
        HttpMethods.getInstance().sendPhoneCode(subscriber, map);
    }

    //这里要改成可以输验证码
    private void showDialog() {
        InputCodeDialog dialog = new InputCodeDialog(CheckPhoneActivity.this, new InputCodeDialog.ButtonListener() {
            @Override
            public void button(String code) {
                if (code != null && code.length() > 0) {

                    //先验证验证码
                    codeString = code;
                    checkInputCode();
//                    sendData();
                }
            }
        });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private String codeString;
    private Boolean unNeedCode = false;

    private void checkInputCode() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("verify", codeString);
        map.put("task_id", task_id);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {
                if (response.code == 0) {
                    //再请求9.4
                    checkFuckingWhat();
                }
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
            }
        };
        HttpMethods.getInstance().checkInputCode(subscriber, map);
    }

    private void checkFuckingWhat() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("task_id", task_id);

        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    sendData();
                } else if (response.code == -3) {
                    //Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    //new dialog
                    codeString = "";
                    showDialog();
                } else if (response.code == -1) {
                    //Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    checkFuckingWhat();
                } else {
                    //Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().checkFuckingWhat(subscriber, map);
    }

    Handler handler = new Handler();

   /* private Timer timer;
    private int time = 60;

    private void timerStart() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                Message message = handler.obtainMessage();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 100, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (time > 0) {
                        getCode.setEnabled(false);
                        getCode.setText(time + "秒后重试");
                    } else {
                        timer.cancel();
                        getCode.setEnabled(true);
                        getCode.setText("发送验证码");
                        time = 60;
                    }
                    break;
            }
        }

    };*/

    private void getData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Subscriber subscriber = new Subscriber<HttpResult.CheckPhoneResponse>() {
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
            public void onNext(HttpResult.CheckPhoneResponse response) {
                if (response.code == 0) {
                    checkPhone = response.obj;
                    phone.setText(checkPhone.getPhone());
                    service.setText(checkPhone.getPhonePperator());
                    getData2();
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
        HttpMethods.getInstance().checkPhone(subscriber, map);
    }

    String real_name_required;

    private void getData2() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("phone", phone.getText().toString());
        Subscriber subscriber = new Subscriber<HttpResult.CheckSthResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.CheckSthResponse response) {
                if (response.code == 0) {
                    real_name_required = response.real_name_required;
                } else if (response.code == -4) {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckPhoneActivity.this, CheckIdActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().checkSth(subscriber, map);
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        if (checkPwd()) {
//            sendData();
            sendCode();
        }
    }

    private void sendData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("phone", checkPhone.getPhone());
        map.put("servicePassWord", password.getText().toString());
        //这里要加传的code

        if (unNeedCode) {
            map.put("verify", "-10");
        } else {
            //这里改成真正的值
            map.put("verify", codeString);
        }


        map.put("task_id", task_id);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
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
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("success", true);
                    setResult(0, intent);
                    finish();
                } else {//-6

                }
            }
        };
        HttpMethods.getInstance().addCheckPhone(subscriber, map);
    }

    private Boolean checkPwd() {
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入服务密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
