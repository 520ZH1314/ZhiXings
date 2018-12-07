package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.SortInfo;
import com.shuben.zhixing.www.util.NumberChangeToChinese;

import java.util.List;

/**
 * Created by Geyan on 2018/6/2.
 */

public class SortAdapter extends BaseAdapter {
    private Context mContext;
    private List<SortInfo> datas;
    private LayoutInflater inflater;
    public SortAdapter(Activity activity, List<SortInfo> news) {
        datas = news;
        mContext =activity;
    }

    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return datas.size();
    }

    @Override
    //获取数据集中与指定索引对应的数据项
    public Object getItem(int position) {
        return null;
    }

    @Override
    //获取在列表中与指定索引对应的行id
    public long getItemId(int position) {
        return position;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_sort_item, null);
            holder.item01 = (TextView)convertView.findViewById(R.id.tx_item01);
            holder.item02 = (TextView)convertView.findViewById(R.id.tx_item02);
            holder.item03 = (TextView)convertView.findViewById(R.id.tx_item03);
            holder.item04 = (TextView)convertView.findViewById(R.id.tx_item04);
            holder.item05 = (TextView)convertView.findViewById(R.id.tx_item05);
            holder.item06 = (TextView)convertView.findViewById(R.id.tx_item06);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        NumberChangeToChinese numToChinese = new NumberChangeToChinese();
        String num=numToChinese.numberToChinese(position+1);
        holder.item01.setText("第"+num+"名");
        holder.item02.setText(datas.get(position).getItem01());
        holder.item03.setText(datas.get(position).getItem02());
        holder.item04.setText(datas.get(position).getItem03());
        holder.item05.setText(datas.get(position).getItem04());
        holder.item06.setText(datas.get(position).getItem05());

        return convertView;
    }

    class ViewHolder
    {
        public TextView item01, item02, item03, item04, item05, item06;
    }
}

