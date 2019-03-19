package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.IntegralEventEntity;

import java.util.List;

public class IntegralEventAdapt extends BaseQuickAdapter<IntegralEventEntity,BaseViewHolder> {
    public IntegralEventAdapt(int layoutResId, @Nullable List<IntegralEventEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralEventEntity item) {
                 helper.setText(R.id.tv_integral_event_num,item.Number);
               helper.setText(R.id.tv_integral_event_score,item.Score);
               helper.setText(R.id.tv_integral_event_content,item.Des);
    }
}
