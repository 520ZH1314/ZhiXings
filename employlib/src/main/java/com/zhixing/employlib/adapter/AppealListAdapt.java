package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.AppealListEntity;

import java.util.List;

public class AppealListAdapt extends BaseQuickAdapter<AppealListEntity,BaseViewHolder> {
    public AppealListAdapt(int layoutResId, @Nullable List<AppealListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppealListEntity item) {
        TextView tvName =helper.itemView.findViewById(R.id.tv_item_appeal_list_name);
        TextView tvStatus =helper.itemView.findViewById(R.id.tv_item_appeal_list_status);
        if ("1".equals(item.eventStatus)){
            //已审核
            tvName.setTextColor(mContext.getResources().getColor(R.color.green));
            tvStatus.setText("已审核");
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.green));
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.green)
                    .radius(10)
                    .into(tvStatus);
        }else{
            tvName.setTextColor(mContext.getResources().getColor(R.color.orange));
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.orange));
            tvStatus.setText("待审核");
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.orange)
                    .radius(10)
                    .into(tvStatus);
        }


        helper.setText(R.id.tv_item_appeal_list_date,"异常日期:"+item.eventDate);
        helper.setText(R.id.tv_item_appeal_list_event_name,"申诉事件:"+item.eventName);
        helper.setText(R.id.tv_item_appeal_list_name,"处理人:"+item.doPeople);
        helper.setText(R.id.tv_item_appeal_list_desc,"处理人:"+item.desc);



    }
}
