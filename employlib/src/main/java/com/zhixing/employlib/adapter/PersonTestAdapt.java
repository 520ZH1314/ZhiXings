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

            switch (item.Score){
                case "1":
                    imageView.setImageResource(R.mipmap.c6);
                    break;
                case "2":
                    imageView.setImageResource(R.mipmap.c5);
                    break;
                case "3":
                    imageView.setImageResource(R.mipmap.c4);
                    break;
                case "4":
                    imageView.setImageResource(R.mipmap.c3);
                    break;
                case "5":
                    imageView.setImageResource(R.mipmap.c2);
                    break;
                case "6":
                    imageView.setImageResource(R.mipmap.c1);
                    break;
            }




        helper.setText(R.id.tv_person_test_des,item.getItemName());
               helper.setText(R.id.tv_person_test_score,item.Score);

    }
}
