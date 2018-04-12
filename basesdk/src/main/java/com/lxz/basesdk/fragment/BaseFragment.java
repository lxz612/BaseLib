package com.lxz.basesdk.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lxz.basesdk.BaseApplication;
import com.lxz.basesdk.R;
import com.lxz.basesdk.activity.BaseActivity;
import com.lxz.basesdk.annotation.Releasable;
import com.lxz.basesdk.views.loading.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

/**
 * Description: Fragment基类
 * Created by xianzhen.li on 2018/4/12.
 */

public class BaseFragment extends Fragment {

    protected View mFragmentView;
    protected Application mApp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = BaseApplication.getApp();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mFragmentView = super.onCreateView(inflater, container, savedInstanceState);
        return this.mFragmentView;
    }

    public void setTitle(int resId) {
        this.setTitle(this.getResources().getString(resId));
    }

    public void setTitle(String string) {
        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(string);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            this.getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public View findViewById(int id) {
        return this.mFragmentView == null ? null : this.mFragmentView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        this.mFragmentView = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        try {
            this.releaseViews();
        } catch (IllegalAccessException var2) {
            var2.printStackTrace();
        }
        this.dismissLoadingDialog();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, (Object) null);
        } catch (NoSuchFieldException var2) {
            throw new RuntimeException(var2);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException(var3);
        }
    }

    private void releaseViews() throws IllegalAccessException {
        for (Class cls = this.getClass(); BaseFragment.class != cls; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            Field[] var3 = fields;
            int var4 = fields.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                if (field.isAnnotationPresent(Releasable.class)) {
                    if (field.isAccessible()) {
                        field.set(this, (Object) null);
                    } else {
                        field.setAccessible(true);
                        field.set(this, (Object) null);
                        field.setAccessible(false);
                    }
                }
            }
        }
    }

    // -------------------------------------- LoadingDialog ----------------------------------------
    private LoadingDialog mLoadingDialog;
    private String mLastMsg = null;

    protected void showLoadingDialog() {
        this.showLoadingDialog(R.string.loading_message);
    }

    public void showLoadingDialog(int strId) {
        this.showLoadingDialog(this.getString(strId), true);
    }

    public void showLoadingDialog(String msg) {
        this.showLoadingDialog(msg, true);
    }

    public void showLoadingDialog(int strId, boolean flag) {
        this.showLoadingDialog(this.getString(strId), flag);
    }

    protected void showLoadingDialog(String msg, boolean flag) {
        if (this.getActivity() != null) {
            if (this.getActivity() instanceof BaseActivity) {
                ((BaseActivity) this.getActivity()).showLoadingDialog(msg);
            } else {
                if (TextUtils.equals(msg, this.mLastMsg)) {
                    return;
                }
                this.dismissLoadingDialog();
                this.mLastMsg = msg;
                this.mLoadingDialog = new LoadingDialog(this.getActivity(), msg);
                this.mLoadingDialog.setCancelable(flag);
                this.mLoadingDialog.show();
            }
        }
    }

    protected void dismissLoadingDialog() {
        if (this.getActivity() instanceof BaseActivity) {
            ((BaseActivity) this.getActivity()).dismissLoadingDialog();
        } else if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.dismiss();
            this.mLoadingDialog = null;
        }
    }
}
