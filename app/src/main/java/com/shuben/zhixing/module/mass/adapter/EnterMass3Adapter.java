package com.shuben.zhixing.module.mass.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuben.zhixing.module.mass.bean.MassItemBean;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.widget.InListView;

import java.util.ArrayList;
import java.util.Map;

/**
 */

public class EnterMass3Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private ArrayList<MassItemBean> massItemBeans ;
    private Handler handler;
    public EnterMass3Adapter(Activity context,  ArrayList<MassItemBean> massItemBeans , Handler handler){
        this.context = context;
        this.massItemBeans = massItemBeans;
        this.handler = handler;

        inflater = LayoutInflater.from(context);


    }
    public void updata(ArrayList<MassItemBean> rbs){
        this.massItemBeans = rbs;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return massItemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return massItemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView item0,item1,item2,item3,item4,item5,item6;
        InListView listView;
        RelativeLayout add_item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null
                || convertView.getTag(R.mipmap.ic_launcher + position) == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.enter_mass3_item, null);
            viewHolder.add_item = convertView.findViewById(R.id.add_item);
            viewHolder.item1 = convertView.findViewById(R.id.item1);
            viewHolder.item2 = convertView.findViewById(R.id.item2);
            viewHolder. item3 = convertView.findViewById(R.id.item3);
            viewHolder. item4 = convertView.findViewById(R.id.item4);
            viewHolder. item5 = convertView.findViewById(R.id.item5);
            viewHolder.item6 = convertView.findViewById(R.id.item6);
            viewHolder.listView = convertView.findViewById(R.id.list_v);
            convertView.setTag(R.mipmap.ic_launcher + position);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.mipmap.ic_launcher
                    + position);
        }
        MassItemBean bean = massItemBeans.get(position);

        viewHolder.item1.setText(bean.getData()+" "+bean.getTime());
        viewHolder.item2.setText("工单号:"+bean.getNo());
        viewHolder.item3.setText("车间/线体:"+bean.getWork()+"/"+bean.getLine());
        viewHolder.item4.setText("产品名称:"+bean.getProduct());
        viewHolder.item5.setText("型号:"+(bean.getProductCode()));
        viewHolder.item6.setVisibility(View.GONE);
        EnterMass3ChildAdapter adapter = new EnterMass3ChildAdapter(context,bean.getWxItems(),handler);
        viewHolder.listView.setAdapter(adapter);
        viewHolder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 31;
                msg.arg1 = position;
                handler.sendMessage(msg);
//               handler.sendEmptyMessage(31);
            }
        });
        return  convertView;
    }
}
