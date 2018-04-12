package com.lxz.basesdk.views.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lxz.basesdk.R;

/**
 * Description: Loading动画
 * Created by xianzhen.li on 2018/4/12.
 */

public class LoadingView extends FrameLayout {

    private static final int DEFAULT_COLOR = 0xFFFF4965;

    private CircleView mCircleView;
    private ImageView mIvLogo;

    public LoadingView(@NonNull Context context) {
        this(context,null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final float defaultLogoSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22f, metrics);// logo尺寸
        final float defaultDiameter = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 44f, metrics);// 圆圈直径
        final float defaultStrokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, metrics);// 圆圈线条宽度

        LayoutParams logoLp = new LayoutParams((int) defaultLogoSize, (int) defaultLogoSize);
        logoLp.gravity = Gravity.CENTER;

        mIvLogo = new ImageView(context);
        mIvLogo.setImageResource(R.drawable.ic_loading_bei);
        mIvLogo.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(mIvLogo, logoLp);

        LayoutParams circleLp = new LayoutParams((int) defaultDiameter, (int) defaultDiameter);
        circleLp.gravity = Gravity.CENTER;

        mCircleView = new CircleView(context);
        addView(mCircleView, circleLp);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            int color = a.getInt(R.styleable.LoadingView_loading_circle_color, DEFAULT_COLOR);
            float diameter = a.getDimension(R.styleable.LoadingView_loading_circle_diameter, defaultDiameter);
            float strokeWidth = a.getDimension(R.styleable.LoadingView_loading_stroke_width, defaultStrokeWidth);
            mCircleView.setColor(color);
            mCircleView.setDiameter(diameter);
            mCircleView.setStrokeWidth(strokeWidth);
            float size = a.getDimension(R.styleable.LoadingView_loading_img_size, defaultLogoSize);
            setImgSize(size);
            int imgSrc = a.getResourceId(R.styleable.LoadingView_loading_img_src, R.drawable.ic_loading_bei);
            setImgResource(imgSrc);
            a.recycle();
        }

        if (LoadingViewConfiguration.getInstance().mImgResId != -1) {
            setImgResource(LoadingViewConfiguration.getInstance().mImgResId);
        }
        if (LoadingViewConfiguration.getInstance().mImgSize != -1f) {
            setImgSize(LoadingViewConfiguration.getInstance().mImgSize);
        }
        if (LoadingViewConfiguration.getInstance().mColor != -1) {
            mCircleView.setColor(LoadingViewConfiguration.getInstance().mColor);
        }
        if (LoadingViewConfiguration.getInstance().mStrokeWidth != -1f) {
            mCircleView.setStrokeWidth(LoadingViewConfiguration.getInstance().mStrokeWidth);
        }
        if (LoadingViewConfiguration.getInstance().mDiameter != -1f) {
            mCircleView.setDiameter(LoadingViewConfiguration.getInstance().mDiameter);
        }
    }

    public void setColor(int color) {
        mCircleView.setColor(color);
    }

    public void setImgSize(float size) {
        LayoutParams layoutParams = new LayoutParams((int) size, (int) size);
        layoutParams.gravity = Gravity.CENTER;
        mIvLogo.setLayoutParams(layoutParams);
    }

    public void setImgResource(int imgResource) {
        mIvLogo.setImageResource(imgResource);
    }

    public void setDiameter(float diameter) {
        mCircleView.setDiameter(diameter);
    }

    public void setStrokeWidth(float strokeWidth) {
        mCircleView.setStrokeWidth(strokeWidth);
    }

}
