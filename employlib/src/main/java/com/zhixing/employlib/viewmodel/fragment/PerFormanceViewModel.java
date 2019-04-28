package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.TestLineData;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PersonDayEventBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.repertory.EventRepertory;
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
    private  EventRepertory eventRepertory;
    private PerformanceMainRepertory performanceRepertory;
    private final MutableLiveData<String> MonthDate=new MutableLiveData<>();
    public LiveData< List<PersonTestEntity> > testEnt=new MutableLiveData<>();
    //获取月绩效数据
    public final LiveData<DBaseResponse<MonthPerformanceBean>> MonthData=Transformations.switchMap(MonthDate, date->(performanceRepertory.getMonthInfo(date)));
    //当前时间
    private final MutableLiveData<String> mCurrentTime=new MutableLiveData<>();
    //获取月绩效数据的日期

    public  void setMonthTime(String date){

        MonthDate.setValue(date);
    }






    //积分事件的数据
    public MutableLiveData<List<IntegralEventEntity>> IntegralEventEntitys=new MutableLiveData<>();
    public PerFormanceViewModel(@NonNull Application application) {
        super(application);

         performanceRepertory=new PerformanceMainRepertory(application);
        eventRepertory=new EventRepertory(application);
    }



    //获取积分事件的数据
//    public MutableLiveData<List<IntegralEventEntity>> getData(){
//
//        List<IntegralEventEntity> lists=new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//
//            lists.add(new IntegralEventEntity(i+"",i+"","上班玩手机啊NMSL"+i));
//
//        }
//        IntegralEventEntitys.setValue(lists);
//
//        return  IntegralEventEntitys;
//
//    }



    //获取积分事件数据

    public final LiveData<DBaseResponse<PersonDayEventBean>> personDayEventData=Transformations.switchMap(mCurrentTime, mTime->(performanceRepertory.getPersonDayEventData(mTime)));









     private final MutableLiveData<String> yesTerDayDate=new MutableLiveData<>();
    //获取昨日绩效数据
    public final LiveData<DBaseResponse<TotalMonthPerformanceBean>> YesDayData=Transformations.switchMap(yesTerDayDate, new Function<String, LiveData<DBaseResponse<TotalMonthPerformanceBean>>>() {
        @Override
        public LiveData<DBaseResponse<TotalMonthPerformanceBean>> apply(String input) {
            return performanceRepertory.getYesterdayInfo(input);
        }
    });



    //获取昨日绩效数据的日期

    public  void setYesterdayTime(String dates){


        yesTerDayDate.setValue(dates);
    }


    //获取当前时间


    public void getTime(String  mTime){

        mCurrentTime.setValue(mTime);
    }




    public LiveData<List<PersonTestEntity>> getEventDatas() {

       /* List<GradingEventEntity> eventEntities =new ArrayList<>();
        eventEntities.add(new GradingEventEntity("玩手机","-2"));
        eventEntities.add(new GradingEventEntity("迟到","-1"));
        eventEntities.add(new GradingEventEntity("早退","-6"));
        eventEntities.add(new GradingEventEntity("打麻将","-5"));
        eventEntities.add(new GradingEventEntity("加班","+5"));*/

        testEnt = Transformations.map(eventRepertory.getEventInfo(), DBaseResponse::getRows);

        return testEnt;

    }

}
