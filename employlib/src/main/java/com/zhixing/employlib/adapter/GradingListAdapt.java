package com.zhixing.employlib.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.ui.activity.GradingDetailActivity;
import com.zhixing.employlib.viewmodel.activity.GradingVIewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *@author zjq
 *create at 2019/3/11 上午11:23
 * 个人绩效评分标准的adapt
 */
public class GradingListAdapt extends BaseQuickAdapter<GradingItemEntity,BaseViewHolder> {

    public boolean isSelected = false;
    public List<GradingItemEntity> data;

    public GradingListAdapt(int layoutResId, @Nullable List<GradingItemEntity> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GradingItemEntity item) {
        final CheckBox checkBox = (CheckBox) helper.itemView.findViewById(R.id.checkBox_item_grading);
        CardView cardView = (CardView) helper.itemView.findViewById(R.id.card_item_grading);
        if (isSelected) {
            checkBox.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(helper.getLayoutPosition()).isChecked) {
                        checkBox.setChecked(false);
                        data.get(helper.getLayoutPosition()).setChecked(false);

                    } else {
                        checkBox.setChecked(true);
                        data.get(helper.getLayoutPosition()).setChecked(true);
                    }
                }
            });

        } else {
            checkBox.setVisibility(View.GONE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GradingDetailActivity.class);
                    intent.putExtra("type", "1");
                    intent.putExtra("position", data.get(helper.getLayoutPosition()).useCode);
                    mContext.startActivity(intent);
                }
            });


        }


        ImageView imageView = (ImageView) helper.itemView.findViewById(R.id.circleImageView);

        helper.setText(R.id.tv_grading_item_list_name, item.name);
        helper.setText(R.id.tv_grading_item_list_sex, item.sex);
        helper.setText(R.id.tv_grading_item_list_worker, item.position);
        helper.setText(R.id.tv_grading_item_list_desc, "已录入关键事件" + item.keyEventsNums + "条");
        Button button = (Button) helper.itemView.findViewById(R.id.btn_item_grading);


        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid(R.color.item_grading_btn)
                .radius(15)
                .into(button);

    }


    public void setIsSelect(boolean isSelect) {

        if (!isSelect) {

        }

        this.isSelected = isSelect;
    }
    //提供给activity获取选中的activity

    @TargetApi(Build.VERSION_CODES.N)
    public List<GradingItemEntity> getSelectData() {
        return data.stream().filter(s -> s.isChecked).collect(Collectors.toList());


    }
}
