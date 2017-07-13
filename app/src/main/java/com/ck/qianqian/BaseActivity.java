package com.ck.qianqian;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cnbs5 on 2017/7/13.
 */

public class BaseActivity extends AppCompatActivity {

    public TextView titleName;

    public void back(View view) {
        finish();
    }
}
