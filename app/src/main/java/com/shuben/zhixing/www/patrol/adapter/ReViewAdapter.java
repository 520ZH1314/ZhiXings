package com.shuben.zhixing.www.patrol.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.bean.ReviewInfo;
import com.shuben.zhixing.www.util.ScrollListview;

import java.util.List;

/**
 * Created by Geyan on 2018/6/15.
 */

public class ReViewAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private List<ReviewInfo> data;
    public ReViewAdapter(Activity mContext, List<ReviewInfo> data) {
        this.mContext = mContext;
        this.data=data;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.patrol_review_item01, null);
            holder.item01=(TextView) convertView.findViewById(R.id.tx_item01);//
            holder.item02=(TextView) convertView.findViewById(R.id.tx_item02);
            holder.item03=(TextView) convertView.findViewById(R.id.tx_item03);
            holder.name03=(TextView) convertView.findViewById(R.id.tx_name03);
            holder.item04=(ListView) convertView.findViewById(R.id.review_listview);

            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        ReviewInfo info=data.get(position);

        holder.item01.setText(info.getItem01());
        holder.item02.setText(info.getItem02());
        if(info.getItem03().equals("")){
            holder.item03.setVisibility(View.GONE);
            holder.name03.setVisibility(View.GONE);
        }else{
            holder.item03.setVisibility(View.VISIBLE);
            holder.name03.setVisibility(View.VISIBLE);
            holder.item03.setText(info.getItem03());
        }

        SubAdapter adapter=new SubAdapter(mContext,info.getItem04());
        holder.item04.setAdapter(adapter);
        new ScrollListview( holder.item04);

        return convertView;
    }
    @Override
    //在此适配器中所代表的数据集中的条目数
    public int getCount() {
        return data.size();
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
        public TextView item01, item02, item03,name03;
        private ListView item04;
    }
    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

        }
    }




}

