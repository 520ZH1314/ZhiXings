package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;

import java.util.List;

/**
 * Created by Geyan on 2018/5/14.
 */

public class ReportAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<SceneInfo> mlist;
    private SceneInfo info;
    public ReportAdapter(Context mContext, List<SceneInfo> list) {
        this.mContext = mContext;
        this.mlist=list;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_report_listview_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx01=(TextView) convertView.findViewById(R.id.tx_item01);
            holder.tx02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.tx03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.tx04=(TextView) convertView.findViewById(R.id.tx_item04);
            holder.tx05=(TextView) convertView.findViewById(R.id.tx_item05);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.tx01.setText(position+1+"");
        holder.tx02.setText(info.getItem01());
        holder.tx03.setText(info.getItem02());
        holder.tx04.setText(info.getItem03());
        holder.tx05.setText(info.getItem04());


        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return mlist.size();
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



    class ViewHolder
    {
        public TextView tx01,tx02,tx03,tx04,tx05;

    }
}




