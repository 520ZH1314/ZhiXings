package com.zhixing.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.TimeUtil;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.selector.DevSelector;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.beans.HourBean;
import com.zhixing.beans.WorkBean;
import com.zhixing.rpclib.R;

import java.util.ArrayList;

public class WorkItemAdapter extends RecyclerView.Adapter<WorkItemAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private ArrayList<WorkBean> foodsBeans;
    private Handler handler;

    int width = 0;
    public WorkItemAdapter(Activity context, ArrayList<WorkBean> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        width = DensityUtil.getWindowWidth(context);
        inflater = LayoutInflater.from(context);
    }

    public void updata(ArrayList<WorkBean> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.rpc_work_item,parent,false);

            return new WorkItemAdapter.ViewHolder(v);
    }
    private int mposition = -1;
    public void selectPosition(int mposition){
        this.mposition = mposition;
        notifyDataSetChanged();
    }
    public int nowIndex(){
        return  mposition;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        WorkBean bean = foodsBeans.get(i);
        viewHolder.item0.setText(bean.getLineName());
        viewHolder.item2.setText("开始时间:"+ TimeUtil.getTime(TimeUtil.parseTimeC(bean.getCreateDateStr())));
        viewHolder.item3.setText("停机次数:"+bean.getStopCount());
        viewHolder.item4.setText("停机时间:"+bean.getStopTime());
        viewHolder.item7.setText("任务单:"+bean.getOrderNo());
        viewHolder.item8.setText("产品编码:"+bean.getProductCode());
        viewHolder.item9.setText("产量:"+bean.getQty());
        viewHolder.item10.setText("计划产量:"+bean.getPlanQty());
//       未切单，已完成，生产中，停机中
        if(bean.getStateName().equals("未切单")){
            MyImageLoader.local(context,R.mipmap.rpc_sta0_p, viewHolder.item1);
        }else if(bean.getStateName().equals("生产中")){
            MyImageLoader.local(context,R.mipmap.rpc_sta1_p, viewHolder.item1);
        }else if(bean.getStateName().equals("停机中")){
            MyImageLoader.local(context,R.mipmap.rpc_sta2_p, viewHolder.item1);
        }else if(bean.getStateName().equals("已完成")){
            MyImageLoader.local(context,R.mipmap.rpc_sta3_p, viewHolder.item1);
        }


       if(bean.getState().equals("0")){
            viewHolder.item6.setVisibility(View.GONE);

            viewHolder.nos_show.setVisibility(View.GONE);
        }else{
            viewHolder.nos_show.setVisibility(View.VISIBLE);

           if(bean.isOpen()){
               viewHolder.item6.setVisibility(View.VISIBLE);
               viewHolder.item5.setText("收起");
           }else{
               viewHolder.item6.setVisibility(View.GONE);
               viewHolder.item5.setText("展开");
           }
        }
/*        viewHolder.item2.setVisibility(View.VISIBLE);
    if(mposition==i){
        viewHolder.child.setBackgroundResource(R.color.status_n);
    }else{
        viewHolder.child.setBackgroundResource(R.color.no_color);
    }
    viewHolder.item1.setText(bean.get("name"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,width*1/6);
        viewHolder.child.setLayoutParams(layoutParams);

       viewHolder.child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick!=null){
                    itemClick.clickItem(i);
                }

            }
        });*/
        viewHolder.item5.setOnClickListener((view -> {
            if(foodsBeans.get(i).isOpen()){
                foodsBeans.get(i).setOpen(false);
            }else {
                foodsBeans.get(i).setOpen(true);
            }
            notifyDataSetChanged();

        }));
    }

    @Override
    public int getItemCount() {
        return
                foodsBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
            TextView item0,item5,item2,item3,item4,item7,item8,item9,item10;
            LinearLayout item6,nos_show;
        ImageView item1;
        public ViewHolder(View itemView) {
            super(itemView);
            item0 = itemView.findViewById(R.id.item0);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);
            item5 = itemView.findViewById(R.id.item5);
            item6 = itemView.findViewById(R.id.item6);
            item7 = itemView.findViewById(R.id.item7);
            item8 = itemView.findViewById(R.id.item8);
            item9 = itemView.findViewById(R.id.item9);
            item10 = itemView.findViewById(R.id.item10);
            nos_show = itemView.findViewById(R.id.nos_show);

        }
    }
    private WorkItemAdapter.onItemClick itemClick;
    public void setOnItemClick(WorkItemAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
