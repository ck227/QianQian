package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.adapter.InfoTypeAdapter;
import com.ck.bean.InfoType;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CheckMsgActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.education)
    AutoCompleteTextView education;
    @BindView(R.id.marriage)
    AutoCompleteTextView marriage;
    @BindView(R.id.income)
    AutoCompleteTextView income;
    @BindView(R.id.livePosition)
    EditText livePosition;
    @BindView(R.id.company)
    EditText company;
    @BindView(R.id.companyPosition)
    EditText companyPosition;
    @BindView(R.id.companyPhone)
    EditText companyPhone;
    @BindView(R.id.qq)
    EditText qq;
    @BindView(R.id.emergencyPhone)
    EditText emergencyPhone;
    @BindView(R.id.emergencyName)
    EditText emergencyName;
    @BindView(R.id.emergencyRelation)
    AutoCompleteTextView emergencyRelation;
    @BindView(R.id.submit)
    TextView submit;

    private Intent intent;
    private ArrayList<InfoType> educations;
    private ArrayList<InfoType> marriages;
    private ArrayList<InfoType> incomes;
    private ArrayList<InfoType> contacts;

    private InfoTypeAdapter adapter1;
    private InfoTypeAdapter adapter2;
    private InfoTypeAdapter adapter3;
    private InfoTypeAdapter adapter4;

    private int educationId = -1;
    private int marriageId = -1;
    private int incomeId = -1;
    private int contactId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_msg);
        ButterKnife.bind(this);
        titleName.setText("个人详细信息认证");
        intent = getIntent();
        educations = intent.getParcelableArrayListExtra("educations");
        marriages = intent.getParcelableArrayListExtra("marriages");
        incomes = intent.getParcelableArrayListExtra("incomes");
        contacts = intent.getParcelableArrayListExtra("contacts");
        setViews();
    }

    private void setViews() {
        adapter1 = new InfoTypeAdapter(this, educations);
        adapter2 = new InfoTypeAdapter(this, marriages);
        adapter3 = new InfoTypeAdapter(this, incomes);
        adapter4 = new InfoTypeAdapter(this, contacts);

        education.setAdapter(adapter1);
        marriage.setAdapter(adapter2);
        income.setAdapter(adapter3);
        emergencyRelation.setAdapter(adapter4);

        education.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                educationId = educations.get(position).getId();
                education.setText(educations.get(position).getTypeName());
            }
        });

        marriage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                marriageId = marriages.get(position).getId();
                marriage.setText(marriages.get(position).getTypeName());
            }
        });

        income.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                incomeId = incomes.get(position).getId();
                income.setText(incomes.get(position).getTypeName());
            }
        });

        emergencyRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactId = contacts.get(position).getId();
                emergencyRelation.setText(contacts.get(position).getTypeName());
            }
        });

    }

    @OnClick({R.id.education, R.id.marriage, R.id.income, R.id.emergencyRelation, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.education:
//                if (!education.isShown())
                education.showDropDown();
                break;
            case R.id.marriage:
//                if (!marriage.isShown())
                marriage.showDropDown();
                break;
            case R.id.income:
//                if (!income.isShown())
                income.showDropDown();
                break;
            case R.id.emergencyRelation:
//                if (!emergencyRelation.isShown())
                emergencyRelation.showDropDown();
                break;
            case R.id.submit:
                if (checkValue()) {
                    uploadData();
                }
                break;
        }
    }

    private LoadingDialog dialog;

    private void uploadData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("educationType", educationId);
        map.put("marriageType", marriageId);
        map.put("incomeType", incomeId);
        map.put("address", livePosition.getText().toString());
        map.put("workUnit", company.getText().toString());
        map.put("workUnitAddress", companyPosition.getText().toString());
        map.put("workUnitPhone", companyPhone.getText().toString());
        map.put("qq", qq.getText().toString());
        map.put("contactsType", contactId);
        map.put("contactsName", emergencyName.getText().toString());
        map.put("contactsPhone", emergencyPhone.getText().toString());
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
        HttpMethods.getInstance().addCheckInfo(subscriber, map);
    }

    private Boolean checkValue() {
        if (educationId < 0) {
            Toast.makeText(getApplicationContext(), "请选择学历", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (marriageId < 0) {
            Toast.makeText(getApplicationContext(), "请选择婚姻状况", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (incomeId < 0) {
            Toast.makeText(getApplicationContext(), "请选择收入", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(livePosition.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入常住地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(company.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入工作单位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(companyPosition.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入工作单位地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(qq.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入QQ号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(emergencyPhone.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入紧急联系人电话", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(emergencyName.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入紧急联系人姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contactId < 0) {
            Toast.makeText(getApplicationContext(), "请选择紧急联系人关系", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
