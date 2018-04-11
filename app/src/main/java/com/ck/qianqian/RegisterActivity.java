package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.Utils;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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

    private Timer timer;
    private int time = 60;
    private LoadingDialog dialog;

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
                if (checkValue()) {
                    sendCode();
                }
                break;
            case R.id.register:
                if (checkRegister()) {
                    register();
                }
                break;
        }
    }

    /**
     * 下面的是注册的
     */
    private void register() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone.getText().toString());
        map.put("passWord", password.getText().toString());
        map.put("verifyCode", code.getText().toString());
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
                    finish();
                }
            }
        };
        HttpMethods.getInstance().register(subscriber, map);
    }

    private Boolean checkRegister() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(password2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "密码至少6位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.getText().toString().equals(password2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(code.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 下面的是验证码的
     */
    private void sendCode() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone.getText().toString());
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
                    timerStart();
                }
            }
        };
        HttpMethods.getInstance().sendCode(subscriber, map);
    }

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
        timer.schedule(timerTask, 100L, 1000);
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

    };

    private Boolean checkValue() {
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(phone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
