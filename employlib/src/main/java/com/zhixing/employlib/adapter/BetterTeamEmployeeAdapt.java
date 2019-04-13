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
import com.zhixing.employlib.model.BetterTeamEmployeeEntity;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;

import java.util.List;

public class BetterTeamEmployeeAdapt extends BaseQuickAdapter<BetterTeamEmployeeEntity,BaseViewHolder> {
    private  String ip;

    public BetterTeamEmployeeAdapt(int layoutResId, @Nullable List<BetterTeamEmployeeEntity> data) {
        super(layoutResId, data);
        ip = "https://sale.stdlean.com:8004";
    }

    @Override
    protected void convert(BaseViewHolder helper, BetterTeamEmployeeEntity item) {

         if (item.ImageUrl!=null&&!TextUtils.isEmpty(item.ImageUrl)){
             ImageView viewById = helper.itemView.findViewById(R.id.iv_excellent_employee);
              MyImageLoader.loads(mContext,ip+item.ImageUrl,viewById,R.drawable.plot);
         }

        helper.setText(R.id.tv_item_better_team_title,item.Title);
        helper.setText(R.id.tv_item_better_team_name,item.teamName);
        helper.setText(R.id.tv_item_better_team_time,item.time);
        helper.setText(R.id.tv_item_better_team_des,item.desc);


    }
}
