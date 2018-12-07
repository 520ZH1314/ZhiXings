package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.MyTypesDate;

import java.util.List;

/**
 * Created by gxb on 2016/3/24.
 * 新通知适配数据
 */
public  class TypeAdapterAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyTypesDate> datas;
    private LayoutInflater inflater;
    public TypeAdapterAdapter(Activity activity, List<MyTypesDate> news) {
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
            convertView = inflater.inflate(R.layout.activity_searchcn_item, null);
            holder.seach_name_item_tv = (TextView)convertView.findViewById(R.id.seach_name_item_tv);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Log.i("Text","4444444444444444444"+datas.get(position).getName());
        holder.seach_name_item_tv.setText(datas.get(position).getName());//姓名
        return convertView;
    }

   class ViewHolder
   {
    public TextView seach_name_item_tv;
  }
}
