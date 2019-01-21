package com.zhixing.tpmlib.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;

import java.util.List;

public class DailyCheckItemAdapt extends BaseQuickAdapter<DailyCheckItemBean,BaseViewHolder> {
    private String status;

    public DailyCheckItemAdapt(int layoutResId, @Nullable List<DailyCheckItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyCheckItemBean item) {
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

    }
}
