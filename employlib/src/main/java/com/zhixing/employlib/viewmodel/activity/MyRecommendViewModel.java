package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.repertory.recruit.RecruitRepertory;
import com.zhixing.netlib.base.BaseResponse;

public class MyRecommendViewModel extends AndroidViewModel {
    private  RecruitRepertory recruitRepertory;
    public  MutableLiveData<BaseResponse> baseResponseMutableLiveData=new MutableLiveData<>();

    public MyRecommendViewModel(@NonNull Application application) {
        super(application);
         recruitRepertory =new RecruitRepertory(application);
    }


    public void PutJob(String ApplyName,
                       String ApplySex,
                       String Phone,
                       String IDCardNo,
                       String NativePlace,
                       String MaxDegree,
                       String Major,
                       String School,
                       String RefPhone,
                       String RefContent,
                       String RefferId,
                       String RefferName,String Reid,String ReName){
       baseResponseMutableLiveData = recruitRepertory.PutJob(ApplyName, ApplySex, Phone, IDCardNo, NativePlace, MaxDegree, Major, School, RefPhone, RefContent, RefferId, RefferName,Reid,ReName);
    }

}
