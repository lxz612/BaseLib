package com.lxz.basesdk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.lxz.basesdk.R;
import com.lxz.basesdk.views.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: Activity基类
 * Created by xianzhen.li on 2018/4/11.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().isRegistered(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //---------------- LoadingDialog 统一入口 ----------------------//
    private LoadingDialog mLoadingDialog;
    private String mLastMsg = null;

    public void showLoadingDialog() {
        showLoadingDialog(R.string.loading_message);
    }

    public void showLoadingDialog(int strId) {
        showLoadingDialog(getString(strId), true);
    }

    public void showLoadingDialog(String msg) {
        showLoadingDialog(msg, true);
    }

    public void showLoadingDialog(int strId, boolean flag) {
        showLoadingDialog(getString(strId), flag);
    }

    public void showLoadingDailog(int resId, boolean flag) {
        showLoadingDialog();
    }

    public void showLoadingDialog(String msg, boolean flag) {
        if ((mLoadingDialog != null && mLoadingDialog.isShowing()) && TextUtils.equals(msg, mLastMsg))
            return;
        dismissLoadingDialog();
        mLastMsg = msg;
        mLoadingDialog = new LoadingDialog(this, msg);
        mLoadingDialog.setCancelable(flag);
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
    //---------------- LoadingDialog 统一入口 ----------------------//
}
