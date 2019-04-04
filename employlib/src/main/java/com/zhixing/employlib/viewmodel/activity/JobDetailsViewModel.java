package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.repertory.recruit.RecruitRepertory;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;

public class JobDetailsViewModel extends AndroidViewModel {
    private  RecruitRepertory recruitRepertory;

    public JobDetailsViewModel(@NonNull Application application) {
        super(application);
         recruitRepertory =new RecruitRepertory(application);
    }

    //投递简历


    public MutableLiveData<BaseResponse>SendJob(String ApplyUserName,
                                                String RecruitId,
                                                String RecruitPost,
                                                String HandleId,
                                                String HandleName,
                                                String ApplyType, String State,String StateName){

        return  recruitRepertory.SendJob(ApplyUserName,RecruitId,RecruitPost,HandleId,HandleName,ApplyType,State,StateName);
    }




}
