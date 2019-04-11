package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.repertory.resume.ResumeRepertory;
import com.zhixing.netlib.base.BaseResponse;

public class ResumeDetailViewModel extends AndroidViewModel {
    private  ResumeRepertory resumeRepertory;

    public ResumeDetailViewModel(@NonNull Application application) {
        super(application);
         resumeRepertory =new ResumeRepertory(application);
    }

    //修改工作.修改学历

    public MutableLiveData<BaseResponse> AlterJob(String ResumeId, String CompanyName, String Jobpost, String InDate,
                                                  String enddate, String jobDesc,
                                                  String School, String Major,
                                                  String InSchoolDate, String InSchoolEndDate){
      return  resumeRepertory.AlterResumeData(ResumeId,CompanyName,Jobpost,InDate,enddate,jobDesc,School,Major,InSchoolDate,InSchoolEndDate);


    }



    //增加工作 //增加学历
    public MutableLiveData<BaseResponse> addJob(String CompanyName, String Jobpost, String InDate,
                                                String enddate, String jobDesc,
                                                String School, String Major,
                                                String InSchoolDate, String InSchoolEndDate){
       return  resumeRepertory.AddResumeData(CompanyName,Jobpost,InDate,enddate,jobDesc,School,Major,InSchoolDate,InSchoolEndDate);

    }





}
