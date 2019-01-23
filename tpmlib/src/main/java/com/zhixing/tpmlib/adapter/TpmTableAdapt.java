package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.MaintenanceBean;
import com.zhixing.tpmlib.bean.SpotCheckBean;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.bean.WarnBean;
import com.zhixing.tpmlib.view.LabelView;

import java.util.List;

public class TpmTableAdapt<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public TpmTableAdapt(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

        if (item instanceof String){
             String  s= (String)item;

            helper.setText(R.id.tv_recy_left,s);


        }else if (item instanceof StaticticalAnalAnalyEntity){

             helper.setText(R.id.tv_recy_tpm_month,((StaticticalAnalAnalyEntity) item).getMonth());
            helper.setText(R.id.tv_recy_tpm_total_num,((StaticticalAnalAnalyEntity) item).getTotalSum()+"次");
            helper.setText(R.id.tv_recy_tpm_com_on_time,((StaticticalAnalAnalyEntity) item).getOKRecord()+"次");
            helper.setText(R.id.tv_recy_tpm_com_on_time_pre,((StaticticalAnalAnalyEntity) item).getOnTimeRate()+"%");

        }



    }

    public String getFormartTime(String data) {
        String buyDate = data;
        String t[] = buyDate.split("T");
        String string=t[0]+" "+t[1];
        return string;
    }
}
