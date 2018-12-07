package com.shuben.zhixing.www.patrol.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.PatrolTaskActivity;
import com.shuben.zhixing.www.patrol.bean.ClassInfo;

import java.util.List;

import name.gudong.drawable.OneDrawable;

/**
 * Created by Geyan on 2018/5/3.
 */

public class ClassGridAdpter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<ClassInfo> mlist;
    private ClassInfo classInfo;
    private ViewHolder holder;
    private int [] img={R.mipmap.patrol01,R.mipmap.patrol02,R.mipmap.patrol03};
    public ClassGridAdpter(Context mContext, List<ClassInfo> list) {
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
            convertView = inflater.inflate(R.layout.patrol_class_grid_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.bnt=(Button) convertView.findViewById(R.id.p_bnt);
            holder.tx=(TextView) convertView.findViewById(R.id.p_text);
            holder.lay=(LinearLayout)convertView.findViewById(R.id.lay);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        classInfo=mlist.get(position);
        holder.tx.setText(classInfo.getItem03());

        setDrawable(holder.bnt, OneDrawable.createBgDrawable(mContext, img[position%3]));
        holder.lay.setFocusable(false);
        holder.bnt.setFocusable(false);
        holder.lay.setOnClickListener(new ClassGridAdpter.lvButtonListener(position));
        holder.bnt.setOnClickListener(new ClassGridAdpter.lvButtonListener(position));
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
        public TextView tx;
        public LinearLayout lay;
    }
    class lvButtonListener implements View.OnClickListener {
        private int position;
        lvButtonListener(int pos) {
            this.position = pos;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v.getId() == holder.lay.getId()||v.getId() == holder.bnt.getId()) {
                Intent intent=new Intent();
                intent.putExtra("ClassId",mlist.get(position).getItem01());
                intent.putExtra("ClassName",mlist.get(position).getItem03());
                intent.setClass(mContext, PatrolTaskActivity.class);
                mContext.startActivity(intent);
            }
        }
    }
    private void setDrawable(final Button button, Drawable icon) {
        button.setBackgroundDrawable(icon);
        button.setClickable(true);
    }
}



