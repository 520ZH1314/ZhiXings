package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.grading.GoGradingPostBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.SelectUserData;
import com.zhixing.employlib.repertory.performance.GradingListRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.List;

public class GradListDetailViewModel extends AndroidViewModel {
    private  GradingListRepertory gradingListRepertory;


    private final MutableLiveData<SelectUserData> Data=new MutableLiveData<>();


    public GradListDetailViewModel(@NonNull Application application) {
        super(application);
         gradingListRepertory=new GradingListRepertory(application);
    }





    public void  setDate(String Dates,String UsrCode){
        Data.setValue(new SelectUserData(Dates,UsrCode));

    }
    //获取单人情况下 读取事件评分
    public final LiveData<BaseResponse<GradingListDetailBean>> DetailData=Transformations.switchMap(Data, date->(gradingListRepertory.getGradingListDetail(date.date,date.date,date.useCode
    )));



    //评分

    public MutableLiveData<BaseResponse> GoGrading(List<GoGradingPostBean.UserInfoBean> UserInfo, List<GoGradingPostBean.EventInfoBean> EventInfo){

        return gradingListRepertory.getGoGrading(UserInfo,EventInfo);

    }



}
