package com.lxz.basesdk.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description:
 * Created by xianzhen.li on 2018/1/8.
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context, CharSequence text) {
        if (TextUtils.isEmpty(text)) return;

        try {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setDuration(Toast.LENGTH_LONG);
                mToast.setText(text);
            }
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void showToastShort(Context context, CharSequence text) {
        if (TextUtils.isEmpty(text)) return;

        try {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setText(text);
            }
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
