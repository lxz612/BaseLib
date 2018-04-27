package com.lxz.basesdk.views.viewpagerindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxz.basesdk.R;
import com.lxz.basesdk.utils.CommonUtil;

/**
 * Description: 简单ViewPager指示器-样式固定
 * Created by xianzhen.li on 2018/4/26.
 */
public class SimpleCircleDotsIndicator extends LinearLayout implements Indicator {
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mOldPosition = -1;
    private int mInitialPosition = 0;

    public SimpleCircleDotsIndicator(Context context) {
        this(context, null);
    }

    public SimpleCircleDotsIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCircleDotsIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView() {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        this.removeAllViews();
        if (mViewPager != null) {
            int count = mViewPager.getAdapter().getCount();
            if (count == 1) return;
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(getContext());
                imageView.setBackgroundResource(R.drawable.selector_indicator_circledots);
                int width = CommonUtil.dip2px(7F);
                int margin = CommonUtil.dip2px(3F);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
                params.setMargins(margin, 0, margin, 0);
                imageView.setLayoutParams(params);
                if (mInitialPosition == i) {
                    imageView.setEnabled(true);
                } else {
                    imageView.setEnabled(false);
                }
                this.addView(imageView);
            }
        }
    }

    @Override
    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        this.setCurrentItem(0);
    }

    @Override
    public void setViewPager(ViewPager mViewPager, int initialPosition) {
        this.mViewPager = mViewPager;
        this.setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mOldPosition = item;
        mInitialPosition = item;
        mViewPager.setCurrentItem(item);
        mViewPager.addOnPageChangeListener(this);
        initView();
        invalidate();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        getChildAt(mOldPosition).setEnabled(false);
        getChildAt(position).setEnabled(true);
        mOldPosition = position;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
