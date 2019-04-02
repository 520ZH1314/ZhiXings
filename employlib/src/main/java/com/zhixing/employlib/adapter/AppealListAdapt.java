package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.base.zhixing.www.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealListEntity;
import com.zhixing.employlib.model.AppealPersonEntity;

import java.sql.Time;
import java.util.List;

public class AppealListAdapt extends BaseQuickAdapter<AppealList,BaseViewHolder> {
    private List<AppealList> mList;//数据源
    public AppealListAdapt(int layoutResId, @Nullable List<AppealList> data) {
        super(layoutResId, data);
        this.mList  = data;
    }
    public void updata(List<AppealList> data){
        this.mList = data;
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, AppealList item) {
        
        TextView tvName =helper.itemView.findViewById(R.id.tv_item_appeal_list_name);
        TextView tvStatus =helper.itemView.findViewById(R.id.tv_item_appeal_list_status);
        if (item.getStatus()==1){
            //已审核
            tvName.setTextColor(mContext.getResources().getColor(R.color.green));
            tvStatus.setText("已通过");
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.green)
                    .radius(10)
                    .into(tvStatus);
        }else if(item.getStatus()==0){
            tvName.setTextColor(mContext.getResources().getColor(R.color.txt_bor));
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.txt_bor));
            tvStatus.setText("未审核");
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.txt_bor)
                    .radius(10)
                    .into(tvStatus);
        }else if(item.getStatus()==2){
            tvName.setTextColor(mContext.getResources().getColor(R.color.orange));
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
            tvStatus.setText("未通过");
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.orange)
                    .radius(10)
                    .into(tvStatus);
        }


        helper.setText(R.id.tv_item_appeal_list_date,"异常日期:"+ TimeUtil.getTime(TimeUtil.parseTimeC(item.getKeyDate())));
        helper.setText(R.id.tv_item_appeal_list_event_name,"申诉事件:"+item.getKeyName());
        helper.setText(R.id.tv_item_appeal_list_name,"处理人:"+item.getHandleUserName());
        helper.setText(R.id.tv_item_appeal_list_desc,"描述:"+item.getOpinion());



    }
}
