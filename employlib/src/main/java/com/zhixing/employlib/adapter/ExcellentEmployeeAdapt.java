package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;

import java.util.List;

public class ExcellentEmployeeAdapt extends BaseQuickAdapter<ExcellentEmployeeEntity,BaseViewHolder> {
    public ExcellentEmployeeAdapt(int layoutResId, @Nullable List<ExcellentEmployeeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExcellentEmployeeEntity item) {

        ImageView imageView = helper.itemView.findViewById(R.id.iv_title);
        if ("月度".equals(item.Title)){
            imageView.setImageResource(R.mipmap.moth_employee);
        }else if ("年度".equals(item.Title)){
            imageView.setImageResource(R.mipmap.year_employee);

        }else{
            imageView.setImageResource(R.mipmap.jidu_employee);

        }

        if ("1".equals(item.rank)){
            helper.setText(R.id.tv_item_excellent_employee_rank,"第一名");
        }else if ("2".equals(item.rank)){
            helper.setText(R.id.tv_item_excellent_employee_rank,"第二名");

        }else{
            helper.setText(R.id.tv_item_excellent_employee_rank,"第三名");
        }

        helper.setText(R.id.tv_item_excellent_employee_name,item.name);
        helper.setText(R.id.tv_item_excellent_employee_worker,item.worker);
        helper.setText(R.id.tv_item_excellent_employee_num,item.rankNum);
        helper.setText(R.id.tv_item_excellent_employee_des,item.desc);

    }
}
