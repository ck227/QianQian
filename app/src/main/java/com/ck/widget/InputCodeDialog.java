package com.ck.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ck.qianqian.R;


/**
 * Created by cnbs5 on 2017/8/25.
 */

public class InputCodeDialog extends Dialog {

    private Context context;
    private EditText code;
    private TextView submit;

    private ButtonListener listener;

    public interface ButtonListener {
        void button(String code);
    }

    public InputCodeDialog(Context context, ButtonListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_code);
        code = (EditText) findViewById(R.id.code);
        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.button(code.getText().toString());
            }
        });
    }


}
