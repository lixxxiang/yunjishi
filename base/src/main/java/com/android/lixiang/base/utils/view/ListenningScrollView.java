package com.android.lixiang.base.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lixiang on 2018/3/14.
 */

public class ListenningScrollView extends ScrollView {

    /**
     * 上次的Y坐标
     */
    private int lastY = 0;
    private ScrollYListener scrollYListener;
    private ScrollListener scrollListener;
    private static final int SCROLL_TIME = 20;

    private static final int SCROLL_WHAT = 111;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCROLL_WHAT:
                    int scrollY = getScrollY();
                    System.out.println("收到滑动事件" + scrollY);
                    if (lastY != scrollY) {
                        lastY = scrollY;
                        scrollYListener.onScrollChanged(scrollY);
                        handler.sendEmptyMessageDelayed(SCROLL_WHAT, SCROLL_TIME);
                    }
                    break;
            }
        }
    };

    public ListenningScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollYViewListener(ScrollYListener scrollYListener) {
        this.scrollYListener = scrollYListener;
    }

    public void setScrollViewListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollYListener != null) {
            scrollYListener.onScrollChanged(t);
        }
        if(scrollListener != null){
            scrollListener.onScrollChanged(l,t,oldl,oldt,computeVerticalScrollRange());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (scrollYListener != null) {
                    handler.sendEmptyMessage(SCROLL_WHAT);
                    System.out.println("发送滑动事件");
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 带惯性的滑动监听器
     */
    public interface ScrollYListener {
        void onScrollChanged(int y);
    }

    /**
     * 滑动监听器
     */
    public interface ScrollListener {
        void onScrollChanged(int x, int y, int oldx, int oldy, int computeVerticalScrollRange);
    }
}
