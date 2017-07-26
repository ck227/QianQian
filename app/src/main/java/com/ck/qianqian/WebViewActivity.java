package com.ck.qianqian;

import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ck.fragment.WebViewFragment;
import com.ck.network.HttpMethods;
import com.ck.network.HttpResult;
import com.ck.util.MyApplication;
import com.ck.util.ScreenRecorder;
import com.ck.util.Utils;
import com.ck.widget.LoadingDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

public class WebViewActivity extends BaseActivity {//implements View.OnClickListener

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.map_container)
    FrameLayout mapContainer;

    private Intent intent;
    private String title;
    private String url;
    private int type;

    private WebViewFragment webViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        setViews();
    }

    private void setViews() {

        intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        type = intent.getIntExtra("type", 0);
        titleName.setText(title);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("webView") == null) {
            webViewFragment = WebViewFragment.newInstance(url, type);
            fm.beginTransaction().add(R.id.map_container, webViewFragment, "webView").commit();
        }

        if (type == 2 || type == 3) {//支付宝验证，开始录屏
            mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
            //没有点击事件了
//            button.setVisibility(View.VISIBLE);
//            button.setOnClickListener(this);

            //开始录制
            Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, REQUEST_CODE);

//            new Handler().postDelayed(new Runnable() {
//
//                public void run() {
//                    //execute the task
//                    finishRecord();
//                }
//
//            }, 5000);
        }
    }

    public void finishRecord() {
        new Handler().postDelayed(new Runnable() {

            public void run() {
                //execute the task
                if (mRecorder != null) {
                    mRecorder.quit();
                    mRecorder = null;
                    uploadVideo();
                }
            }

        }, 3000);

    }

    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;
    private static final int REQUEST_CODE = 1;
    private File file;
    private LoadingDialog dialog;
    private String alipay_video = "";
    private String taobao_video = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        if (mediaProjection == null) {
            Log.e("@@", "media projection is null");
            return;
        }
        // video size
        final int width = 1280;
        final int height = 720;
        file = new File(Environment.getExternalStorageDirectory(),
                "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");
        final int bitrate = 6000000;
        mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection, file.getAbsolutePath());
        mRecorder.start();
        button.setText("停止录制");
        Toast.makeText(this, "正在录制...", Toast.LENGTH_SHORT).show();
    }
/*
    @Override
    public void onClick(View v) {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
//            button.setText("重新录制");
            button.setVisibility(View.GONE);
            //这里上传录制的内容
            uploadVideo();
        } else {
            Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, REQUEST_CODE);
        }
    }*/

    private void uploadVideo() {
        dialog = new LoadingDialog(this, R.style.MyCustomDialog);
        dialog.show();
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Subscriber subscriber = new Subscriber<HttpResult.UploadPicResponse>() {
            @Override
            public void onCompleted() {
//                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                dialog.dismiss();
                Utils.showSnackBar(WebViewActivity.this, titleName, "请稍后再试");
            }

            @Override
            public void onNext(HttpResult.UploadPicResponse response) {
                if (response.code == 0) {
                    if (type == 2) {
                        alipay_video = response.obj;
                        addAlipay();
                    } else if (type == 3) {
                        taobao_video = response.obj;
                        addTaobao();
                    }
                } else {
                    dialog.dismiss();//upload/image/zhf/2017-07-25/_1500965150717.png
                    Utils.showSnackBar(WebViewActivity.this, titleName, "上传失败");
                }
            }
        };
        HttpMethods.getInstance().uploadVideo(subscriber, fbody, map);
    }

    private void addAlipay() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("zfbVideoUrl", alipay_video);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
                finish();
//                button.setText("重新录制");
//                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("success", true);
                    setResult(0, intent);
                } else {
//                    button.setText("重新录制");
//                    button.setVisibility(View.VISIBLE);
                }
                finish();
            }
        };
        HttpMethods.getInstance().addAlipay(subscriber, map);
    }

    private void addTaobao() {
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", MyApplication.getInstance().getUserName());
        map.put("tbVideoUrl", taobao_video);
        Subscriber subscriber = new Subscriber<HttpResult.BaseResponse>() {
            @Override
            public void onCompleted() {
                dialog.cancel();
            }

            @Override
            public void onError(Throwable e) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), R.string.plz_try_later, Toast.LENGTH_SHORT).show();
//                button.setText("重新录制");
//                button.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(HttpResult.BaseResponse response) {
                Toast.makeText(getApplicationContext(), response.msg, Toast.LENGTH_SHORT).show();
                if (response.code == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("success", true);
                    setResult(0, intent);
                }
                finish();
            }
        };
        HttpMethods.getInstance().addTaobao(subscriber, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
        }
    }

}
