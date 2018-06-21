package com.android.lixiang.base.utils.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(Context context,
                              OnDateSetListener callBack, int year, int monthOfYear,
                              int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub

    }

    public MyDatePickerDialog(@NonNull Context context, int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}
