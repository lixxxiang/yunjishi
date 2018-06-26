package com.yunjishi.lixiang.yunjishi.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunjishi.lixiang.yunjishi.R;

import java.util.List;

public class SelectTimeAdapter extends BaseAdapter {

    private List<String> titleList;
    private List<String> detailList;
    private LayoutInflater inflater;
    private Context context;


    public SelectTimeAdapter() {
    }

    public SelectTimeAdapter(List<String> titleList, List<String> detailList, Context context) {
        this.titleList = titleList;
        this.detailList = detailList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.item_time_params, null);
        TextView item_tv = view.findViewById(R.id.mTimeTitleTextView);
        TextView item_detail_tv = view.findViewById(R.id.mTimeDetailTextView);
        item_tv.setText(titleList.get(position));
        if (detailList.size() != 0){
            String s = detailList.get(position);
            System.out.println(s);
            item_detail_tv.setText(s);
        }
        return view;
    }


}
