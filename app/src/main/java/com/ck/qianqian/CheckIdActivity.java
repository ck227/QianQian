package com.ck.qianqian;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.util.Utils;
import com.ck.widget.ChoosePhotoDialog;
import com.ck.widget.LoadingDialog;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.functions.Action1;

public class CheckIdActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.idNumber)
    EditText idNumber;
    @BindView(R.id.frontImg)
    ImageView frontImg;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.personImg)
    ImageView personImg;
    @BindView(R.id.submit)
    TextView submit;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_id);
        ButterKnife.bind(this);
        titleName.setText("身份证认证");
    }

    @OnClick({R.id.frontImg, R.id.backImg, R.id.personImg, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frontImg:
                type = 1;
                showSelectDialog();
                break;
            case R.id.backImg:
                type = 2;
                showSelectDialog();
                break;
            case R.id.personImg:
                type = 3;
                showSelectDialog();
                break;
            case R.id.submit:
                if (checkValue()) {
                    uploadData();
                }
                break;
        }
    }

    private void showSelectDialog() {
        ChoosePhotoDialog dialog = new ChoosePhotoDialog(
                CheckIdActivity.this,
                new ChoosePhotoDialog.ChooseListener() {

                    @Override
                    public void chooseGallery() {
                        // TODO Auto-generated method CheckIdActivity
                        RxPermissions.getInstance(CheckIdActivity.this)
                                .request(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里申请了两组权限
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean granted) {
                                        if (granted) {
                                            selectPicFromLocal();
                                        } else {
                                            //不同意，给提示
                                            Toast.makeText(CheckIdActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    }

                    @Override
                    public void chooseCamera() {
                        // TODO Auto-generated method stub
                        RxPermissions.getInstance(CheckIdActivity.this)
                                .request(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)//这里申请了两组权限
                                .subscribe(new Action1<Boolean>() {
                                    @Override
                                    public void call(Boolean granted) {
                                        if (granted) {
                                            selectPicFromCamera();
                                        } else {
                                            //不同意，给提示
                                            Toast.makeText(CheckIdActivity.this, "请同意软件的权限，才能继续提供服务", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    }
                });
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private File cameraFile;

    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, 1);
    }

    private void selectPicFromCamera() {
        if (!Utils.isExitsSdcard()) {
            Toast.makeText(this, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        cameraFile = new File(Utils.getSDPath() + "/jiedai/",
                System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                        MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) { // 本地相册
            startPhotoZoom(Uri.parse("file://" + getFilePath(data.getData())));
        } else if (requestCode == 2) {
            if (cameraFile != null && cameraFile.exists()) {// 拍照
                startPhotoZoom(Uri.parse("file://" + cameraFile));
            }
        } else if (requestCode == 3 && data != null) {// 裁剪后
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                saveFile(bitmap);
                uploadPic(dirFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 8);
        intent.putExtra("aspectY", 5);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private String getFilePath(Uri uri) {
        if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            return actualimagecursor.getString(actual_image_column_index);
        }
    }

    private File dirFile;

    public void saveFile(Bitmap bm) {
        dirFile = new File(Utils.getSDPath() + "/jiedai/",
                System.currentTimeMillis() + ".jpg");
        dirFile.getParentFile().mkdirs();
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LoadingDialog dialog;
    private String frontImgUrl = "";
    private String backImgUrl = "";
    private String totalImgUrl = "";

    private void uploadPic(File file) {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("type", "1");

        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        Subscriber subscriber = new Subscriber<HttpResult.UploadPicResponse>() {
            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Utils.showSnackBar(CheckIdActivity.this, titleName, "请稍后再试");
            }

            @Override
            public void onNext(HttpResult.UploadPicResponse response) {
                if (response.code == 0) {
                    if (type == 1) {
                        frontImgUrl = response.obj;
                        Glide.with(CheckIdActivity.this).load(frontImgUrl).into(frontImg);
                    } else if (type == 2) {
                        backImgUrl = response.obj;
                        Glide.with(CheckIdActivity.this).load(backImgUrl).into(backImg);
                    } else if (type == 3) {
                        totalImgUrl = response.obj;
                        Glide.with(CheckIdActivity.this).load(totalImgUrl).into(personImg);
                    }
                } else {
                    Utils.showSnackBar(CheckIdActivity.this, titleName, "上传失败");
                }
            }
        };
        HttpMethods.getInstance().uploadPic(subscriber, fbody, map);
    }

    //下面是提交信息的
    private Boolean checkValue() {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(idNumber.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (frontImgUrl.equals("")) {
            Toast.makeText(getApplicationContext(), "请上传身份证正面", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (backImgUrl.equals("")) {
            Toast.makeText(getApplicationContext(), "请上传身份证反面", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (totalImgUrl.equals("")) {
            Toast.makeText(getApplicationContext(), "请上传手持身份证照片", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void uploadData() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("cardName", name.getText().toString());
        map.put("cardNumber", idNumber.getText().toString());
        map.put("cardImg1", frontImgUrl);
        map.put("cardImg2", backImgUrl);
        map.put("cardImg3", totalImgUrl);
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
        HttpMethods.getInstance().addCheckId(subscriber, map);
    }
}
