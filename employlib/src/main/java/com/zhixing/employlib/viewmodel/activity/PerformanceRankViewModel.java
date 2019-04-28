package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.performance.PerformanceRankBean;
import com.zhixing.employlib.repertory.performance.RankRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class PerformanceRankViewModel  extends AndroidViewModel {

    private  RankRepertory rankRepertory;

    public MutableLiveData<BaseResponse<PerformanceRankBean>> rank=new MutableLiveData<>();
    public MutableLiveData<BaseResponse<PerformanceRankBean>> monthRank=new MutableLiveData<>();

    public PerformanceRankViewModel(@NonNull Application application) {
        super(application);
         rankRepertory=new RankRepertory(application);
    }


  /**
   *ew
   *@author zjq
   *create at 2019/4/24 下午2:13
   * 当日
   */
    public MutableLiveData<BaseResponse<PerformanceRankBean>>getRank(String StartDate, String EndDate, String sort)
    {
        rank = rankRepertory.getRank(StartDate, EndDate, sort);
        return  rank;
    }





    private final MutableLiveData<List<String>> dayDate=new MutableLiveData<>();

    public LiveData<BaseResponse<PerformanceRankBean>> responseDayLiveData=Transformations.switchMap(dayDate,data->(rankRepertory.getRank(data.get(0),data.get(1),data.get(2))));
    public void setRankDay(String StartDate, String EndDate, String sort){
        List<String> dayData=new ArrayList<>();
        dayData.add(StartDate);
        dayData.add(EndDate);
        dayData.add(sort);
        dayDate.setValue(dayData);
    }

    private final MutableLiveData<List<String>> MonthDate=new MutableLiveData<>();

    public LiveData<BaseResponse<PerformanceRankBean>> responseMonthLiveData=Transformations.switchMap(MonthDate,data->(rankRepertory.getRank(data.get(0),data.get(1),data.get(2))));



    public void setRankMonthDay(String StartDate, String EndDate, String sort){
        List<String> dayData=new ArrayList<>();
        dayData.add(StartDate);
        dayData.add(EndDate);
        dayData.add(sort);
        MonthDate.setValue(dayData);
    }

    /**
     *
     *@author zjq
     *create at 2019/4/24 下午2:13
     * 当月
     */
    public MutableLiveData<BaseResponse<PerformanceRankBean>>getMonthRank(String StartDate, String EndDate, String sort)
    {
        monthRank = rankRepertory.getRank(StartDate, EndDate, sort);
        return  monthRank;
    }


}
