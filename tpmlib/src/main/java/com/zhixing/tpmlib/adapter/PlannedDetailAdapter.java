package com.zhixing.tpmlib.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.PlanDatailBean;


import java.util.List;

public class PlannedDetailAdapter extends BaseQuickAdapter<PlanDatailBean,BaseViewHolder> {
    public PlannedDetailAdapter(List<PlanDatailBean> data) {
        super(R.layout.item_planned_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanDatailBean entity) {
        helper.setText(R.id.tv_requiment_num,entity.getNum());
        helper.setText(R.id.tv_planned_employee,entity.getOperator());//设置计划保养的人员
        helper.setText(R.id.tv_check_stard,entity.getDescription());//设置计划保养的标准
        TextView tv_check_status =(TextView) helper.itemView.findViewById(R.id.tv_check_status);
        String fruit = entity.getFruit();
        if(TextUtils.isEmpty(fruit)){
            tv_check_status.setText("未提交");
        }else if(fruit.equals("0")){
            tv_check_status.setText("待审核");
        }else if(fruit.equals("1")){
            tv_check_status.setText("已审核");
        }
    }
}
