package com.lxz.basesdk.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.lxz.basesdk.R;
import com.lxz.basesdk.utils.PreferenceUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Description:
 * Created by xianzhen.li on 2018/4/24.
 */

public class BaseSwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;
    //新安装应用，在某些页面不提示
    protected boolean mShowSwipeGuide = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
        this.getSwipeBackLayout().setScrimColor(Color.argb(0, 255, 0, 0));
        this.getSwipeBackLayout().setShadow(R.drawable.swipeback_shadow_left, SwipeBackLayout.EDGE_LEFT);

        // show user guide if needed
        if (!PreferenceUtil.getBoolean(this, "swipe_back", false) && mShowSwipeGuide) {
            PreferenceUtil.setBoolean(this, "swipe_back", true);

            final View view = LayoutInflater.from(this).inflate(R.layout.layout_tips_swipe_back, null);
            addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(300);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ViewGroup) view.getParent()).removeView(view);
                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    view.startAnimation(animation);
                }
            }, 3000);
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
