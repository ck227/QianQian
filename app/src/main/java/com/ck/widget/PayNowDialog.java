package com.ck.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ck.qianqian.R;


/**
 * Created by cnbs5 on 2017/7/21.
 */

public class PayNowDialog extends Dialog {

    private Context context;
    private ButtonListener listener;

    private TextView money;
    private RelativeLayout alipay, alipay2, weixinPay;
    private ImageView select_alipay, select_alipay2, select_weixinPay;

    private TextView yes, no;

    private String moneyAmount;

    private int selectType = 0;

    public interface ButtonListener {
        void submit(int type);
    }

    public PayNowDialog(Context context, ButtonListener listener, String moneyAmount) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.moneyAmount = moneyAmount;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_now);

        money = (TextView) findViewById(R.id.money);
        alipay = (RelativeLayout) findViewById(R.id.alipay);
        alipay2 = (RelativeLayout) findViewById(R.id.alipay2);
        weixinPay = (RelativeLayout) findViewById(R.id.weixinPay);
        select_alipay = (ImageView) findViewById(R.id.select_alipay);
        select_alipay2 = (ImageView) findViewById(R.id.select_alipay2);
        select_weixinPay = (ImageView) findViewById(R.id.select_weixinPay);

        yes = (TextView) findViewById(R.id.yes);
        no = (TextView) findViewById(R.id.no);

        money.setText(moneyAmount + "元");
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = 0;
                select_alipay.setVisibility(View.VISIBLE);
                select_alipay2.setVisibility(View.INVISIBLE);
                select_weixinPay.setVisibility(View.INVISIBLE);
            }
        });
        alipay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = 1;
                select_alipay.setVisibility(View.INVISIBLE);
                select_alipay2.setVisibility(View.VISIBLE);
                select_weixinPay.setVisibility(View.INVISIBLE);
            }
        });
        weixinPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType = 2;
                select_alipay.setVisibility(View.INVISIBLE);
                select_alipay2.setVisibility(View.INVISIBLE);
                select_weixinPay.setVisibility(View.VISIBLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.submit(selectType);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
