package com.ck.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ck.qianqian.R;

/**
 * Created by cnbs5 on 2017/7/17.
 */

public class ChoosePhotoDialog extends Dialog {

    private Context context;
    private RelativeLayout camera, gallery;
    private ChooseListener listener;

    public interface ChooseListener {
        void chooseCamera();

        void chooseGallery();
    }

    public ChoosePhotoDialog(Context context, ChooseListener listener) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listener = listener;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_photo);

        camera = (RelativeLayout) findViewById(R.id.camera);
        gallery = (RelativeLayout) findViewById(R.id.gallery);
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                listener.chooseCamera();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dismiss();
                listener.chooseGallery();
            }
        });

    }

}