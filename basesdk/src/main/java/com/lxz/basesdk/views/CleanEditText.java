package com.lxz.basesdk.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.lxz.basesdk.R;

public class CleanEditText extends EditText {

    private Drawable cleanImg;

    public CleanEditText(Context context) {
        this(context,null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        cleanImg = context.getResources().getDrawable(R.drawable.ic_highlight_off_black_24dp);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length() < 1) {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(null, null, cleanImg, null);
                }
            }
        });
    }

    //当触摸范围在右侧时，触发删除方法，隐藏叉叉
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cleanImg != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}
