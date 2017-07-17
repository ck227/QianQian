package com.ck.qianqian;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.Contact;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

public class CheckContactActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.submit)
    TextView submit;

    private LoadingDialog dialog;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_contact);
        ButterKnife.bind(this);
        titleName.setText("通信认证");
        contacts = new ArrayList<>();
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {
        RxPermissions.getInstance(CheckContactActivity.this)
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            dialog = new LoadingDialog(CheckContactActivity.this, R.style.MyCustomDialog);
                            dialog.show();
                            contacts.clear();//避免数据重复
                            getContacts();
                        } else {
                            //不同意，给提示
                            Toast.makeText(CheckContactActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getContacts() {
//        ContentResolver cr = getContentResolver();
//        //取得电话本中开始一项的光标
//        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        //向下移动光标
//        while (cursor.moveToNext()) {
//            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//            contactNumber = contactNumber.replace("-", "");
//            contactNumber = contactNumber.replace(" ", "");
//            Contact contact = new Contact(contactName, contactNumber);
//            contacts.add(contact);
//        }

        try {
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            Cursor cursor = getContentResolver().query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactNumber = contactNumber.replace("-", "");
                contactNumber = contactNumber.replace(" ", "");
                Contact contact = new Contact(contactName, contactNumber);
                if (contactName != null)
                    contacts.add(contact);
            }
            cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题
        } catch (Exception e) {
            e.printStackTrace();
        }
        uploadData();
    }

    private void uploadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Gson gson = new Gson();
        map.put("jsonDate", gson.toJson(contacts));
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
        HttpMethods.getInstance().addCheckContact(subscriber, map);
    }

}
