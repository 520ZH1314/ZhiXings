package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;
import com.zhixing.tpmlib.view.LabelView;

import java.util.List;

public class MaintenanceWarmingAdapt extends BaseQuickAdapter<MaintenanceWarnBean, BaseViewHolder> {
    public MaintenanceWarmingAdapt(int layoutResId, @Nullable List<MaintenanceWarnBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaintenanceWarnBean item) {
        helper.setText(R.id.tv_machine_num, item.sum);
        helper.setText(R.id.tv_machine_name, item.machineName);
        helper.setText(R.id.tv_machine_id, item.machineId);
        helper.setText(R.id.tv_maintain_type, item.maintainType);
        LabelView viewById = helper.itemView.findViewById(R.id.tv_rotate);
        if ("5".equals(item.getMachineStatus())){
            viewById.setPrimaryText("保养中");
            viewById.setTriangleBackgroundColor(Color.parseColor("#15BC84"));
        }else if ("10".equals(item.getMachineStatus())){
            viewById.setPrimaryText("待审核");
            viewById.setTriangleBackgroundColor(Color.parseColor("#FF943D"));
        }else{
            viewById.setPrimaryText("未保养");
            viewById.setTriangleBackgroundColor(Color.parseColor("#4a4a4a"));
        }


    }
}
