package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.shuben.zhixing.www.R;

import java.util.List;

/**
 * Created by Geyan on 2018/3/27.
 */

public class GridAdapter  extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<String> mlist;
    private ViewHolder holder;
    private EditText ed_qus;
    private   RatingBar ratingBar;
    private List<Integer> r_list;
    public GridAdapter(Context mContext, List<String> list,List<Integer> r_list,EditText ed_qus, RatingBar ratingBar) {
        this.mContext = mContext;
        this.mlist=list;
        this.ed_qus=ed_qus;
        this.ratingBar=ratingBar;
        this.r_list=r_list;
    }



    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.grid_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.bnt=(Button) convertView.findViewById(R.id.bnt_qus);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.bnt.setText(mlist.get(position));
        holder.bnt.setFocusable(false);
        holder.bnt.setOnClickListener(new lvButtonListener(position));
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
        public Button bnt;

    }

    class lvButtonListener implements View.OnClickListener {
        private int position;

        lvButtonListener(int pos) {
            this.position = pos;
        }


        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if ((v.getId()) == (holder.bnt.getId())) {
                ed_qus.setText(mlist.get(position));
                ratingBar.setRating(r_list.get(position));
            }
        }
    }
}


