package com.zhixing.employlib.adapter;


import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.GradingedEntity;

import java.util.List;

/**
 * @author zjq
 * create at 2019/3/13 上午11:34
 * 已评分列表
 */
public class GradingedListAdapt extends BaseQuickAdapter<GradingedEntity, BaseViewHolder> {
    public GradingedListAdapt(int layoutResId, @Nullable List<GradingedEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GradingedEntity item) {

        //加粗处理
        TextView woker = helper.itemView.findViewById(R.id.tv_gradinged_item_worker);
        TextView score = helper.itemView.findViewById(R.id.tv_gradinged_item_score);
        TextView rank = helper.itemView.findViewById(R.id.tv_gradinged_item_better);

        woker.getPaint().setFakeBoldText(true);
        score.getPaint().setFakeBoldText(true);
        rank.getPaint().setFakeBoldText(true);


        helper.setText(R.id.tv_gradinged_item_name, item.name);
        helper.setText(R.id.tv_gradinged_item_worker, item.worker);
        helper.setText(R.id.tv_gradinged_item_score, item.score);
        helper.setText(R.id.tv_gradinged_item_better, item.rank);


    }
}
