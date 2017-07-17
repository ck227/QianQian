package com.ck.util;

import android.content.Context;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.ck.qianqian.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cnbs5 on 2017/7/15.
 */

public class Utils {

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(1[3,4,5,7,8][0-9])\\d{8}$");
        Matcher m;
        try {
            m = p.matcher(mobiles);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return m.matches();
    }

    public static void showSnackBar(Context context, View view, String msg) {
        try {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("好的", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).setActionTextColor(context.getResources().getColor(R.color.text_blue)).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static String getSDPath() {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
        }
        return sdDir.toString();
    }

}
