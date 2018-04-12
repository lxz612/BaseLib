package com.lxz.basesdk.views.loading;

/**
 * Description:
 * Created by xianzhen.li on 2018/4/12.
 */

public class LoadingViewConfiguration {

    int mImgResId = -1;
    float mImgSize = -1f;
    int mColor = -1;
    float mStrokeWidth = -1f;
    float mDiameter = -1f;

    public LoadingViewConfiguration setImgSrc(int resId) {
        mImgResId = resId;
        return this;
    }

    public LoadingViewConfiguration setImgSize(float imgSize) {
        mImgSize = imgSize;
        return this;
    }

    public LoadingViewConfiguration setColor(int color) {
        mColor = color;
        return this;
    }

    public LoadingViewConfiguration setDiameter(float diameter) {
        mDiameter = diameter;
        return this;
    }

    public LoadingViewConfiguration setStrokeWidth(float width) {
        mStrokeWidth = width;
        return this;
    }


    @Deprecated
    private LoadingViewConfiguration() {
    }

    private static class SingletonHolder {
        static LoadingViewConfiguration INSTANCE = new LoadingViewConfiguration();
    }

    public static LoadingViewConfiguration getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
