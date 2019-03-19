package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.model.NewEmployeeEntity;

import java.util.List;

public class NewEmployeeAdapt extends BaseQuickAdapter<NewEmployeeEntity,BaseViewHolder> {
    public NewEmployeeAdapt(int layoutResId, @Nullable List<NewEmployeeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewEmployeeEntity item) {
       helper.setText(R.id.tv_item_new_employee_name,item.name);
        helper.setText(R.id.tv_item_new_employee_address,item.address);
        helper.setText(R.id.tv_item_new_employee_go_time,item.goTime);
        helper.setText(R.id.tv_item_new_employee_name4,item.teamName);
        helper.setText(R.id.tv_item_new_employee_work,item.worker);
        helper.setText(R.id.tv_item_new_employee_desc,item.desc);
    }
}
