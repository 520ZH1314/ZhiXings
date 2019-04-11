package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.resume.CompanyEntity;

import java.util.ArrayList;
import java.util.List;

public class JobAdapt extends BaseQuickAdapter<CompanyEntity,BaseViewHolder> {

    private List<CompanyEntity> datas=new ArrayList<>();
    public JobAdapt(int layoutResId, @Nullable List<CompanyEntity> data) {
        super(layoutResId, data);
        this.datas=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyEntity item) {
        helper.setText(R.id.tv_item_my_job_name,item.company);
        helper.setText(R.id.tv_item_my_job_time,item.StartTime+"至"+item.EndTime);
        helper.setText(R.id.tv_item_my_job,item.job);
        ConstraintLayout constraintLayout = helper.itemView.findViewById(R.id.con_get_resume);




    }
}
