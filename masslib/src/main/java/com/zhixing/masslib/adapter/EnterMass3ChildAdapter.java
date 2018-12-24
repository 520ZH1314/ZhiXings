package com.zhixing.masslib.adapter;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.WxItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * 维修子项
 */

public class EnterMass3ChildAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<WxItem> rbs;
    private Handler handler;
    public EnterMass3ChildAdapter(Activity context, ArrayList<WxItem> rbs, Handler handler){
        this.context = context;
        this.rbs = rbs;
        this.handler = handler;

        inflater = LayoutInflater.from(context);


    }
    public void updata(ArrayList<WxItem> rbs){
        this.rbs = rbs;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return rbs.size();
    }

    @Override
    public Object getItem(int position) {
        return rbs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
              TextView  item2,item3,item4,item5,item6,item7;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.enter_mass3_child, null);
            viewHolder.item2 = convertView.findViewById(R.id.item2);
            viewHolder.item3 = convertView.findViewById(R.id.item3);
            viewHolder.item4 = convertView.findViewById(R.id.item4);
            viewHolder.item5 = convertView.findViewById(R.id.item5);
            viewHolder.item6 = convertView.findViewById(R.id.item6);
            viewHolder.item7 = convertView.findViewById(R.id.item7);
            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        WxItem item = rbs.get(position);
        viewHolder.item2.setText("编号:"+(position+1));
        viewHolder.item3.setText("产品名称:"+item.getName());
        viewHolder.item4.setText("不良数量:"+item.getNum());
        viewHolder.item5.setText("不良现象:"+item.getOkN());
        viewHolder.item6.setText("不良原因:"+item.getNoN());
        viewHolder.item7.setText("维修结果"+item.getResult());
        return  convertView;
    }
}
