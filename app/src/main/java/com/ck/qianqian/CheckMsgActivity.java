package com.ck.qianqian;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

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
    TextView emergencyPhone;
    @BindView(R.id.emergencyName)
    TextView emergencyName;
    @BindView(R.id.emergencyPhone2)
    TextView emergencyPhone2;
    @BindView(R.id.emergencyName2)
    TextView emergencyName2;
    @BindView(R.id.emergencyPhone3)
    TextView emergencyPhone3;
    @BindView(R.id.emergencyName3)
    TextView emergencyName3;
    @BindView(R.id.emergencyRelation)
    AutoCompleteTextView emergencyRelation;
    @BindView(R.id.emergencyRelation2)
    AutoCompleteTextView emergencyRelation2;
    @BindView(R.id.emergencyRelation3)
    AutoCompleteTextView emergencyRelation3;
    @BindView(R.id.service)
    TextView service;
    @BindView(R.id.submit)
    TextView submit;

    private Intent intent;
    private ArrayList<InfoType> educations;
    private ArrayList<InfoType> marriages;
    private ArrayList<InfoType> incomes;
    private ArrayList<InfoType> contacts;
    private ArrayList<InfoType> contactsOne;

    private InfoTypeAdapter adapter1;
    private InfoTypeAdapter adapter2;
    private InfoTypeAdapter adapter3;
    private InfoTypeAdapter adapter4;
    private InfoTypeAdapter adapter5;
    private InfoTypeAdapter adapter6;

    private int educationId = -1;
    private int marriageId = -1;
    private int incomeId = -1;
    private int contactId = -1;
    private int contactId2 = -1;
    private int contactId3 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_msg);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        titleName.setText("个人详细信息认证");
        intent = getIntent();
        educations = intent.getParcelableArrayListExtra("educations");
        marriages = intent.getParcelableArrayListExtra("marriages");
        incomes = intent.getParcelableArrayListExtra("incomes");
        contacts = intent.getParcelableArrayListExtra("contacts");
        contactsOne = intent.getParcelableArrayListExtra("contactsOne");
        setViews();
    }

    private void setViews() {
        adapter1 = new InfoTypeAdapter(this, educations);
        adapter2 = new InfoTypeAdapter(this, marriages);
        adapter3 = new InfoTypeAdapter(this, incomes);
        adapter4 = new InfoTypeAdapter(this, contactsOne);
        adapter5 = new InfoTypeAdapter(this, contacts);
        adapter6 = new InfoTypeAdapter(this, contacts);

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
                contactId = contactsOne.get(position).getId();
                emergencyRelation.setText(contactsOne.get(position).getTypeName());
            }
        });

        emergencyRelation2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactId2 = contacts.get(position).getId();
                emergencyRelation2.setText(contacts.get(position).getTypeName());
            }
        });

        emergencyRelation3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactId3 = contacts.get(position).getId();
                emergencyRelation3.setText(contacts.get(position).getTypeName());
            }
        });

    }

    @OnClick({R.id.education, R.id.marriage, R.id.income, R.id.emergencyRelation, R.id.emergencyRelation2, R.id.emergencyRelation3, R.id.service, R.id.submit, R.id.emergencyPhone, R.id.emergencyPhone2, R.id.emergencyPhone3})
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
            case R.id.emergencyRelation2:
//                if (!emergencyRelation.isShown())
                emergencyRelation2.showDropDown();
                break;
            case R.id.emergencyRelation3:
//                if (!emergencyRelation.isShown())
                emergencyRelation3.showDropDown();
                break;
            case R.id.service:
                Intent intent = new Intent(CheckMsgActivity.this, WebViewActivity.class);
                intent.putExtra("title", "服务与隐私");
                intent.putExtra("url", HttpMethods.BASE_URL + "service/service.html?key=RIVACY_SERVICE");
                startActivity(intent);
                break;
            case R.id.submit:
                if (checkValue()) {
                    uploadData();
                }
                break;
            //紧急联系人
            case R.id.emergencyPhone:
                getContact(1);
                break;
            case R.id.emergencyPhone2:
                getContact(2);
                break;
            case R.id.emergencyPhone3:
                getContact(3);
                break;
        }
    }

    private void getContact(final int position) {
        RxPermissions.getInstance(CheckMsgActivity.this)
                .request(Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setClassName("com.android.contacts", "com.android.contacts.activities.ContactSelectionActivity");
                            startActivityForResult(intent, position);
                        } else {
                            //不同意，给提示
                            Toast.makeText(CheckMsgActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            String[] contact = getPhoneNumber(data);
            emergencyName.setText(contact[0]);
            emergencyPhone.setText(contact[1]);
        } else if (requestCode == 2 && data != null) {
            String[] contact = getPhoneNumber(data);
            emergencyName2.setText(contact[0]);
            emergencyPhone2.setText(contact[1]);
        } else if (requestCode == 3 && data != null) {
            String[] contact = getPhoneNumber(data);
            emergencyName3.setText(contact[0]);
            emergencyPhone3.setText(contact[1]);
        }

    }

    private String[] getPhoneNumber(Intent intent) {
        Cursor cursor = null;
        Cursor phone = null;
        try {
            String[] projections = {ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
            cursor = getContentResolver().query(intent.getData(), projections, null, null, null);
            if ((cursor == null) || (!cursor.moveToFirst())) {
                return null;
            }
            int _id = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
            String id = cursor.getString(_id);
            int has_phone_number = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int hasPhoneNumber = cursor.getInt(has_phone_number);
            String phoneNumber = null;
            String phoneNmae = null;
            if (hasPhoneNumber > 0) {
                phone = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + id, null, null);
                while (phone.moveToNext()) {
                    int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int index2 = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    String number = phone.getString(index);
                    phoneNmae = phone.getString(index2);
                    phoneNumber = number;
                }
            }
            return new String[]{phoneNmae, phoneNumber};
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
            if (phone != null) phone.close();
        }
        return null;
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
        map.put("contactsType2", contactId2);
        map.put("contactsName2", emergencyName2.getText().toString());
        map.put("contactsPhone2", emergencyPhone2.getText().toString());
        map.put("contactsType3", contactId3);
        map.put("contactsName3", emergencyName3.getText().toString());
        map.put("contactsPhone3", emergencyPhone3.getText().toString());
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
        if (TextUtils.isEmpty(emergencyPhone.getText().toString()) || TextUtils.isEmpty(emergencyPhone2.getText().toString()) || TextUtils.isEmpty(emergencyPhone3.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入紧急联系人电话", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(emergencyName.getText().toString()) || TextUtils.isEmpty(emergencyName2.getText().toString()) || TextUtils.isEmpty(emergencyName3.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入紧急联系人姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contactId < 0 || contactId2 < 0 || contactId3 < 0) {
            Toast.makeText(getApplicationContext(), "请选择紧急联系人关系", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
