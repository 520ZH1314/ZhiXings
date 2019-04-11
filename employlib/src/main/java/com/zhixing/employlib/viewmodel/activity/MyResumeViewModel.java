package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.resume.CompanyEntity;
import com.zhixing.employlib.model.resume.GetResumeBean;
import com.zhixing.employlib.repertory.resume.ResumeRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class MyResumeViewModel extends AndroidViewModel {

    private  ResumeRepertory resumeRepertory;
    public MutableLiveData< List<CompanyEntity>> JobData=new MutableLiveData<>();

    public MutableLiveData< List<CompanyEntity>> ShcoolData=new MutableLiveData<>();
    public MyResumeViewModel(@NonNull Application application) {
        super(application);
         resumeRepertory =new ResumeRepertory(application);
    }


    public MutableLiveData<BaseResponse<GetResumeBean>> getResumeData(){

       return resumeRepertory.GetResumeData();

    }





//    public void getJobData(){
//        List<CompanyEntity> jobs=new ArrayList<>();
//        jobs.add(new CompanyEntity("数本科技","Android开发","2018-12"+"至"+"2019-03","1认真工作",""));
//        JobData.setValue(jobs);
//    }
//    public void getShcoolData(){
//        List<CompanyEntity> jobs=new ArrayList<>();
//        jobs.add(new CompanyEntity("南昌大学","计算机技术","2018-12"+"至"+"2019-03","1认真学习",""));
//        ShcoolData.setValue(jobs);
//    }

}
