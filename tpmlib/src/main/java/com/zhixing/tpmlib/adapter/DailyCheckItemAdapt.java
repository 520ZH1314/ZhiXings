package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.MaintenanceItemEntity;

import java.util.List;

public class DailyCheckItemAdapt<T> extends BaseQuickAdapter<T,BaseViewHolder> {
    private String status;

    public DailyCheckItemAdapt(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T items) {

        if (items instanceof DailyCheckItemBean ){
            DailyCheckItemBean item=(DailyCheckItemBean)items;
            helper.setText(R.id.tv_daily_check_item_body,item.getDescription());
            CardView cardView =helper.itemView.findViewById(R.id.card_daily_check_item);
            if ("1".equals(item.getFruit())){
                status="OK";
                cardView.setCardBackgroundColor(Color.parseColor("#15BC84"));
            }else if ("0".equals(item.getFruit())){
                status="NG";
                cardView.setCardBackgroundColor(Color.parseColor("#FF943D"));

            }else{
                status="未点检";
                cardView.setCardBackgroundColor(Color.parseColor("#ADADAD"));

            }


            helper.setText(R.id.tv_daily_check_item_status,status);

        }else{

            CardView cardView =helper.itemView.findViewById(R.id.card_daily_check_item);

            MaintenanceItemEntity bean =(MaintenanceItemEntity)items;
            if ("".equals(bean.getFruit())){
                //未保养
                helper.setText(R.id.tv_daily_check_item_status,"未保养");
                cardView.setCardBackgroundColor(Color.parseColor("#ADADAD"));

            }else if ("1".equals(bean.getFruit())){
                //已审核
                helper.setText(R.id.tv_daily_check_item_status,"已审核");
                cardView.setCardBackgroundColor(Color.parseColor("#15BC84"));

            }else{
                //待审核
                helper.setText(R.id.tv_daily_check_item_status,"待审核");
                cardView.setCardBackgroundColor(Color.parseColor("#FF943D"));


            }

            TextView tv=helper.itemView.findViewById(R.id.tv_warn12);
            helper.setText(R.id.tv_daily_check_item_body,bean.getDescription());
            tv.setText("保养标准");
        }



    }
}
