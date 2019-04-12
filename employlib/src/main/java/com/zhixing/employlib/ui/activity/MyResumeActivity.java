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
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhixing.employlib.R;
import com.zhixing.employlib.R2;
import com.zhixing.employlib.adapter.JobAdapt;
import com.zhixing.employlib.adapter.ShcoolAdapt;
import com.zhixing.employlib.model.resume.CompanyEntity;
import com.zhixing.employlib.model.resume.GetResumeBean;
import com.zhixing.employlib.viewmodel.activity.MyResumeViewModel;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
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
    private JobAdapt jobAdapt;
    private ShcoolAdapt jobAdapt2;
    private String jobList;
    private String schoolList;
    private ACache aCache;

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
        setStatus(-1);
         aCache =ACache.get(this);
        ivWorkAddWork.setImageResource(R.mipmap.back);
        tvWorkTitle.setText("我的履历");
        tvWorkSend.setVisibility(View.GONE);
        recyleMyResumeJob.setLayoutManager(new LinearLayoutManager(this));
        recyleMyResumeShcoolJob.setLayoutManager(new LinearLayoutManager(this));
         myResumeViewModel = ViewModelProviders.of(this).get(MyResumeViewModel.class);



        tvResumeName.setText(  SharedPreferencesTool.getMStool(this).getUserName());

         myResumeViewModel.getResumeData().observe(this, new Observer<BaseResponse<GetResumeBean>>() {
             @Override
             public void onChanged(@Nullable BaseResponse<GetResumeBean> getResumeBeanBaseResponse) {
                 if (getResumeBeanBaseResponse!=null){
                     List<GetResumeBean> rows = getResumeBeanBaseResponse.getRows();
                     List<CompanyEntity>JobData=new ArrayList<>();
                     List<CompanyEntity>ShcoolData=new ArrayList<>();


                     for (GetResumeBean bean: rows) {
                         if (bean.getJobList().size()!=0){
                             for (int i = 0; i < bean.getJobList().size(); i++) {
                                 JobData.add(new CompanyEntity(bean.getJobList().get(i).getCompanyName(),bean.getJobList().get(i).getJobpost(),getSimpleTime(bean.getJobList().get(i).getInDate()),getSimpleTime(bean.getJobList().get(i).getOffDate()),bean.getJobList().get(i).getJobJd(),bean.getJobList().get(i).getResumeId()));

                             }

                         }


                         if (bean.getEducationList().size()!=0){

                             for (int i = 0; i < bean.getEducationList().size(); i++) {
                                 ShcoolData.add(new CompanyEntity(bean.getEducationList().get(i).getSchool(),bean.getEducationList().get(i).getMajor(),getSimpleTime(bean.getEducationList().get(i).getInSchoolDate()),getSimpleTime(bean.getEducationList().get(i).getOffSchoolDate()),bean.getEducationList().get(i).getJobJd(),bean.getEducationList().get(i).getResumeId()));

                             }
                         }


                     }
                     if(JobData.size()!=0){
                          jobList = GsonUtil.getGson().toJson(JobData);
                         aCache.put("jobList",jobList);
                     }
                     if (ShcoolData.size()!=0){
                          schoolList = GsonUtil.getGson().toJson(ShcoolData);
                          aCache.put("schoolList",schoolList);
                     }



                     //后期可以优化为一个多布局的Recyleview
                       //工作经历
                       jobAdapt=new JobAdapt(R.layout.item_my_resume_job,JobData);
                       recyleMyResumeJob.setAdapter(jobAdapt);

                       jobAdapt.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                           @Override
                           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                               Intent intent =new Intent(MyResumeActivity.this,ResumeDetailActivity.class);
                               intent.putExtra("resumeInt",position);
                               intent.putExtra("resumeType","1");
                               startActivity(intent);
                           }
                       });



                       //教育经历
                       jobAdapt2=new ShcoolAdapt(R.layout.item_my_resume_job,ShcoolData);
                       recyleMyResumeShcoolJob.setAdapter(jobAdapt2);
                       jobAdapt2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                         @Override
                         public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                             Intent intent =new Intent(MyResumeActivity.this,ResumeDetailActivity.class);
                             intent.putExtra("resumeInt",position);
                             intent.putExtra("resumeType","2");
                             startActivity(intent);
                         }
                     });
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
            Intent intent =new Intent(this,ResumeDetailActivity.class);
            intent.putExtra("resumeType","3");
            startActivity(intent);

        } else if (i == R.id.tv_my_shcool_add) {

            Intent intent =new Intent(this,ResumeDetailActivity.class);
            intent.putExtra("resumeType","4");
            startActivity(intent);
        }
    }


    public String getSimpleTime(String Time){

        String[] ts = Time.split("T");
        return  ts[0];

    }
}
