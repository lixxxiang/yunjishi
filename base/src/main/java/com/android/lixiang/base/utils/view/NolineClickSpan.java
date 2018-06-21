package com.android.lixiang.base.utils.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

class NolineClickSpan extends ClickableSpan {
    String text;

    public NolineClickSpan(String text) {
        super();
        this.text = text;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false); //去掉下划线
    }

    @Override
    public void onClick(View widget) {

    }
}
