package com.ck.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ck.qianqian.R;
import com.ck.qianqian.WebViewActivity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cnbs5 on 17/3/24.
 */

public class WebViewFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;

    private View view;
    private String realUrl;

    private int type;

    public static WebViewFragment newInstance(String url, int type) {

        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("type", type);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater
                    .inflate(R.layout.fragment_webview, container, false);
            ButterKnife.bind(this, view);
            realUrl = getArguments().getString("url");
            type = getArguments().getInt("type");
            setViews();
        }
        return view;
    }

    private void setViews() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(false);
        webView.getSettings().setAllowContentAccess(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new HelloWebViewClient());
        webView.loadUrl(realUrl);

//        webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
//                b.setTitle("");
//                b.setMessage(message);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                b.setCancelable(false);
//                b.create().show();
//                return true;
//            }
//        });
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.startsWith("taobao://") || url.startsWith("alipays://")) {
                    ((WebViewActivity) getActivity()).finishRecord();
                    //不跳转
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (type == 2) {
                if (url.contains("my.alipay.com")) {
                    ((WebViewActivity) getActivity()).finishRecord();
                }
            } else if (type == 3) {
                if (url.contains("www.taobao.com")) {
                    ((WebViewActivity) getActivity()).finishRecord();
                }
            }
            super.onPageFinished(view, url);
        }
    }

}