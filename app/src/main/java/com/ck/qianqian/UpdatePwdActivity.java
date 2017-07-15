package com.ck.qianqian;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class UpdatePwdActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.oldPwd)
    EditText oldPwd;
    @BindView(R.id.newPwd)
    EditText newPwd;
    @BindView(R.id.newPwd2)
    EditText newPwd2;
    @BindView(R.id.updatePwd)
    TextView updatePwd;

    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        ButterKnife.bind(this);
        titleName.setText("修改密码");
    }

    @OnClick(R.id.updatePwd)
    public void onViewClicked() {
        if (checkValue()) {
            updatePwd();
        }
    }

    private void updatePwd() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("passWord", oldPwd.getText().toString());
        map.put("newPassWord", newPwd.getText().toString());
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
        HttpMethods.getInstance().updatePwd(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(oldPwd.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入原始密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(newPwd.getText().toString()) || TextUtils.isEmpty(newPwd2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPwd.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), "密码至少6位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPwd.getText().toString().equals(newPwd2.getText().toString())) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
