package com.lxz.basesdk.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.lxz.basesdk.BaseApplication;
import com.lxz.basesdk.R;

/**
 * Description: Fragment管理类（注意：Fragment的容器的id必须为R.id.ll_main）
 * Created by xianzhen.li on 2018/4/23.
 */

public class BaseFragmentManager {

    private String mLastFragmentTag;
    private FragmentManager mFragmentManager;

    public BaseFragmentManager(FragmentActivity context) {
        this.mFragmentManager = context.getSupportFragmentManager();
    }

    public BaseFragmentManager(Fragment fragment) {
        this.mFragmentManager = fragment.getChildFragmentManager();
    }

    public void switchFragment(String key, Bundle bundle) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(key);
        if (fragment == null) {
            fragment = Fragment.instantiate(BaseApplication.getApp(), key, bundle);
        }

        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        ft.replace(R.id.ll_main, fragment, key);

        this.mLastFragmentTag = key;
        ft.commitAllowingStateLoss();
        this.mFragmentManager.executePendingTransactions();
    }

    public void switchFragment(String key, Bundle bundle, int enterAnimation, int exitAnimation, int popEnterAnimation, int popExitAnimation) {
        this.switchFragment(true, key, bundle, enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation);
    }

    public void switchFragment(boolean addBackStack, String key, Bundle bundle, int enterAnimation, int exitAnimation, int popEnterAnimation, int popExitAnimation) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(key);
        if (fragment == null) {
            fragment = Fragment.instantiate(BaseApplication.getApp(), key, bundle);
        }

        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation);
        ft.replace(R.id.ll_main, fragment, key);
        if (addBackStack) {
            ft.addToBackStack(key);
        }

        this.mLastFragmentTag = key;
        ft.commitAllowingStateLoss();
        this.mFragmentManager.executePendingTransactions();
    }

    /**
     * 通过Fragment的类名来切换Fragment
     *
     * @param key    Fragment类名
     * @param bundle bundle
     */
    public void switchFragmentWithCache(String key, Bundle bundle) {
        if (this.mLastFragmentTag == null || !TextUtils.equals(this.mLastFragmentTag, key)) {
            FragmentTransaction ft = this.mFragmentManager.beginTransaction();
            if (this.mLastFragmentTag != null) {
                ft.hide(this.mFragmentManager.findFragmentByTag(this.mLastFragmentTag));
            }

            Fragment fragment = this.mFragmentManager.findFragmentByTag(key);
            if (fragment == null) {
                // 通过Fragment类名来实例化Fragment，同时将类名作为Fragment的Tag
                fragment = Fragment.instantiate(BaseApplication.getApp(), key, bundle);
                ft.add(R.id.ll_main, fragment, key);
            } else {
                ft.show(fragment);
            }

            this.mLastFragmentTag = key;
            ft.commitAllowingStateLoss();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public void switchSameFragmentWithCache(String key, String fragmentName, Bundle bundle) {
        if (this.mLastFragmentTag == null || !TextUtils.equals(this.mLastFragmentTag, key)) {
            FragmentTransaction ft = this.mFragmentManager.beginTransaction();
            if (this.mLastFragmentTag != null) {
                ft.hide(this.mFragmentManager.findFragmentByTag(this.mLastFragmentTag));
            }

            Fragment fragment = this.mFragmentManager.findFragmentByTag(key);
            if (fragment == null) {
                fragment = Fragment.instantiate(BaseApplication.getApp(), fragmentName, bundle);
                ft.add(R.id.ll_main, fragment, key);
            } else {
                ft.show(fragment);
            }

            this.mLastFragmentTag = key;
            ft.commitAllowingStateLoss();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public void switchFragmentWithoutCache(boolean addBackStack, String key, Bundle bundle) {
        Fragment fragment = Fragment.instantiate(BaseApplication.getApp(), key, bundle);
        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        ft.replace(R.id.ll_main, fragment, key);
        if (addBackStack) {
            ft.addToBackStack((String) null);
        }

        this.mLastFragmentTag = key;
        ft.commitAllowingStateLoss();
        this.mFragmentManager.executePendingTransactions();
    }

    public void switchFragmentWithoutCache(boolean addBackStack, String key, Bundle bundle, int enterAnimation, int exitAnimation, int popEnterAnimation, int popExitAnimation) {
        Fragment fragment = Fragment.instantiate(BaseApplication.getApp(), key, bundle);
        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        ft.setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation);
        ft.replace(R.id.ll_main, fragment, key);
        if (addBackStack) {
            ft.addToBackStack((String) null);
        }

        this.mLastFragmentTag = key;
        ft.commitAllowingStateLoss();
        this.mFragmentManager.executePendingTransactions();
    }

    public void removeFragment(String key) {
        Fragment fragment = this.mFragmentManager.findFragmentByTag(key);
        if (fragment != null) {
            FragmentTransaction ft = this.mFragmentManager.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
            this.mFragmentManager.executePendingTransactions();
        }

    }

    public void detachLastFragment() {
        if (this.mLastFragmentTag != null && this.mFragmentManager.findFragmentByTag(this.mLastFragmentTag) != null) {
            FragmentTransaction ft = this.mFragmentManager.beginTransaction();
            Fragment fragment = this.mFragmentManager.findFragmentByTag(this.mLastFragmentTag);
            ft.detach(fragment);
            this.mLastFragmentTag = null;
            ft.commitAllowingStateLoss();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public Fragment getFragment(String key) {
        return this.mFragmentManager.findFragmentByTag(key);
    }

    public String getLastFragmentTag() {
        return this.mLastFragmentTag;
    }

    public int getBackStackEntryCount() {
        return this.mFragmentManager.getBackStackEntryCount();
    }

    public String getBackStackEntryName(int Index) {
        return this.mFragmentManager.getBackStackEntryAt(Index).getName();
    }
}
