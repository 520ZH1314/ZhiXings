package com.zhixing.employlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.RecruitDeiveredEntity;

import java.util.List;

public class RecruitDeiveredAdapt extends BaseQuickAdapter<RecruitDeiveredEntity,BaseViewHolder> {
    public RecruitDeiveredAdapt(int layoutResId, @Nullable List<RecruitDeiveredEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitDeiveredEntity item) {

        Button button=(Button)helper.itemView.findViewById(R.id.btn_deiver_status);
        if (item.type.equals("0")){
            if ("4".equals(item.status)){
                //已通过
                button.setText("已通过");
                button.setTextColor(mContext.getResources().getColor(R.color.white));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.green)
                        .radius(15)
                        .into(button);


            }else if ("3".equals(item.status)){
                //不合适
                //已通过
                button.setText("不合适");
                button.setTextColor(mContext.getResources().getColor(R.color.gray));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.gray02)
                        .radius(15)
                        .into(button);
            }else {

                button.setText("待审核");
                button.setTextColor(mContext.getResources().getColor(R.color.white));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.orange)
                        .radius(15)
                        .into(button);
            }
        }else{
            if ("4".equals(item.status)){
                //已通过
                button.setText("推荐成功");
                button.setTextColor(mContext.getResources().getColor(R.color.white));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.green)
                        .radius(15)
                        .into(button);


            }else if ("3".equals(item.status)){
                //不合适
                //已通过
                button.setText("不合适");
                button.setTextColor(mContext.getResources().getColor(R.color.gray));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.gray02)
                        .radius(15)
                        .into(button);
            }else {

                button.setText("待审核");
                button.setTextColor(mContext.getResources().getColor(R.color.white));

                DevShapeUtils
                        .shape(DevShape.RECTANGLE)
                        .solid(R.color.orange)
                        .radius(15)
                        .into(button);
            }
        }



        helper.setText(R.id.tv_item_delivered_work_name,item.workName);
        helper.setText(R.id.tv_deliver_date,item.date);
        helper.setText(R.id.tv_deliver_money,item.money);
        helper.setText(R.id.tv_deliver_year,item.workYear);



    }
}
