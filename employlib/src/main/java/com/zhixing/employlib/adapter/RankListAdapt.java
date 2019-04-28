package com.zhixing.employlib.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.base.zhixing.www.util.MyImageLoader;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.GradingedEntity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author zjq
 * create at 2019/3/13 上午11:34
 * 已评分列表
 */
public class RankListAdapt extends BaseQuickAdapter<GradingedEntity, BaseViewHolder> {
    private  String ip;

    public RankListAdapt(int layoutResId, @Nullable List<GradingedEntity> data) {
        super(layoutResId, data);
         ip=SharedPreferencesTool.getMStool(mContext).getIp();

    }

    @Override
    protected void convert(BaseViewHolder helper, GradingedEntity item) {

        //加粗处理

        TextView score = helper.itemView.findViewById(R.id.tv_gradinged_item_score);
        TextView rank = helper.itemView.findViewById(R.id.tv_gradinged_item_better);


        score.getPaint().setFakeBoldText(true);
        rank.getPaint().setFakeBoldText(true);

      CircleImageView circleImageView =helper.itemView.findViewById(R.id.profile_image);
      if (item.imagUrl!=null&&!TextUtils.isEmpty(item.imagUrl)){
          MyImageLoader.loads(mContext,item.imagUrl,circleImageView,R.mipmap.standard_head);
      }
        helper.setText(R.id.tv_gradinged_item_name, item.name);
        helper.setText(R.id.tv_gradinged_item_score, item.score+"");
        helper.setText(R.id.tv_gradinged_item_better, item.rank);


    }
}
