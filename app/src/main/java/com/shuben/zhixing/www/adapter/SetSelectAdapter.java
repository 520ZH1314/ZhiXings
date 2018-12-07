package com.shuben.zhixing.www.adapter;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;
import com.base.zhixing.www.util.DensityUtil;

import java.util.ArrayList;
import java.util.Map;

public class SetSelectAdapter extends RecyclerView.Adapter<SetSelectAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private ArrayList<Map<String,String>> foodsBeans;
    private Handler handler;
    private Map<String,String> map0;

    int width = 0;
    public SetSelectAdapter(Activity context, ArrayList<Map<String,String>> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        width = DensityUtil.getWindowWidth(context);
        inflater = LayoutInflater.from(context);
    }

    public void updata(ArrayList<Map<String,String>> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.setselect_item,parent,false);

            return new SetSelectAdapter.ViewHolder(v);
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

        Map<String,String> bean = foodsBeans.get(i);


        viewHolder.item2.setVisibility(View.VISIBLE);
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
        });
    }

    @Override
    public int getItemCount() {
        return  foodsBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout child;
        ImageView item2;
        TextView item1;
        public ViewHolder(View itemView) {
            super(itemView);

            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            child  = itemView.findViewById(R.id.child);
        }
    }
    private SetSelectAdapter.onItemClick itemClick;
    public void setOnItemClick(SetSelectAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
