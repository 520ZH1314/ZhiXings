package com.zhixing.tpmlib.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.PlannedDetailActivity;
import com.zhixing.tpmlib.bean.PlanMatheBean;
import com.zhixing.tpmlib.bean.PlannetEntity;
import com.zhixing.tpmlib.view.LabelView;

import java.util.List;

public class PlannedMmatenceAdapter extends BaseQuickAdapter<PlannetEntity, BaseViewHolder> {

    private PlanMatheBean planMatheBean;
    public PlannedMmatenceAdapter(List<PlannetEntity> data) {
        super(R.layout.item_planned_mentance, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, PlannetEntity entity) {
        helper.setText(R.id.tv_matche_name, entity.getEquipmentName());
        helper.setText(R.id.tv_matche_date, entity.getLastMaintanceDate());
        helper.setText(R.id.tv_matche_num, entity.getEquipmentCode());
            helper.setText(R.id.tv_num, entity.getNum());
//        设置保养人
        helper.setText(R.id.tv_panned_name, entity.getLastMaintanceUser());
        String status = entity.getStatus();
        LabelView labelView = (LabelView) helper.itemView.findViewById(R.id.tv_rotate);
        if (TextUtils.isEmpty(status)) {
            labelView.setPrimaryText("待审批");
        } else if (status.equals("5")) {
            labelView.setPrimaryText("保养中");
        } else if (status.equals("10")) {
            labelView.setPrimaryText("已保养");
        }
        String equipmentId = entity.getEquipmentId();
        String classId = entity.getClassId();
        String planId = entity.getPlanId();
        String gradeId = entity.getGradeId();
        String equipmentName = entity.getEquipmentName();
        planMatheBean = new PlanMatheBean();
        planMatheBean.setEquipmentId(equipmentId);
        planMatheBean.setClassId(classId);
        planMatheBean.setPlanId(planId);
        planMatheBean.setGradeId(gradeId);
        planMatheBean.setEquipmentName(equipmentName);
        helper.setText(R.id.tv_check_date, entity.getMaintanceDate() + "天");
        helper.setOnClickListener(R.id.tv_check_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlannedDetailActivity.class);
                intent.putExtra("planMatheBean_data", planMatheBean);
                mContext.startActivity(intent);
            }
        });
    }


}
