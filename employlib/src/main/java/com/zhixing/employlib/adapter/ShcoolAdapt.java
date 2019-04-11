package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.resume.CompanyEntity;

import java.util.List;

public class ShcoolAdapt extends BaseQuickAdapter<CompanyEntity,BaseViewHolder> {
    public ShcoolAdapt(int layoutResId, @Nullable List<CompanyEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyEntity item) {
        helper.setText(R.id.tv_item_my_job_name,item.company);
        helper.setText(R.id.tv_item_my_job_time,item.StartTime+"至"+item.EndTime);
        helper.setText(R.id.tv_item_my_job,item.job);

    }
}
