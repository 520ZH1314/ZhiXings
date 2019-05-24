package com.zhixing.view;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.zhixing.beans.PzBean;
import com.zhixing.rpclib.R;

import java.util.ArrayList;

public class PzViewItemAdapter extends RecyclerView.Adapter<PzViewItemAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private ArrayList<PzBean> foodsBeans;
    private Handler handler;
    private      String[] city;
    int width = 0;
    public PzViewItemAdapter(Activity context, ArrayList<PzBean> foodsBeans, Handler handler,int ng_leav){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        this.ng_leav = ng_leav;
        width = DensityUtil.getWindowWidth(context);
        inflater = LayoutInflater.from(context);
        Resources res =context.getResources();
        city=res.getStringArray(R.array.ng_lis);
    }

    public void updata(ArrayList<PzBean> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }
    private int ng_leav = 0;
    public void setNg_leav(int ng_leav){
        this.ng_leav = ng_leav;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.rpc_pz_item,parent,false);

            return new PzViewItemAdapter.ViewHolder(v);
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
        PzBean pb = foodsBeans.get(i);
        viewHolder.name.setText(pb.getLineName());
        double dv = 0;
        if(pb.getSumQty()!=0){
            dv = FileUtils.formatDouble(Double.parseDouble(String.valueOf(pb.getSumNG()))/Double.parseDouble(String.valueOf(pb.getSumQty()))*100);
        }

        if(dv>=Double.parseDouble(city[ng_leav])){
            viewHolder.ng.setTextColor(Color.RED);
        }else{
            viewHolder.ng.setTextColor(context.getResources().getColor(R.color.txt_viw));
        }
        viewHolder.ng.setText("不良率:"+dv+"%");
        viewHolder.num.setText("生产数:"+pb.getSumQty());

    }

    @Override
    public int getItemCount() {
//        return 8;
              return   foodsBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView name,ng,num;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ng = itemView.findViewById(R.id.ng);
            num = itemView.findViewById(R.id.num);

        }
    }
    private PzViewItemAdapter.onItemClick itemClick;
    public void setOnItemClick(PzViewItemAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
