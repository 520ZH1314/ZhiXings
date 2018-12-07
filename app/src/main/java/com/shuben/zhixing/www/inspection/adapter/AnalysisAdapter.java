package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.AnalyInfo;

import java.util.List;

/**
 * Created by Geyan on 2018/8/6.
 */

public class AnalysisAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<AnalyInfo> mlist;
    private AnalyInfo info;
    private ViewHolder holder;
    private Intent intent;
    private EditText ed_workplace;
    public AnalysisAdapter(Activity mContext, List<AnalyInfo> list) {
        this.mContext = mContext;
        this.mlist=list;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_analysis_item01, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx_class=(TextView) convertView.findViewById(R.id.tx_calss);
            holder.tx_item01=(TextView) convertView.findViewById(R.id.tx_item01);
            holder.tx_item02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.tx_item03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.tx_item04=(TextView) convertView.findViewById(R.id.tx_item04);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.tx_class.setText(info.getTx_calss());
        holder.tx_item01.setText(info.getTx_item01());
        holder.tx_item02.setText(info.getTx_item02());
        holder.tx_item03.setText(info.getTx_item03());
        holder.tx_item04.setText(info.getTx_item04());
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
        TextView tx_class;
        TextView tx_item01;
        TextView tx_item02;
        TextView tx_item03;
        TextView tx_item04;

    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {



        }
    }




}



