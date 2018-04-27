package com.lxz.basesdk.views.viewpagerindicator;

import android.support.v4.view.ViewPager;

/**
 * Description: 指示器
 * Created by xianzhen.li on 2018/4/26.
 */

public interface Indicator extends ViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     *
     * @param mViewPager viewpager
     */
    void setViewPager(ViewPager mViewPager);

    /**
     * Bind the indicator to a ViewPager.
     *
     * @param mViewPager            viewpager
     * @param initialPosition 初始位置
     */
    void setViewPager(ViewPager mViewPager, int initialPosition);

    /**
     * Set the current page of both the ViewPager and indicator.
     * 设置ViewPager和indicator当前项
     *
     * @param item
     */
    void setCurrentItem(int item);

    /**
     * Set a page change listener which will receive forwarded events.
     * 设置页面切换事件监听器
     *
     * @param listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * Notify the indicator that the fragment list has changed.
     */
    void notifyDataSetChanged();
}
