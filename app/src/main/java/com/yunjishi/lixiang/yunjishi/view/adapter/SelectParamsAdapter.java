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

public class SelectParamsAdapter extends BaseAdapter {

    private List<String> titleList;
    private List<String> subTitleList;
    private List<String> detailList;
    private LayoutInflater inflater;
    private Context context;


    public SelectParamsAdapter() {
    }

    public SelectParamsAdapter( List<String> titleList, List<String> subTitleList, List<String> detailList,Context context) {
        this.titleList = titleList;
        this.subTitleList = subTitleList;
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
        View view = inflater.inflate(R.layout.item_select_params, null);
        TextView item_tv = view.findViewById(R.id.mTitleTextView);
        TextView item_subTv = view.findViewById(R.id.mSubTitleTextView);
        TextView item_detail_tv = view.findViewById(R.id.mDetailTextView);
        item_tv.setText(titleList.get(position));
        item_detail_tv.setText(detailList.get(position));
        item_subTv.setText(subTitleList.get(position));
        return view;
    }


}
