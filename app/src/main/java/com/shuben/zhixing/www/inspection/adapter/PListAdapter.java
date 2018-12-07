package com.shuben.zhixing.www.inspection.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.InspectionQInfo;

import java.util.List;

/**
 * Created by Geyan on 2018/8/6.
 */

public class PListAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater inflater;
    private List<InspectionQInfo> mlist;
    private InspectionQInfo info;
    private PListAdapter.ViewHolder holder;
    private Intent intent;
    private String IsRelationWorksheet;
    private EditText ed_workplace;
    public PListAdapter(Activity mContext, List<InspectionQInfo> list,String IsRelationWorksheet) {
        this.mContext = mContext;
        this.mlist=list;
        this.IsRelationWorksheet=IsRelationWorksheet;
    }

    @Override
    //获取一个在数据集中指定索引的视图来显示数据
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            holder = new PListAdapter.ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.inspection_plist_item, null);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            holder.tx_problemNo=(TextView) convertView.findViewById(R.id.tx_problemNo);
            holder.tx_date=(TextView) convertView.findViewById(R.id.tx_date);
            holder.tx_content=(TextView) convertView.findViewById(R.id.tx_content);
            holder.tx_name=(TextView) convertView.findViewById(R.id.tx_name03);
            holder.tx_user=(TextView) convertView.findViewById(R.id.tx_user);
            holder.tx_problem=(TextView) convertView.findViewById(R.id.tx_problem);
            holder.tx_product=(TextView) convertView.findViewById(R.id.tx_product);
            holder.img_status=(ImageView) convertView.findViewById(R.id.img_status);
            convertView.setTag(holder);
        }else
        {
            holder = (PListAdapter.ViewHolder)convertView.getTag();
        }
        info=mlist.get(position);
        holder.tx_problemNo.setText("问题号:"+info.getProblemNo());
        holder.tx_date.setText(info.getDueDate());
        holder.tx_user.setText(info.getLiableUserName());
        holder.tx_product.setText("发现人:"+info.getPatrolUserName());
        holder.tx_content.setText("产品:"+info.getProductName());

        holder.tx_problem.setText(info.getProblemDesc());
        if(info.getStatus().equals("未开始")){
            holder.img_status.setBackgroundResource(R.mipmap.qpa_hand);
        }else {
            holder.img_status.setBackgroundResource(R.mipmap.qpa_handed);
        }



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
        TextView tx_problemNo;
        TextView tx_date;
        TextView tx_content;
        TextView tx_name;
        TextView tx_user,tx_problem,tx_product;
        ImageView img_status;

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


