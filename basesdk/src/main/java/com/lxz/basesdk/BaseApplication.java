package com.lxz.basesdk;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.List;

/**
 * Description: 基础Application（需要在应用初始时设置）
 * Created by xianzhen.li on 2018/4/12.
 */

public class BaseApplication {

    // BaseApplication.application = getApplication();
    // BaseApplication.context = getApplication();
    public static Application application = null;
    public static Context context = null;

    // 获取应用全局的Application
    public static Application getApp() {
        return application;
    }

    // 获取当前Activity
    public static Activity getCurrentActivity() {
        return BaseActivityLifeCycle.getInstance().getCurrentActivity();
    }

    // 应用是否在前台
    public static boolean isAppOnForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return BaseActivityLifeCycle.getInstance().isForeground();
        } else {
            ActivityManager mActivityManager = ((ActivityManager) BaseApplication.application.getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningTaskInfo> taskInfos = mActivityManager.getRunningTasks(1);
            return null != taskInfos
                    && taskInfos.size() > 0
                    && TextUtils.equals(BaseApplication.application.getPackageName(),
                    taskInfos.get(0).topActivity.getPackageName());
        }
    }
}
