package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.BanksAdapter;
import com.ck.bean.Bank;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CheckCardActivity extends BaseActivity {


    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.type)
    AutoCompleteTextView type;
    @BindView(R.id.cardNo)
    EditText cardNo;
    @BindView(R.id.submit)
    TextView submit;

    private int bankType = -1;
    private ArrayList<Bank> banks;
    private BanksAdapter adapter;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_card);
        ButterKnife.bind(this);
        titleName.setText("银行卡认证");

        banks = new ArrayList<>();
        adapter = new BanksAdapter(this, banks);
        type.setAdapter(adapter);

        type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankType = banks.get(position).getId();
                type.setText(banks.get(position).getTypeName());
            }
        });
    }

    private void getBanks() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Subscriber subscriber = new Subscriber<HttpResult.BanksResponse>() {
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
            public void onNext(HttpResult.BanksResponse response) {
                if (response.code == 0) {
                    banks.addAll(response.obj.getListBank());
                    adapter.notifyDataSetChanged();
                    handler.sendEmptyMessage(0);
                } else {
                    Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpMethods.getInstance().getBanks(subscriber);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                type.showDropDown();
            }
        }
    };

    @OnClick({R.id.type, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.type:
                if (banks.size() > 0) {//如果有数据就直接显示
                    type.showDropDown();
                } else {//没有数据请求服务器
                    getBanks();
                }
                break;
            case R.id.submit:
                if (checkValue()) {
                    uploadData();
                }
                break;
        }
    }

    private void uploadData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("bankType", bankType);
        map.put("bankName", name.getText().toString());
        map.put("bankNumber", cardNo.getText().toString());
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
                }
            }
        };
        HttpMethods.getInstance().addCheckBank(subscriber, map);
    }

    private Boolean checkValue() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bankType < 0) {
            Toast.makeText(getApplicationContext(), "请选择银行类型", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(cardNo.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入银行卡号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}


