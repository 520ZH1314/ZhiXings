package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;

import java.util.List;

public class ExcellentEmployeeAdapt extends BaseQuickAdapter<ExcellentEmployeeEntity,BaseViewHolder> {
    private  String ip;

    public ExcellentEmployeeAdapt(int layoutResId, @Nullable List<ExcellentEmployeeEntity> data) {

        super(layoutResId, data);
          data.size();
        ip = PerformanceApi.IMAGE_BASE_URL;
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


        if (item.ImageUrl!=null&&!TextUtils.isEmpty(item.ImageUrl)){
            ImageView imageView1=helper.itemView.findViewById(R.id.iv_excellent_employee);
            MyImageLoader.loads(mContext,ip+item.ImageUrl,imageView1,R.drawable.excelicent_employee);
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
