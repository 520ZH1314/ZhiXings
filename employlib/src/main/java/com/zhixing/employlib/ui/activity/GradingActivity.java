package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.widget.ChangeTime;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.GradingListAdapt;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.viewmodel.activity.GradingVIewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author zjq
 * create at 2019/3/11 下午2:51
 * <p>
 * 评分员工列表
 */
public class GradingActivity extends BaseActvity {


    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_grading_list_year)
    TextView tvGradingListYear;
    @BindView(R2.id.tv_grading_list_moth)
    TextView tvGradingListMoth;
    @BindView(R2.id.tv_grading_list_day)
    TextView tvGradingListDay;
    @BindView(R2.id.recy_grading_list)
    RecyclerView recyGradingList;
    @BindView(R2.id.tv_go_grading)
    TextView tvGoGrading;
    @BindView(R2.id.rl_grading)
    RelativeLayout rlGrading;
    private Unbinder bind;
    private GradingVIewModel gradingVIewModel;

    private boolean isSelected = false;//是否编辑

    private GradingListAdapt gradingListAdapt;

    private List<GradingItemEntity> itemEntities = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_grading;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        bind = ButterKnife.bind(this);
        initView();

        initData();


    }

    private void initData() {
        gradingVIewModel.getGradingItemEntitysData().observe(this, new Observer<List<GradingItemEntity>>() {


            @Override
            public void onChanged(@Nullable List<GradingItemEntity> gradingItemEntities) {
                if (gradingItemEntities != null) {
                    gradingListAdapt = new GradingListAdapt(R.layout.item_grading_list, gradingItemEntities);
                    recyGradingList.setAdapter(gradingListAdapt);
                }

            }
        });


    }

    private void initView() {
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("我要评分");
        tvWorkSend.setText("编辑");
        recyGradingList.setLayoutManager(new LinearLayoutManager(this));
        gradingVIewModel = ViewModelProviders.of(this).get(GradingVIewModel.class);
    }


    @OnClick({R2.id.iv_work_add_work, R2.id.tv_work_send, R2.id.tv_grading_list_day, R2.id.rl_grading})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {

            AppManager.getAppManager().finishActivity();

        } else if (i == R.id.tv_work_send) {

            if (!isSelected) {
                //点击编辑界面
                rlGrading.setVisibility(View.VISIBLE);
                tvWorkSend.setText("取消");
                gradingListAdapt.setIsSelect(true);
                gradingListAdapt.notifyDataSetChanged();
                isSelected=true;
            } else {

                rlGrading.setVisibility(View.GONE);
                tvWorkSend.setText("编辑");
                gradingListAdapt.setIsSelect(false);
                gradingListAdapt.notifyDataSetChanged();
                isSelected=false;

            }


        }


     else if(i ==R.id.tv_grading_list_day)

    {
        ChangeTime changeTime = new ChangeTime(this, "", 2);
        changeTime.setSelect(new SelectTime() {
            @Override
            public void select(String time, long timestp) {
                String commonTime1 = TimeUtil.getCommonTime1(time);
                String[] splitDay = commonTime1.split("-");
                String Year = splitDay[0];
                String Month = splitDay[1];
                String Day = splitDay[2];
                tvGradingListYear.setText(Year + "年");
                tvGradingListMoth.setText(Month + "月");
                tvGradingListDay.setText(Day + "日");


            }
        });
        changeTime.showSheet();


    }else if(i==R.id.rl_grading){

            Intent intent =new Intent(GradingActivity.this,GradingDetailActivity.class);
            intent.putExtra("type","2");
            startActivity(intent);
    }
}
}






