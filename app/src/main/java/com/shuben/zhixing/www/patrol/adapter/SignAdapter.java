package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.SignInfo;

import java.util.List;

/**
 * Created by Geyan on 2018/5/18.
 */

public class SignAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<SignInfo> mlist;
    private ViewHolder holder;
    public SignAdapter(Context mContext, List<SignInfo> list) {
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
            convertView = inflater.inflate(R.layout.patrol_sign_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx_item01=(TextView) convertView.findViewById(R.id.tx_user);
            holder.tx_item02=(TextView) convertView.findViewById(R.id.tx_org);
            holder.bnt_item03=(TextView) convertView.findViewById(R.id.bnt_item03);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tx_item01.setText(mlist.get(position).getItem05());
        holder.tx_item02.setText(mlist.get(position).getItem04());
        if(mlist.get(position).getItem06().equals("0")){
            holder.bnt_item03.setBackgroundColor(Color.GRAY);
            holder.bnt_item03.setText("未签到");
        }else if(mlist.get(position).getItem06().equals("1")){
            holder.bnt_item03.setText("已签到");
            holder.bnt_item03.setBackgroundColor(Color.GREEN);
        }else if(mlist.get(position).getItem06().equals("2")){
            holder.bnt_item03.setText("请假");
            holder.bnt_item03.setBackgroundColor(Color.YELLOW);
        }else if(mlist.get(position).getItem06().equals("3")){
            holder.bnt_item03.setText("结束签到");
            holder.bnt_item03.setBackgroundColor(Color.BLUE);
        }
        holder.bnt_item03.setOnClickListener(new lvButtonListener(position));
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
        public TextView tx_item01,tx_item02;
        public TextView bnt_item03;

    }

    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if ((v.getId()) == (holder.bnt_item03.getId())) {
            }
        }
    }
}



