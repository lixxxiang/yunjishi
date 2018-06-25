package com.yunjishi.lixiang.yunjishi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunjishi.lixiang.yunjishi.R;

import java.util.List;

public class SelectTypeParamsAdapter extends BaseAdapter {

    private List<String> titleList;
    private LayoutInflater inflater;
    private Context context;
    private int selectedItem = -1;


    public SelectTypeParamsAdapter() {
    }

    public SelectTypeParamsAdapter(List<String> titleList, Context context) {
        this.titleList = titleList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
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
        View view = inflater.inflate(R.layout.item_select_type_params, null);
        TextView item_tv = view.findViewById(R.id.mTitleTextView);
        ImageView item_iv = view.findViewById(R.id.mTypeImageView);

        item_tv.setText(titleList.get(position));
        if(position == selectedItem) {
            item_iv.setVisibility(View.VISIBLE);
        }
        return view;
    }


}
