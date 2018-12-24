package com.zhixing.masslib.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.QC_NoListBean;

import java.util.ArrayList;

public class ShowMassItemAdapter extends RecyclerView.Adapter<ShowMassItemAdapter.ViewHolder> {
    private Activity context;
    private final LayoutInflater inflater;
    private ArrayList<QC_NoListBean> noListBeans;
   public ShowMassItemAdapter(Activity context,ArrayList<QC_NoListBean> noListBeans){
       this.context = context;
       this.noListBeans = noListBeans;
       inflater = LayoutInflater.from(context);
    }
    public void updata(ArrayList<QC_NoListBean> noListBeans){
       this.noListBeans = noListBeans;
       notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.mass_detail_item,parent,false);
        return new ShowMassItemAdapter.ViewHolder(v);
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
        QC_NoListBean bean  = noListBeans.get(position);
        holder.item0.setText(bean.getNo());
        holder.item1.setText(bean.getDes());
        holder.item2.setText(bean.getTime());
        holder.item3.setText(bean.getNum());
    }

    @Override
    public int getItemCount() {
        return noListBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView item0,item1,item2,item3;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,40));
            layout.setLayoutParams(params);
            item0 = layout.findViewById(R.id.item0);
            item1 = layout.findViewById(R.id.item1);
            item2 = layout.findViewById(R.id.item2);
            item3 = layout.findViewById(R.id.item3);


        }
    }
    private ShowMassItemAdapter.onItemClick itemClick;
    public void setOnItemClick(ShowMassItemAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
