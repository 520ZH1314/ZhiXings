package com.zhixing.employlib.viewmodel.activity;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.GradingedEntity;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.GradingRecordListDateBean;
import com.zhixing.employlib.model.grading.RankBean;
import com.zhixing.employlib.model.grading.SelectUserData;
import com.zhixing.employlib.repertory.performance.GradingListRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/13 上午11:30
 * 已评分列表
 */
public class GradingedListViewModel  extends AndroidViewModel {

    private  GradingListRepertory gradingListRepertory;
    public MutableLiveData<List<GradingedEntity>> datas=new MutableLiveData<>();
    private final MutableLiveData<SelectUserData> Data=new MutableLiveData<>();

    public GradingedListViewModel(@NonNull Application application) {
        super(application);
        gradingListRepertory=new GradingListRepertory(application);
    }

    //日期筛选
    private final MutableLiveData<GradingRecordListDateBean> GradingDate=new MutableLiveData<>();





    public  final LiveData<BaseResponse<GradListBean>> ListData=Transformations.switchMap(GradingDate, entity->(
            gradingListRepertory.getGradingListBean(entity.startTime,entity.endTime)));




    public MutableLiveData<List<GradingedEntity>> getDatas(){
        List<GradingedEntity> entities=new ArrayList<>();
//        for (int i = 30; i < 40; i++) {
//            entities.add(new GradingedEntity("","张三","包装工",i+"","优"));
//
//        }
        datas.setValue(entities);
        return  datas;

    }

    //已评分列表
    public void setDate(String startEnd,String endTime){

        GradingDate.setValue(new GradingRecordListDateBean(startEnd,endTime));

    }
    //已评分列表详情
    public void  setDates(String Dates,String Endtime,String UsrCode){
        Data.setValue(new SelectUserData(Dates,UsrCode,Endtime));

    }
    //获取单人情况下 读取事件评分
    public final LiveData<BaseResponse<GradingListDetailBean>> RecordDetailData=Transformations.switchMap(Data, date->(gradingListRepertory.getGradingListDetail(date.date,date.endTime,date.useCode
    )));



    //获取等级标准


    public MutableLiveData<BaseResponse<RankBean>> getRankStandData(){
        return gradingListRepertory.getRankStand();

    }


}
