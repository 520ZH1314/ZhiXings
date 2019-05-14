package com.zhixing.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.zhixing.www.util.DensityUtil;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.selector.DevSelector;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.beans.HourBean;
import com.zhixing.rpclib.R;

import java.util.ArrayList;
import java.util.Map;

public class HourItemAdapter extends RecyclerView.Adapter<HourItemAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private Activity context;

    private ArrayList<HourBean> foodsBeans;
    private Handler handler;

    int width = 0;
    public HourItemAdapter(Activity context, ArrayList<HourBean> foodsBeans, Handler handler){
        this.context = context;
        this.foodsBeans = foodsBeans;
        this.handler = handler;
        width = DensityUtil.getWindowWidth(context);
        inflater = LayoutInflater.from(context);
    }

    public void updata(ArrayList<HourBean> foodsBeans){
        this.foodsBeans = foodsBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.hour_view_item,parent,false);

            return new HourItemAdapter.ViewHolder(v);
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

        HourBean bean = foodsBeans.get(i);


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
viewHolder.it00.setText(bean.getTimeDisplay());
viewHolder.it1.setText("型号:"+bean.getProductName());
viewHolder.it2.setText(bean.getUPH());
viewHolder.it3.setText(bean.getQty());
viewHolder.it4.setText(bean.getQtyNG());
viewHolder.it5.setText(bean.getDetailedNG());
        viewHolder.it6.setText(bean.getRemark());
    viewHolder.reson_btn.setOnClickListener((v)->{
        Message msg = new Message();
        msg.what = 2;
        msg.obj = bean.getID();
        msg.arg1 = i;
       handler.sendMessage(msg);
    });
    }

    @Override
    public int getItemCount() {
        return
                foodsBeans.size();
    }
    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView reson_btn;
        TextView it00,it1,it2,it3,it4,it5,it6;
        public ViewHolder(View itemView) {
            super(itemView);
            reson_btn = itemView.findViewById(R.id.reson_btn);
            Drawable normalsc = DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .solid(R.color.title_bg)
                    .radius(7)
                    .build();
            DevShapeUtils
                    .selector(DevSelector.STATE_PRESSED,normalsc,normalsc)
                    .selectorTextColor("#575757", "#ffffff")
                    .into(reson_btn);
            it00 = itemView.findViewById(R.id.it00);
            it1 = itemView.findViewById(R.id.it1);
            it2 = itemView.findViewById(R.id.it2);
            it3 = itemView.findViewById(R.id.it3);
            it4 = itemView.findViewById(R.id.it4);
            it5 = itemView.findViewById(R.id.it5);
            it6 = itemView.findViewById(R.id.it6);
        }
    }
    private HourItemAdapter.onItemClick itemClick;
    public void setOnItemClick(HourItemAdapter.onItemClick click){
        this.itemClick = click;
    }

    /**
     * 增加点击按钮事件
     */
    public interface onItemClick {
        void clickItem(int pos);
    }

}
