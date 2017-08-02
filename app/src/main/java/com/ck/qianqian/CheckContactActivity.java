package com.ck.qianqian;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.bean.Contact;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.widget.LoadingDialog;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

public class CheckContactActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.submit)
    TextView submit;

    private LoadingDialog dialog;
    private ArrayList<Contact> contacts;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_contact);
        ButterKnife.bind(this);
        titleName.setText("通信认证");
        contacts = new ArrayList<>();

        rxPermissions = new RxPermissions(this);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    icon.setImageResource(R.mipmap.check_contact_sure2);
                    submit.setClickable(false);
                    submit.setBackgroundResource(R.drawable.shape_gray_round);
                } else {
                    icon.setImageResource(R.mipmap.check_contact_sure);
                    submit.setClickable(false);
                    submit.setBackgroundResource(R.drawable.login_btn);
                }
                isEnable = !isEnable;
            }
        });
    }

    private Boolean isEnable = true;

    @OnClick({R.id.contact, R.id.msg, R.id.submit})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.contact:
                intent = new Intent(CheckContactActivity.this, WebViewActivity.class);
                intent.putExtra("title", "信息协议");
                intent.putExtra("url", HttpMethods.BASE_URL + "service/service.html?key=USER_SERVICE" + "&loginName=" + MyApplication.getInstance().getUserName());
                startActivity(intent);
                break;
            case R.id.msg:
                intent = new Intent(CheckContactActivity.this, WebViewActivity.class);
                intent.putExtra("title", "信息协议");
                intent.putExtra("url", HttpMethods.BASE_URL + "service/service.html?key=USER_SERVICE");
                startActivity(intent);
                break;
            case R.id.submit:
                rxPermissions
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

                                /*if (permission.granted) {
                                    dialog = new LoadingDialog(CheckContactActivity.this, R.style.MyCustomDialog);
                                    dialog.show();
                                    contacts.clear();//避免数据重复
                                    getContacts();
                                } else if(permission.shouldShowRequestPermissionRationale){
                                    // Denied permission without ask never again
                                    Toast.makeText(getApplicationContext(), "请同意软件权限", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "请手动开启权限", Toast.LENGTH_SHORT).show();
                                }*/

                            }
                        });


                break;
        }
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
//        uploadData();
        int size = contacts.size();
        int page = size / 20 + 1;
        for (int i = 1; i < page; i++) {
            if (i < page - 1) {
                List<Contact> data = contacts.subList((i - 1) * 20, (i - 1) * 20 + 19);
                uploadData(data, false);
            } else {
                List<Contact> data = contacts.subList((i - 1) * 20, contacts.size() - 1);
                uploadData(data, true);
            }
        }
    }

//    private Boolean hasFinish = false;

    private void uploadData(List<Contact> data, final Boolean isLast) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        Gson gson = new Gson();
        map.put("jsonDate", gson.toJson(data));

//        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"),
//                "{\"jsonDate\":"+gson.toJson(contacts)+"}");
//        Gson gson=new Gson();
//        HashMap<String,String> paramsMap=new HashMap<>();
//        paramsMap.put("jsonDate",gson.toJson(contacts));
//        paramsMap.put("jsonDate","[{bookName:fdsfsf,bookPhone:24324324}]");
//        String strEntity = gson.toJson(paramsMap);
//        String strEntity = "jsonDate="+gson.toJson(contacts);
//        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);


        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
                if (isLast)
                    dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                if (isLast)
                    dialog.cancel();
//                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    if (isLast) {
                        Intent intent = new Intent();
                        intent.putExtra("success", true);
                        setResult(0, intent);
                        finish();
                    }
                }
            }
        };
        HttpMethods.getInstance().addCheckContact(subscriber, map);
    }


}


