package com.zhixing.employlib.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;

import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/11 上午11:23
 * 个人绩效评分标准的adapt
 */
public class PersonTestAdapt extends BaseQuickAdapter<PersonTestEntity,BaseViewHolder> {
    public PersonTestAdapt(int layoutResId, @Nullable List<PersonTestEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonTestEntity item) {


        ImageView imageView = (ImageView)helper.itemView.findViewById(R.id.iv_persion_test);

        if ("6".equals(item)){
            imageView.setImageResource(R.mipmap.c1);
        }else if ("5".equals(item)){
            imageView.setImageResource(R.mipmap.c2);
        }else if ("4".equals(item)){
            imageView.setImageResource(R.mipmap.c3);
        }else if ("3".equals(item)){
            imageView.setImageResource(R.mipmap.c4);
        }else if ("2".equals(item)){
            imageView.setImageResource(R.mipmap.c5);
        }else if ("1".equals(item)){
            imageView.setImageResource(R.mipmap.c6);
        }


        helper.setText(R.id.tv_person_test_des,item.desc);
               helper.setText(R.id.tv_person_test_score,item.score);

    }
}
