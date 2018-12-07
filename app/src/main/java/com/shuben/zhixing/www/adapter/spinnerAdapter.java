package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

/**
 * Created by Administrator on 2017/8/31.
 */

public class spinnerAdapter extends BaseAdapter {

    private Context context;
    private String[] datas;
    private LayoutInflater  inflater;

    public spinnerAdapter(Activity activity, String[] data) {
        context = activity;
        datas = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1 = inflater.inflate(R.layout.fragment03_sp_item,null);
        TextView textView = (TextView)view1.findViewById(R.id.spiner_item);
        textView.setText(datas[position]);
        return view1;
    }
}
