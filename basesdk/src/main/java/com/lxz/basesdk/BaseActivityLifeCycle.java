package com.lxz.basesdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Description: Activity生命周期管理
 * Created by xianzhen.li on 2018/4/12.
 */

public class BaseActivityLifeCycle implements Application.ActivityLifecycleCallbacks {

    protected final String TAG = "BaseActivityLifeCycle";

    private static BaseActivityLifeCycle sInstance;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mCheckForegroundRunnable;
    private boolean mForeground = false;// App处于前台
    private boolean mPaused = true;

    private WeakReference<Activity> mActivityReference;// 当前Activity的弱引用

    public static synchronized BaseActivityLifeCycle getInstance() {
        if (sInstance == null) {
            sInstance = new BaseActivityLifeCycle();
        }
        return sInstance;
    }

    public Activity getCurrentActivity() {
        if (mActivityReference != null) {
            return mActivityReference.get();
        }
        return null;
    }

    public boolean isForeground() {
        return mForeground;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityCreated");
        mActivityReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityResumed");
        mPaused = false;
        if (!mForeground) {
            Log.d(TAG, "App to Foreground！");
        }
        mForeground = true;
        if (mCheckForegroundRunnable != null) {
            mHandler.removeCallbacks(mCheckForegroundRunnable);
        }
        mActivityReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityPaused");
        mPaused = true;
        if (mCheckForegroundRunnable != null) {
            mHandler.removeCallbacks(mCheckForegroundRunnable);
        }
        mHandler.postDelayed(mCheckForegroundRunnable = new Runnable() {
            @Override
            public void run() {
                if (mPaused && mForeground) {
                    Log.d(TAG, "App to Background！");
                    mForeground = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, activity.getLocalClassName() + " " + "onActivityDestroyed");
    }
}
