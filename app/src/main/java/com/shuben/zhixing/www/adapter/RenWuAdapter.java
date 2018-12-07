package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.data.ReplyInfo;
import com.shuben.zhixing.www.reminder.ReplyActivity;

import java.util.List;

/**
 * Created by gxb on 2016/3/24.
 * 新通知适配数据
 */
public  class RenWuAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReplyInfo> datas;
    private LayoutInflater inflater;
    private String type;
    private boolean  isShow;
    private int index=0;
    private String user;
    private String toUser;
    public RenWuAdapter(Activity activity, List<ReplyInfo> news,String type,boolean  isShow,String user,String toUser) {
        datas = news;
        mContext =activity;
        this.type=type;
        this.isShow=isShow;
        this.user=user;
        this.toUser=toUser;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.activity_renwu_item, null);
            holder.renwu_item_name = (TextView)convertView.findViewById(R.id.renwu_item_name);//发起或责任人
            holder.renwu_item_faqitime = (TextView)convertView.findViewById(R.id.renwu_item_faqitime);//回复时间
            holder.renwu_item_number = (TextView)convertView.findViewById(R.id.renwu_item_number);//交货数量
            holder.renwu_item_time = (TextView)convertView.findViewById(R.id.renwu_item_time);//交货时间
            holder.renwu_cargo_type = (TextView)convertView.findViewById(R.id.renwu_cargo_type);//类型
            holder.renwu_cargo_context = (TextView)convertView.findViewById(R.id.renwu_cargo_context);//回复内容
            holder.renwu_item_rl1 = (RelativeLayout)convertView.findViewById(R.id.renwu_item_rl1);//回复按钮
            holder.renwu_item_rl2 = (RelativeLayout)convertView.findViewById(R.id.renwu_item_rl2);//更多
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        if(position==0){
            holder.renwu_item_name.setText("1.需求("+ user+")");
            //Toast.makeText(mContext,""+isShow,Toast.LENGTH_SHORT).show();
            Log.e("是否是责任人",""+isShow);
            if(position<datas.size()){
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
            }else{
                if (isShow){
                    holder.renwu_item_rl1.setVisibility(View.VISIBLE);
                }else{
                    holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
                }
            }

        }else if(position==1){
            holder.renwu_item_name.setText("2.回复1("+toUser+")");
            if(position<datas.size()){
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
            }else{
                if (isShow){
                    holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
                }else if(position==datas.size()){
                    holder.renwu_item_rl1.setVisibility(View.VISIBLE);
                }
            }

        }else if(position==2){
            holder.renwu_item_name.setText("3.确认("+user+")");
            if(position<datas.size()){
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
            }else{
                if (isShow){
                    holder.renwu_item_rl1.setVisibility(View.VISIBLE);
                }else if(position==datas.size()){
                    holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
                }
            }
        }else if(position==3){
            holder.renwu_item_name.setText("4.回复2("+toUser+")");
            if(position<datas.size()){
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
            }else{
                if (isShow){
                    holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
                }else {
                    holder.renwu_item_rl1.setVisibility(View.VISIBLE);
                }
            }

        }else if(position==4){
            holder.renwu_item_name.setText("5.实际("+toUser+")");
            if(position<datas.size()){
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
            }else{
                if (isShow){
                    holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
                }else {
                    holder.renwu_item_rl1.setVisibility(View.VISIBLE);
                }
            }

        }else if(position==5){
            holder.renwu_item_name.setText("6.确认("+user+")");
                holder.renwu_item_rl1.setVisibility(View.INVISIBLE);
        }

        holder.renwu_item_time.setText(datas.get(position).getHandleDate());
        holder.renwu_item_number.setText(datas.get(position).getDeliverQty());
        holder.renwu_cargo_type.setText(datas.get(position).getType());
        holder.renwu_cargo_context.setText(datas.get(position).getMemo());

       // holder.renwu_item_name.setText(datas.get(position).getUserName());
        holder.renwu_item_rl1.setTag(position);
        holder.renwu_item_rl1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = (Integer) v.getTag();
                }
                return false;
            }
        });


        holder.renwu_item_rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == position){
                    Intent intent = new Intent(mContext,ReplyActivity.class);
                    if(type.equals("内部催单")){
                        intent.putExtra("InnerUrgeBillNo",datas.get(position).getBillNo());
                    }else if(type.equals("采购催单1")||type.equals("采购催单2")||type.equals("采购催单3")){
                        intent.putExtra("OuterUrgeBillNo",datas.get(position).getBillNo());
                    }
                    intent.putExtra("DeliverQty",datas.get(position).getDeliverQty());//交货数量
                    intent.putExtra("DeliverDate",datas.get(position).getHandleDate());//交货时间
                    Log.e("Step888888888888888888",datas.get(position).getMyStep()+"");
                    intent.putExtra("Step",datas.get(position).getMyStep());
                    intent.putExtra("Type",type);
                    mContext.startActivity(intent);
                    mContext.startActivity(intent);
                }

            }
        });
        return convertView;
    }

   class ViewHolder
   {
    public TextView renwu_item_name,renwu_item_faqitime,renwu_item_number,renwu_item_time,renwu_cargo_type,renwu_cargo_context;
       private RelativeLayout renwu_item_rl1,renwu_item_rl2;
  }
}
