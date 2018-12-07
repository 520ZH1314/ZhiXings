package com.shuben.zhixing.module.mass.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.Map;

public class LeftListAdapter extends RecyclerView.Adapter<LeftListAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Context context;

    private ArrayList<String> foodsBeans;
    private Handler handler;
    private Map<String,String> map0;


    public LeftListAdapter(Context context, ArrayList<String> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;

        inflater = LayoutInflater.from(context);
    }

    public void updata(ArrayList<String> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.left_list_item,parent,false);

            return new LeftListAdapter.ViewHolder(v);
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

        viewHolder.item1.setText(foodsBeans.get(i));


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
        return foodsBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{


       LinearLayout child;
        TextView item1;
        public ViewHolder(View itemView) {
            super(itemView);
            child = itemView.findViewById(R.id.child);
            item1 = itemView.findViewById(R.id.item1);

        }
    }
    private LeftListAdapter.onItemClick itemClick;
    public void setOnItemClick(LeftListAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
