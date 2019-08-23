package com.shuben.zhixing.www.inspection.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.widget.CharAvatarView;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.bean.TypeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inspection_item extends RecyclerView.Adapter<Inspection_item.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private List<TypeInfo> foodsBeans;
    private Handler handler;
    private Map<String,String> map0;


    public Inspection_item(Activity context, List<TypeInfo> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        inflater = LayoutInflater.from(context);
    }

    public void updata(List<TypeInfo> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.inspection_main_item,parent,false);

            return new Inspection_item.ViewHolder(v);
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

        viewHolder.item2.setText(foodsBeans.get(i).getClassName());
        MyImageLoader.load(context,foodsBeans.get(i).getFilePath(),viewHolder.item1);

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
        ImageView item1;
        TextView item2;
        public ViewHolder(View itemView) {
            super(itemView);
            child = itemView.findViewById(R.id.child);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);

            int width = DensityUtil.getWindowWidth(context)/4;
            LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(width,width);
            params.gravity = Gravity.CENTER_HORIZONTAL;

            //item1.setLayoutParams(params);

        }
    }
    private Inspection_item.onItemClick itemClick;
    public void setOnItemClick(Inspection_item.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
