package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.FragmentTwoDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxb on 2016/3/24.
 * 新通知适配数据
 */
public  class MyTaskFragmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<FragmentTwoDate> datas=new ArrayList<FragmentTwoDate>();
    private LayoutInflater inflater;
    private String str;
    public MyTaskFragmentAdapter(Activity activity, List<FragmentTwoDate> news,String strs) {
        datas = news;
        mContext =activity;
        str = strs;
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
            convertView = inflater.inflate(R.layout.fragment_mytask_item, null);
            holder.fm_item_title = (TextView)convertView.findViewById(R.id.fm_item_title);
            holder.fm_item_jindu = (TextView)convertView.findViewById(R.id.fm_item_jindu);
            holder.fm_item_bianhao = (TextView)convertView.findViewById(R.id.fm_item_bianhao);
            holder.fm_item_zeren = (TextView)convertView.findViewById(R.id.fm_item_zeren);
            holder.fm_item_laiyuan = (TextView)convertView.findViewById(R.id.fm_item_laiyuan);
            holder.name_01 = (TextView)convertView.findViewById(R.id.name_01);
            holder.name_02 = (TextView)convertView.findViewById(R.id.name_02);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        if (str.equals("内部催单")){
            holder.name_02.setText("客户名称:");
            holder.fm_item_zeren.setText(datas.get(position).getToDoUserName());//责任人
            holder.fm_item_laiyuan.setText(datas.get(position).getCustomerName());//供应商
        }else if  (str.equals("外部催单1")){
            holder.name_01.setText("责任人:");
            holder.name_02.setText("供应商:");
            holder.fm_item_zeren.setText(datas.get(position).getToDoUserName());//责任人
            holder.fm_item_laiyuan.setText(datas.get(position).getCustomerName());//供应商
        }else {
            holder.name_02.setText("客户名称:");
            holder.name_01.setText("客户采购:");
            holder.fm_item_zeren.setText(datas.get(position).getToDoUserName());//责任人
            holder.fm_item_laiyuan.setText(datas.get(position).getCustomerName());//供应商
        }

        //holder.fm_item_title.setText(datas.get(position).getMission());//任务
        holder.fm_item_jindu.setText(datas.get(position).getTaskStatusName());//任务状态
        holder.fm_item_bianhao.setText(datas.get(position).getBillNo());//催单编号

        return convertView;
    }

   class ViewHolder
   {
    public TextView fm_item_title,fm_item_jindu,fm_item_bianhao,fm_item_zeren,fm_item_laiyuan,name_01,name_02;
  }
}
