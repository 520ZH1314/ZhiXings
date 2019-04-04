package com.zhixing.employlib.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.JobAdapt;
import com.zhixing.employlib.model.resume.CompanyEntity;
import com.zhixing.employlib.viewmodel.activity.MyResumeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyResumeActivity extends BaseActvity {

    @BindView(R2.id.iv_work_add_work)
    ImageView ivWorkAddWork;
    @BindView(R2.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R2.id.tv_work_send)
    TextView tvWorkSend;
    @BindView(R2.id.tv_resume_name)
    TextView tvResumeName;
    @BindView(R2.id.tv_resume_job)
    TextView tvResumeJob;
    @BindView(R2.id.tv_resume_address)
    TextView tvResumeAddress;
    @BindView(R2.id.recyle_my_resume_job)
    RecyclerView recyleMyResumeJob;
    @BindView(R2.id.tv_my_job_add)
    TextView tvMyJobAdd;
    @BindView(R2.id.recyle_my_resume_shcool_job)
    RecyclerView recyleMyResumeShcoolJob;
    @BindView(R2.id.tv_my_shcool_add)
    TextView tvMyShcoolAdd;
    private MyResumeViewModel myResumeViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_resume;
    }


    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        ButterKnife.bind(this);

        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("我的履历");
        tvWorkSend.setVisibility(View.GONE);
        recyleMyResumeJob.setLayoutManager(new LinearLayoutManager(this));
        recyleMyResumeShcoolJob.setLayoutManager(new LinearLayoutManager(this));
         myResumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);
        myResumeViewModel.getJobData();
        myResumeViewModel.getShcoolData();
        myResumeViewModel.JobData.observe(this, new Observer<List<CompanyEntity>>() {
            @Override
            public void onChanged(@Nullable List<CompanyEntity> companyEntities) {
                if (companyEntities!=null){

                    JobAdapt  jobAdapt=new JobAdapt(R.layout.item_my_resume_job,companyEntities);
                    recyleMyResumeJob.setAdapter(jobAdapt);
                }


            }
        });

        myResumeViewModel.ShcoolData.observe(this, new Observer<List<CompanyEntity>>() {
            @Override
            public void onChanged(@Nullable List<CompanyEntity> companyEntities) {
                if (companyEntities!=null){
                    JobAdapt  jobAdapt=new JobAdapt(R.layout.item_my_resume_job,companyEntities);
                    recyleMyResumeShcoolJob.setAdapter(jobAdapt);
                }

            }
        });


    }



    @OnClick({R2.id.iv_work_add_work, R2.id.tv_my_job_add, R2.id.tv_my_shcool_add})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_work_add_work) {
            AppManager.getAppManager().finishActivity();
        } else if (i == R.id.tv_my_job_add) {
        } else if (i == R.id.tv_my_shcool_add) {
        }
    }
}
