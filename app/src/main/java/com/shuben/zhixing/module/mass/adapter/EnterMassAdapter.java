package com.shuben.zhixing.module.mass.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuben.zhixing.module.mass.bean.MassItemBean;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.util.DensityUtil;

import java.util.ArrayList;

public class EnterMassAdapter extends RecyclerView.Adapter<EnterMassAdapter.ViewHolder> {
    private Activity context;
    private final LayoutInflater inflater;
    private ArrayList<MassItemBean> massItemBeans;
    private int TYPE;//是首检 还是全检 还是抽检
   public EnterMassAdapter(Activity context,ArrayList<MassItemBean> massItemBeans){
       this.context = context;
       this.massItemBeans = massItemBeans;
       inflater = LayoutInflater.from(context);
    }
    private int type;
    public void setType(int type){
        this.type = type;
    }
    public void updata(ArrayList<MassItemBean> massItemBeans){
       this.massItemBeans = massItemBeans;
       notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.enter_mass_item,parent,false);
        return new EnterMassAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClick!=null){
                        itemClick.clickItem(position);
                    }
                }
            });
            MassItemBean bean = massItemBeans.get(position);
            holder.item0.setText("工单号:"+bean.getNo());
            holder.item1.setText(bean.getTime());
            holder.item4.setText("工单数量:"+(bean.getCount()));
            holder.item2.setText("车间/线体:"+bean.getWork()+"/"+bean.getLine());
            holder.item3.setText("产品名称:"+bean.getProduct());
            holder.item5.setText("送检时间:"+bean.getData());
            if(type==2){
                //抽检处理
                holder.item6.setVisibility(View.VISIBLE);
                holder.item6.setText("批次数量:"+bean.getpNum());

            }else{
                holder.item6.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return massItemBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView item0,item1,item2,item3,item4,item5,item6;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,150));
            layout.setLayoutParams(params);
            item0 = itemView.findViewById(R.id.item0);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);
            item5 = itemView.findViewById(R.id.item5);
            item6 = itemView.findViewById(R.id.item6);


        }
    }
    private EnterMassAdapter.onItemClick itemClick;
    public void setOnItemClick(EnterMassAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
