package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by lixiang on 2018/3/21.
 */

public class SquareRoundImageView extends RoundImageView{
    public SquareRoundImageView(Context context) {
        super(context);
    }

    public SquareRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //传入参数widthMeasureSpec、heightMeasureSpec
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
