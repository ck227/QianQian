package com.ck.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.ck.qianqian.R;


/**
 * Created by cnbs5 on 2017/7/21.
 */

public class PayNowDialog extends Dialog {

    private Context context;
    private ButtonListener listener;

    public interface ButtonListener {
        void submit();
    }

    public PayNowDialog(Context context, ButtonListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_now);
    }
}
