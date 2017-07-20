package com.ck.qianqian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ck.fragment.WebViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.titleName)
    TextView titleName;
    @BindView(R.id.map_container)
    FrameLayout mapContainer;

    private Intent intent;
    private String title;
    private String url;

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
        titleName.setText(title);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag("webView") == null) {
            fm.beginTransaction().add(R.id.map_container, WebViewFragment.newInstance(url), "webView").commit();
        }
    }
}
