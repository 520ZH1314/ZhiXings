package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.model.NewEmployeeEntity;

import java.util.List;

public class NewEmployeeAdapt extends BaseQuickAdapter<NewEmployeeEntity,BaseViewHolder> {
    private  String ip;

    public NewEmployeeAdapt(int layoutResId, @Nullable List<NewEmployeeEntity> data) {
        super(layoutResId, data);
        ip = SharedPreferencesTool.getMStool(mContext).getIp();

    }

    @Override
    protected void convert(BaseViewHolder helper, NewEmployeeEntity item) {


        if (item.imageUrl!=null&&!TextUtils.isEmpty(item.imageUrl)){
            ImageView imageView =helper.itemView.findViewById(R.id.iv_new_employee);
            MyImageLoader.load(mContext,item.imageUrl,imageView);
        }

       helper.setText(R.id.tv_item_new_employee_name,item.name);
        helper.setText(R.id.tv_item_new_employee_address,item.address);
        helper.setText(R.id.tv_item_new_employee_go_time,item.goTime);
        helper.setText(R.id.tv_item_new_employee_name4,item.teamName);
        helper.setText(R.id.tv_item_new_employee_work,item.worker);
        helper.setText(R.id.tv_item_new_employee_desc,item.desc);
    }
}
