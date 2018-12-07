package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.GetOrganizeToDoList_Data;

import java.util.List;



/**
 * Created by Geyan on 2017/9/12.
 */

public class OrganizeAdapter extends BaseAdapter {
    private Context mContext;
    private List<GetOrganizeToDoList_Data> datas;
    private LayoutInflater inflater;
    public OrganizeAdapter(Activity activity, List<GetOrganizeToDoList_Data> news) {
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
        final OrganizeAdapter.ViewHolder holder;
        if(convertView == null) {
            holder = new OrganizeAdapter.ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment02_item, null);
            holder.fm_item_title = (TextView)convertView.findViewById(R.id.fm_item_title);
            holder.fm_item_jindu = (TextView)convertView.findViewById(R.id.fm_item_jindu);
            holder.fm_item_bianhao = (TextView)convertView.findViewById(R.id.fm_item_bianhao);
            holder.fm_item_zeren = (TextView)convertView.findViewById(R.id.fm_item_zeren);
            holder.fm_item_laiyuan = (TextView)convertView.findViewById(R.id.fm_item_laiyuan);
            holder.fragment02_time = (TextView)convertView.findViewById(R.id.fragment02_time);
            holder.fm_img = (ImageView) convertView.findViewById(R.id.fm_img);


            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (OrganizeAdapter.ViewHolder)convertView.getTag();
        }

        holder.fm_item_title.setText("任务内容："+datas.get(position).getTitle());//任务
        holder.fm_item_jindu.setText(datas.get(position).getTaskStatusName());//任务状态
        holder.fm_item_bianhao.setText(datas.get(position).getTaskNo());//编号
        holder.fm_item_zeren.setText(datas.get(position).getToDoUser());//责任人
        holder.fm_item_laiyuan.setText(datas.get(position).getSource());//任务来源

        if (datas.get(position).getCreateDate().length()>10){
            holder.fragment02_time.setText(datas.get(position).getCreateDate().substring(0,10));//创建时间

        }else {
            holder.fragment02_time.setText(datas.get(position).getCreateDate());//创建时间

        }

        if ("紧急催单".equals(datas.get(position).getSource())){
            holder.fm_img.setBackgroundResource(R.mipmap.sy_cuidan);

        }else if("任务交办".equals(datas.get(position).getSource())){
            holder.fm_img.setBackgroundResource(R.mipmap.sy_task);
        }else if("高效会议".equals(datas.get(position).getSource())){
            holder.fm_img.setBackgroundResource(R.mipmap.sy_gxmeeting);
        }else if("8D管理".equals(datas.get(position).getSource())){
            holder.fm_img.setBackgroundResource(R.mipmap.sy_bad);
        }else if("MRB管理".equals(datas.get(position).getSource())){
            holder.fm_img.setBackgroundResource(R.mipmap.rolling_mrb);
        }else {
            holder.fm_img.setBackgroundResource(R.mipmap.shubenlogo);
        }
        return convertView;
    }

    class ViewHolder
    {
        public TextView fm_item_title,fm_item_jindu,fm_item_bianhao,fm_item_zeren,fm_item_laiyuan,fragment02_time;
        public ImageView fm_img;
    }
}

