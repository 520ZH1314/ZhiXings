package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.GetVendorAnalysis_Data;

import java.util.List;

/**
 * Created by gxb on 2016/3/24.
 * 新通知适配数据
 */
public  class GetVendorAnalysisAdapter extends BaseAdapter {
    private Context mContext;
    private List<GetVendorAnalysis_Data> datas;
    private LayoutInflater inflater;
    public GetVendorAnalysisAdapter(Activity activity, List<GetVendorAnalysis_Data> news) {
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
            convertView = inflater.inflate(R.layout.activity_dataanalysis_two_item, null);
            holder.analysis_item_ranking = (TextView)convertView.findViewById(R.id.analysis_item_ranking);
            holder.analysis_item_name = (TextView)convertView.findViewById(R.id.analysis_item_name);
            holder.analysis_item_section = (TextView)convertView.findViewById(R.id.analysis_item_section);
            holder.analysis_item_number = (TextView)convertView.findViewById(R.id.analysis_item_number);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        //请仔细查看赋值是否正确，一下写法是错误的，不清楚具体哪个是哪个

        holder.analysis_item_ranking.setText("");//排名
        holder.analysis_item_name.setText(datas.get(position).getVendorName());//姓名
        holder.analysis_item_section.setText(datas.get(position).getDemao());//部门
        holder.analysis_item_number.setText(datas.get(position).getAnalysis());//评分
        return convertView;
    }

   class ViewHolder
   {
    public TextView analysis_item_ranking,analysis_item_name,analysis_item_section,analysis_item_number;
  }
}
