package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.yunjishi.lixiang.base.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lixiang on 2018/3/18.
 */


public class TextSwitcherView2 extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private ArrayList<String> reArrayList = new ArrayList<String>();
    private int resIndex = 0;
    private final int UPDATE_TEXTSWITCHER = 1;
    private int timerStartAgainCount = 0;
    private Context mContext;

    public TextSwitcherView2(Context context) {

        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public TextSwitcherView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
        // TODO Auto-generated constructor stub
    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(getContext(), R.anim.vertical_in);
        this.setOutAnimation(getContext(), R.anim.vertical_out);
        Timer timer = new Timer();
        timer.schedule(timerTask, 1, 3000);
    }

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {   //不能在这里创建任何UI的更新，toast也不行
            // TODO Auto-generated method stub
            Message msg = new Message();
            msg.what = UPDATE_TEXTSWITCHER;
            handler.sendMessage(msg);
        }
    };
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXTSWITCHER:
                    updateTextSwitcher();

                    break;
                default:
                    break;
            }

        }

        ;
    };

    /**
     * 需要传递的资源
     *
     * @param reArrayList
     */
    public void getResource(ArrayList<String> reArrayList) {
        this.reArrayList = reArrayList;
    }

    public void updateTextSwitcher() {
        if (this.reArrayList != null && this.reArrayList.size() > 0) {
            this.setText(this.reArrayList.get(resIndex++));
            if (resIndex > this.reArrayList.size() - 1) {
                resIndex = 0;
            }
        }

    }

    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView tView = new TextView(getContext());
        tView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F);
        tView.setTextColor(Color.parseColor("#5C5C5C"));
        tView.setSingleLine();
        tView.setEllipsize(TextUtils.TruncateAt.END);
        return tView;
    }
}
