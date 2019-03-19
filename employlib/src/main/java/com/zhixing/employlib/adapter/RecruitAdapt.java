package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.RecruitEntry;

import java.util.List;

public class RecruitAdapt extends BaseQuickAdapter<RecruitEntry, BaseViewHolder> {
    public RecruitAdapt(int layoutResId, @Nullable List<RecruitEntry> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitEntry item) {
        Button button = (Button) helper.itemView.findViewById(R.id.btn_item_recruit_delivered);
        TextView tvMoney = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_money);
        TextView tvDesc = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_work_desc);
        TextView tvDescName = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_work_desc_name);
        TextView tvDate = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_date);
        TextView tvWorkName = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_wrok_name);
        TextView tvWorkYear = (TextView) helper.itemView.findViewById(R.id.tv_item_recruit_work_year);


        if ("0".equals(item.status)) {
            //没有投递
            button.setVisibility(View.GONE);
            tvDesc.setText(item.workDesc);
            tvMoney.setText(item.money);
            tvWorkName.setText(item.workName);
            tvDate.setText(item.date);
            tvWorkYear.setText(item.workYear);
        } else {
            //已投递
            button.setVisibility(View.VISIBLE);
            button.setText("已投递");
            button.setTextColor(mContext.getResources().getColor(R.color.gray02));
            DevShapeUtils
                    .shape(DevShape.RECTANGLE)
                    .line(1, R.color.gray02)
                    .radius(5)
                    .into(button);

            tvMoney.setTextColor(mContext.getResources().getColor(R.color.gray02));
            tvDesc.setTextColor(mContext.getResources().getColor(R.color.gray02));
            tvDescName.setTextColor(mContext.getResources().getColor(R.color.gray02));
            tvDate.setTextColor(mContext.getResources().getColor(R.color.gray02));
            tvWorkName.setTextColor(mContext.getResources().getColor(R.color.gray02));

            tvDesc.setText(item.workDesc);
            tvMoney.setText(item.money);
            tvWorkName.setText(item.workName);
            tvDate.setText(item.date);
            tvWorkYear.setText(item.workYear);

        }


    }
}
