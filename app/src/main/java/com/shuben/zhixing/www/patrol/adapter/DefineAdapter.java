package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.GroupInfo;

import java.util.List;

/**
 * Created by Geyan on 2018/5/30.
 */

public class DefineAdapter extends BaseAdapter {
    private Context context;
    private List<GroupInfo> data;
    private LayoutInflater inflater;

    public DefineAdapter(Context context, List<GroupInfo> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHoloder holoder = null;
        if (convertView==null){
            holoder = new ViewHoloder();
            convertView = inflater.inflate(R.layout.patrol_define_item,null);
            holoder.item01 = (TextView) convertView.findViewById(R.id.tx_item01);
            holoder.item02 = (TextView) convertView.findViewById(R.id.tx_item02);
            holoder.item03 = (TextView) convertView.findViewById(R.id.tx_item03);
            convertView.setTag(holoder);
        }
        else {
            holoder = (ViewHoloder) convertView.getTag();
        }
        GroupInfo item = data.get(position);
        holoder.item01.setText(item.getItem01());
        holoder.item02.setText(item.getItem02()+"分钟");
        holoder.item03.setText(item.getItem03());
        return convertView;
    }

    class ViewHoloder{
        TextView item01,item02,item03;
    }
}


