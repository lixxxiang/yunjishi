package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class DetailScrollView extends ScrollView {
    public OnScrollChangeListener onScrollChangeListener;

    public View contentView;

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener)
    {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public DetailScrollView(Context context) {
        super(context);
    }

    public DetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
    }

    public interface OnScrollChangeListener {
        void onScrollChange(DetailScrollView view, int x, int y, int oldx, int oldy);

        void onScrollBottomListener();

        void onScrollTopListener();
    }

    /**
     * l当前水平滚动的开始位置
     * t当前的垂直滚动的开始位置
     * oldl上一次水平滚动的位置。
     * oldt上一次垂直滚动的位置。
     **/
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null)
        {
            onScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
        {
            onScrollChangeListener.onScrollBottomListener();
        }
        if (t == 0 || t + getHeight() > contentView.getHeight() && onScrollChangeListener != null)
        {
            onScrollChangeListener.onScrollTopListener();
        }
    }
}
