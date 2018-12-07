package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.NotificationData;
import com.shuben.zhixing.www.util.DataChangeUtil;

import java.util.List;

/**
 * Created by gxb on 2016/3/24.
 * 新通知适配数据
 */
public  class NewMissionAdapter extends BaseAdapter {
    private Context mContext;
    private List<NotificationData> datas;
    private LayoutInflater inflater;
    public NewMissionAdapter(Activity activity, List<NotificationData> news) {
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
            convertView = inflater.inflate(R.layout.activity_notification_item, null);
            holder.renwu_context = (TextView)convertView.findViewById(R.id.renwu_context);
            holder.bianhao_tv = (TextView)convertView.findViewById(R.id.bianhao_tv);
            holder.source_tv = (TextView)convertView.findViewById(R.id.source_tv);
            holder.stop_time_tv = (TextView)convertView.findViewById(R.id.stop_time_tv);
            holder.fabu_name = (TextView)convertView.findViewById(R.id.fabu_name);
            holder.crate_time_tv = (TextView)convertView.findViewById(R.id.crate_time_tv);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.renwu_context.setText(datas.get(position).getTitle());//任务
        holder.bianhao_tv.setText(datas.get(position).getTaskNo());//编号
        holder.stop_time_tv.setText(datas.get(position).getCreateDept());//截止时间
        holder.fabu_name.setText(datas.get(position).getCreateUser());//发布人
        holder.source_tv.setText(DataChangeUtil.getInstance().getSource(datas.get(position).getSource()));//任务来源
        if (datas.get(position).getCreateDate().length()>10){
            holder.crate_time_tv.setText(datas.get(position).getCreateDate().substring(0,10));//创建时间
        }else {
            holder.crate_time_tv.setText(datas.get(position).getCreateDate());//创建时间
        }

        return convertView;
    }

   class ViewHolder
   {
    public TextView renwu_context,bianhao_tv,source_tv,stop_time_tv,fabu_name,crate_time_tv;
  }
}
