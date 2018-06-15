package com.lxz.basesdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.lxz.basesdk.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 通用工具
 * Created by xianzhen.li on 2018/4/24.
 */

public class CommonUtil {

    // 获取设备宽度
    public static int getWidthPixels(Context context) {
        if (context == null) {
            return 0;
        } else {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
    }

    // 获取设备高度
    public static int getHeightPixels(Context context) {
        if (context == null) {
            return 0;
        } else {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.heightPixels;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int dip2px(float dpValue) {
        float scale = BaseApplication.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int dip2px(Context context, int res) {
        float dpValue = (float) context.getResources().getDimensionPixelSize(res);
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }


    public static int resizeHeight(int w, int h) {
        return resizeHeight(w, h, getWidthPixels(BaseApplication.getApp()));
    }

    public static int resizeHeight(int w, int h, int displayWidth) {
        return resizeHeight(w, h, displayWidth, 0);
    }

    /**
     * 重设区块高度
     *
     * @param w                   宽度
     * @param h                   高度
     * @param displayWidth        显示宽度
     * @param defaultResizeHeight 默认返回的高度
     * @return 显示高度
     */
    public static int resizeHeight(int w, int h, int displayWidth, int defaultResizeHeight) {
        if (w > 0 && h > 0) {
            float ratio = 1.0F * (float) h / (float) w;
            return (int) ((float) displayWidth * ratio);
        } else {
            return defaultResizeHeight;
        }
    }

    /**
     *  是否是颜色值字符串
     * @param colorStr
     * @return
     */
    public static boolean isColorStr(String colorStr) {
        Pattern pattern = Pattern.compile("#[0-9a-fA-F]{6}");
        Matcher m = pattern.matcher(colorStr);
        return m.find();
    }

    public static boolean isEmpty(String str){
        if(str == null || str.length() <= 0){
            return true;
        }
        return false;
    }
}
