package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunjishi.lixiang.base.R;


/**
 * Created by lixiang on 2017/11/29.
 */

public class BottomBarView extends RelativeLayout {

    private int msgCount;
    private TextView bar_num;

    public BottomBarView(Context context) {
        this(context, null);
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.btn_with_red, this, true);
        bar_num = rl.findViewById(R.id.bar_num);
    }

    public void setMessageCount(int count) {
        msgCount = count;
        if (count == 0) {
            bar_num.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 1000) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("999+");
            }
        }
        invalidate();
    }

    public void addMsg() {
        setMessageCount(msgCount + 1);
    }
}
