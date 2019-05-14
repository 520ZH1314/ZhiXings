package com.zhixing.rpclib;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.base.zhixing.www.util.DensityUtil;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.selector.DevSelector;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.beans.NoItem;
import java.util.ArrayList;
import java.util.Map;

public class NoSelectAdapter extends RecyclerView.Adapter<NoSelectAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private ArrayList<NoItem> foodsBeans;
    private Handler handler;
    private Map<String,String> map0;
   
    int width = 0;
    public NoSelectAdapter(Activity context, ArrayList<NoItem> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        width = DensityUtil.getWindowWidth(context);
        inflater = LayoutInflater.from(context);

    }

    public void updata(ArrayList<NoItem> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.select_lay,parent,false);

            return new NoSelectAdapter.ViewHolder(v);
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

        NoItem bean = foodsBeans.get(i);


    switch (bean.getStatus()){
        case 0:
            viewHolder.item_status.setText(context.getResources().getString(R.string.state0));
            break;
        case 1:
            viewHolder.item_status.setText(context.getResources().getString(R.string.state1));
            break;
        case 2:
            viewHolder.item_status.setText(context.getResources().getString(R.string.state2));
            break;
        case 3:
            viewHolder.item_status.setText(context.getResources().getString(R.string.state3));
            break;
    }
    viewHolder.item11.setText("产品名称:"+bean.getName());
        viewHolder.item12.setText("批次号:"+bean.getBatchNo());
    viewHolder.item13.setText("客户:"+bean.getKehu());
    viewHolder.item14.setText("数量:"+bean.getNum());

    viewHolder.item15.setText(bean.getTime());
    if(mposition==i){
        viewHolder.child.setBackgroundResource(R.color.status_n);
    }else{
        viewHolder.child.setBackgroundResource(R.color.no_color);
    }

    viewHolder.item1.setText(bean.getNo());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (width*0.92), LinearLayout.LayoutParams.WRAP_CONTENT);
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
        TextView  item_status;
        TextView item1,item11,item12,item13,item14,item15;
        public ViewHolder(View itemView) {
            super(itemView);
            item11 = itemView.findViewById(R.id.item11);
            item12 = itemView.findViewById(R.id.item12);
            item13 = itemView.findViewById(R.id.item13);
            item14 = itemView.findViewById(R.id.item14);
            item15 = itemView.findViewById(R.id.item15);
            item1 = itemView.findViewById(R.id.item1);
            item_status = itemView.findViewById(R.id.item_status);
            child  = itemView.findViewById(R.id.child);
        }
    }
    private NoSelectAdapter.onItemClick itemClick;
    public void setOnItemClick(NoSelectAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
