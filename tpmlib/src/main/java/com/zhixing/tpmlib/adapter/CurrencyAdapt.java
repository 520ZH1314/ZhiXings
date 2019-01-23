package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.MaintenanceBean;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;
import com.zhixing.tpmlib.bean.SpotCheckBean;
import com.zhixing.tpmlib.bean.WarnBean;
import com.zhixing.tpmlib.view.LabelView;

import java.util.List;

public class CurrencyAdapt<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public CurrencyAdapt(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
           if (item instanceof MaintenanceBean){
               MaintenanceBean maintenanceBean  =(MaintenanceBean)item;
               helper.setText(R.id.tv_tpm_currency_tv1,"完成时间:");
               helper.setText(R.id.tv_tpm_currency_tv2,"保养类别:");
               helper.setText(R.id.tv_tpm_currency_tv3,"保养人:");
               helper.setText(R.id.tv_tpm_currency_tv5,"延期天数:");

               helper.setText(R.id.tv_tpm_currency_tv1_value,getFormartTime(maintenanceBean.time));
               helper.setText(R.id.tv_tpm_currency_tv2_value,maintenanceBean.MaintenanceType);
               helper.setText(R.id.tv_tpm_currency_tv3_value,maintenanceBean.MaintenancePeople);
               helper.setText(R.id.tv_tpm_currency_tv5_value,maintenanceBean.delayDay);
               helper.itemView.findViewById(R.id.tv_currency_status).setVisibility(View.GONE);
           }else if(item instanceof WarnBean){
               WarnBean warnBean  =(WarnBean)item;
               helper.setText(R.id.tv_tpm_currency_tv1,"时间:");
               helper.setText(R.id.tv_tpm_currency_tv2,"异常小类:");
               helper.setText(R.id.tv_tpm_currency_tv3,"处理人:");
               helper.setText(R.id.tv_tpm_currency_tv5,"处理措施:");


               helper.setText(R.id.tv_tpm_currency_tv1_value,warnBean.time);
               helper.setText(R.id.tv_tpm_currency_tv2_value,warnBean.warnType);
               helper.setText(R.id.tv_tpm_currency_tv3_value, warnBean.doPeople);
               helper.setText(R.id.tv_tpm_currency_tv5_value,warnBean.howDo);
               LabelView viewById = helper.itemView.findViewById(R.id.tv_currency_status);
               viewById.setPrimaryText(warnBean.status);
               if ("未关闭".equals(warnBean.status)){
                   viewById.setTriangleBackgroundColor(Color.parseColor("#FF943D"));
               }else{
                   viewById.setTriangleBackgroundColor(Color.parseColor("#4a4a4a"));
               }
           }else{
               SpotCheckBean spotCheckBean  =(SpotCheckBean)item;
               helper.setText(R.id.tv_tpm_currency_tv1s,"日期:");
               helper.setText(R.id.tv_tpm_currency_tv2s,"点检时间:");
               helper.setText(R.id.tv_tpm_currency_tv3s,"点检人:");



               helper.setText(R.id.tv_tpm_currency_tv1_values,spotCheckBean.date);
               helper.setText(R.id.tv_tpm_currency_tv2_values,spotCheckBean.time);
               helper.setText(R.id.tv_tpm_currency_tv3_values, spotCheckBean.SpotCheckPeople);


               LabelView viewById = helper.itemView.findViewById(R.id.tv_currency_statuss);

               if ("5".equals(spotCheckBean.status)){
                   viewById.setPrimaryText("未完成");
                   viewById.setTriangleBackgroundColor(Color.parseColor("#FF943D"));
               }else{
                   viewById.setTriangleBackgroundColor(Color.parseColor("#4a4a4a"));
                   viewById.setPrimaryText("已完成");
               }


           }

    }

    public String getFormartTime(String data) {
        String buyDate = data;
        String t[] = buyDate.split("T");
        String string=t[0]+" "+t[1];
        return string;
    }
}
