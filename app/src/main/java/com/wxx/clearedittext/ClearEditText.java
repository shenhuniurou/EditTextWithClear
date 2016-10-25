package com.wxx.clearedittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by wxx
 * on 2016/10/25.
 * 带删除功能的EditText
 */

public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable ;

    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public ClearEditText(Context context) {
        this(context, null);
    }


    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }


    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        //DrawableRight是Drawable数组中的第三个元素
        mClearDrawable = getCompoundDrawables()[2];
        if ( mClearDrawable == null ) {
            mClearDrawable = getResources().getDrawable(R.drawable.chacha);
        }

        mClearDrawable.setBounds(0 , 0, mClearDrawable.getIntrinsicWidth() , mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    @Override public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //这里根据手指抬起时的触摸点是否在删除图标的区域内来判断是否点击了删除

            //计算删除图标所占的区域
            if (getCompoundDrawables()[2] != null) {
                //只考虑水平方向
                //getWidth() - getTotalPaddingRight()表示控件左边到图标左边的距离
                //getWidth() - getPaddingRight()表示控件左边到图标右边的距离
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    //点击清除图标
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }


    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(hasFoucs) {
            //如果输入框中没有内容了就隐藏删除图标
            setClearIconVisible(text.length() > 0);
        }
    }


    @Override public void afterTextChanged(Editable s) {

    }


    /**
     * 根据是否获取了焦点和输入框内容长度来设置删除图标的隐藏
     * @param v
     * @param hasFocus
     */
    @Override public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            //失去焦点后隐藏图标
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

}
