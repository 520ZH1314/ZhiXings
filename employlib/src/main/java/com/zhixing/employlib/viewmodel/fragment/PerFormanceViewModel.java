package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.repertory.performance.PerformanceMainRepertory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/11 上午11:16
 * 绩效界面的ViewModel
 */

public class PerFormanceViewModel extends AndroidViewModel {
    private PerformanceMainRepertory performanceRepertory;
    private final MutableLiveData<String> MonthDate=new MutableLiveData<>();

    //获取月绩效数据
    public final LiveData<DBaseResponse<MonthPerformanceBean>> MonthData=Transformations.switchMap(MonthDate, date->(performanceRepertory.getMonthInfo(date)));
    //获取月绩效数据的日期

    public  void setMonthTime(String date){

        MonthDate.setValue(date);
    }




    //个人绩效评分的数据

    public MutableLiveData<List<PersonTestEntity>> personTestEntitys=new MutableLiveData<>();



    //积分事件的数据
    public MutableLiveData<List<IntegralEventEntity>> IntegralEventEntitys=new MutableLiveData<>();
    public PerFormanceViewModel(@NonNull Application application) {
        super(application);

         performanceRepertory=new PerformanceMainRepertory(application);

    }



    //获取积分事件的数据
    public MutableLiveData<List<IntegralEventEntity>> getData(){

        List<IntegralEventEntity> lists=new ArrayList<>();
        for (int i = 0; i < 6; i++) {

            lists.add(new IntegralEventEntity(i+"",i+"","上班玩手机啊NMSL"+i));

        }
        IntegralEventEntitys.setValue(lists);

        return  IntegralEventEntitys;

    }


    //获取个人绩效评分的数据


    public MutableLiveData<List<PersonTestEntity>>getPersonTestEntitysData(){

        List<PersonTestEntity> lists=new ArrayList<>();
        for (int i = 6; i >0; i--) {
            lists.add(new PersonTestEntity(i+"","员工正常出勤即获得基础得分5分",""+i));

        }

        personTestEntitys.setValue(lists);
        return  personTestEntitys;

    }






     private final MutableLiveData<String> yesTerDayDate=new MutableLiveData<>();
    //获取昨日绩效数据
    public final LiveData<DBaseResponse<TotalMonthPerformanceBean>> YesDayData=Transformations.switchMap(yesTerDayDate,dates->(performanceRepertory.getYesterdayInfo(dates)));


    //获取昨日绩效数据的日期

    public  void setYesterdayTime(String dates){


        yesTerDayDate.setValue(dates);
    }







}
