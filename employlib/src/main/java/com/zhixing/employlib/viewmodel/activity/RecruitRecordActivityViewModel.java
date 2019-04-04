package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.repertory.recruit.RecruitRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.List;

public class RecruitRecordActivityViewModel extends AndroidViewModel {
    private  RecruitRepertory recruitRepertory;
   public LiveData<List<RecruitListBean>> JobList;
    public RecruitRecordActivityViewModel(@NonNull Application application) {
        super(application);
         recruitRepertory =new RecruitRepertory(application);
    }



    public  void getJobList(){
        MutableLiveData<BaseResponse<RecruitListBean>> recruitList = recruitRepertory.getRecruitList();
        JobList=Transformations.map(recruitList,BaseResponse::getRows);

    }
}
