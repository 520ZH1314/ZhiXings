package com.zhixing.tpmlib.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.DailyCheckDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class DailyCheckAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public DailyCheckAdapter(List<String> data) {
        super(R.layout.item_requrement, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //设置店铺名称
        helper.setText(R.id.tv_requiment, item);
        helper.setOnClickListener(R.id.btn_check_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,DailyCheckDetailActivity.class));
            }
        });
    }
}
