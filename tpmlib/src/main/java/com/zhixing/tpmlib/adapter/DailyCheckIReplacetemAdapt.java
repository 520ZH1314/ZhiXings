package com.zhixing.tpmlib.adapter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.base.zhixing.www.common.SharedUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.activity.MyTextActivity;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.RefrshBean;

import com.zhixing.tpmlib.viewModel.MyTextActivityViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
public class DailyCheckIReplacetemAdapt extends BaseQuickAdapter<DailyCheckItemBean,BaseViewHolder> {

    private  MyTextActivityViewModel mViewModel;
    private String status;
    private List<DailyCheckItemBean> data;
    public DailyCheckIReplacetemAdapt(int layoutResId, @Nullable List<DailyCheckItemBean> data, FragmentActivity context) {
        super(layoutResId, data);
        this.data=data;

        mViewModel=ViewModelProviders.of(context).get(MyTextActivityViewModel.class);
    }




    @Override
    protected void convert(BaseViewHolder helper, DailyCheckItemBean item) {

        helper.setText(R.id.tv_daily_check_replace_body,item.checkBody);
        Button btn1 =helper.itemView.findViewById(R.id.btn_ng);
        Button btn2 =helper.itemView.findViewById(R.id.btn_ok);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid("#FF943D")
                .radius(10)
                .into(btn1);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .solid("#15BC84")
                .radius(10)
                .into(btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(helper.getLayoutPosition()).setCheckStatus("2");
                mViewModel.RefrshBean(data);
                EventBus.getDefault().post(new RefrshBean(data));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                data.get(helper.getLayoutPosition()).setCheckStatus("1");
                mViewModel.RefrshBean(data);
                EventBus.getDefault().post(new RefrshBean(data));
            }
        });


    }


}
