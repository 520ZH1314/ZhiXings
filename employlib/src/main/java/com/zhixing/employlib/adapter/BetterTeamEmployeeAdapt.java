package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.BetterTeamEmployeeEntity;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;

import java.util.List;

public class BetterTeamEmployeeAdapt extends BaseQuickAdapter<BetterTeamEmployeeEntity,BaseViewHolder> {
    public BetterTeamEmployeeAdapt(int layoutResId, @Nullable List<BetterTeamEmployeeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BetterTeamEmployeeEntity item) {

        helper.setText(R.id.tv_item_better_team_title,item.Title);
        helper.setText(R.id.tv_item_better_team_name,item.teamName);
        helper.setText(R.id.tv_item_better_team_time,item.time);
        helper.setText(R.id.tv_item_better_team_des,item.desc);


    }
}
