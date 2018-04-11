package com.ck.qianqian;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.util.Utils;
import com.ck.widget.LoadingDialog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.findPwd)
    TextView findPwd;

    private LoadingDialog dialog;
    private Transition explode;

    private RxPermissions rxPermissions;

    private String s = "90u7V+xpjuE6geqHrmlfkD2jkc8ycy5Izdg4ImCBb66OZrNrYk3dJWz0AtkFjrxGmIkgZJHOzACTumF1PmCgjtzDpdAvNYTQjIsjO2Cz1f4dEPqcTaFQ9GGnbQKu/ym+DNu41pODKCMpRQxl7lAUcDcX2+lBSvXaFXTyWspH1FZ91R/ta8LUPErPeC8HzRIMTlddPe0zjw/5W5r0Wb7B+oYEiqIBJhmkOZioDkWsB0bTKQatUbh8kFghAkTEa8VcYC1yHWWX6/3s45epHDJHJa1PpqczoDLAJCED80cugi40SBwN1UipyZUqECEBd2qBSS3ScPa/sK0UC7+Q6eAe3oMJtADNDYwmf1ojlsmr74/x4Lj6iUVFpiIcGzN9cZ2cVgkFq351yRwoVaNVAZG4J8+wU9YdJKaecd15KQvBTYHEMS+VAzuoRTTtdZBCZCxfg3dePPZ6R9hLLUTg8MFC8TCdLS1WsVCe2Wp/vef7xLcFVOvHUICuRKFiv4yyTTpj8sJkX2tqFCH7YBjrs17YT5r1W54PNJM+D56f6OlQ7UmSFU5l2a/It1aUh2mvzekImt1cDdPrK+W3cwxCD3ceuiZsXgfy0bz+4Na/cN63n1tLpVHAO9m3skEjmjKUdz1n"
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        rxPermissions = new RxPermissions(this);

//        account.setSelection(account.getText().toString().length());

//        test("g3!|H^y,C%v-#n2u1R<naXEk","d074b21c",s);
    }

   /* private String test(String key, String keyiv, String data){
        byte[] bOut = new byte[0];
        try {
            Key deskey = null;

//            String keybase64 = Base64.encodeToString(key.getBytes(),0);
//            String keyivbase64 = Base64.encodeToString(keyiv.getBytes(),0);
//            String database64 = Base64.encodeToString(data.getBytes());

            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            bOut = cipher.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return bOut.toString();

    }*/

    @OnClick({R.id.login, R.id.register, R.id.findPwd})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.login:
                if (checkValue()) {
                    showDialog();
                }
//                test("g3!|H^y,C%v-#n2u1R<naXEk","d074b21c",s);
                break;
            case R.id.register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.findPwd:
                intent = new Intent(LoginActivity.this, FindPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showDialog() {

        rxPermissions
                .request(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里申请了两组权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {

                        if (granted) {
                            login();
                        } else {
                            Toast.makeText(getApplicationContext(), "请手动开启权限", Toast.LENGTH_SHORT).show();
                        }

                        /*if (permission.granted) {
                            login();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "请手动开启权限", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
    }

    private void login() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", account.getText().toString());
        map.put("passWord", password.getText().toString());
        Subscriber subscriber = new Subscriber<HttpResult.LoginResponse>() {
            @Override
            public void onCompleted() {
//                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.LoginResponse response) {
                if (response.code == 0) {
                    MyApplication.getInstance().setUserName(account.getText().toString());
                    MyApplication.getInstance().setRealName(response.obj.getUserName());
                    //这里请求服务器
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                    getHomeState();
                } else {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().login(subscriber, map);
    }

    private void getHomeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", account.getText().toString());
        Subscriber subscriber = new Subscriber<HttpResult.IndexResponse>() {
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
            public void onNext(HttpResult.IndexResponse response) {
                int code = response.code;
                if (code == 1 || code == 8) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("state", 2);//
                    intent.putExtra("code", code);
                    intent.putExtra("creditDetail", response.obj);
                    startActivity(intent);
                } else if (code == 3) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("state", 1);//还款
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("state", 0);//借款
                    startActivity(intent);
                }
                finish();
            }
        };
        HttpMethods.getInstance().getHomeState(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(account.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.isMobileNO(account.getText().toString())) {
            Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

