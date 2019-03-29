package com.zhixing.employlib.viewmodel.activity;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.repertory.EventRepertory;
import com.zhixing.employlib.model.GradingEventEntity;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.repertory.performance.GradingListRepertory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/12 下午2:09
 *
 * 选择事件
 */
public class GradingEventViewModel  extends AndroidViewModel {
    private final EventRepertory eventRepertory;
    private  GradingListRepertory gradingListRepertory;


    //事件数据

    public MutableLiveData<List<PersonTestEntity>> eventDatas=new MutableLiveData<>();
    public LiveData< List<PersonTestEntity> > testEnt=new MutableLiveData<>();


    public GradingEventViewModel(@NonNull Application application) {
        super(application);
        eventRepertory = new EventRepertory(application);
         gradingListRepertory=new GradingListRepertory(application);
    }



    //积分事件
    public LiveData<List<PersonTestEntity>> getEventDatas(){

       /* List<GradingEventEntity> eventEntities =new ArrayList<>();
        eventEntities.add(new GradingEventEntity("玩手机","-2"));
        eventEntities.add(new GradingEventEntity("迟到","-1"));
        eventEntities.add(new GradingEventEntity("早退","-6"));
        eventEntities.add(new GradingEventEntity("打麻将","-5"));
        eventEntities.add(new GradingEventEntity("加班","+5"));*/

        testEnt =   Transformations.map(eventRepertory.getEventInfo(), DBaseResponse::getRows);

        return  testEnt;
    }







      //评分
        public MutableLiveData<DBaseResponse<EventKeyBean>> getGradingEventDatas(){

        List<GradingEventEntity> eventEntities =new ArrayList<>();

        return   gradingListRepertory.getEvent();

//        eventEntities.add(new GradingEventEntity("玩手机","-2"));
//        eventEntities.add(new GradingEventEntity("迟到","-1"));
//        eventEntities.add(new GradingEventEntity("早退","-6"));
//        eventEntities.add(new GradingEventEntity("打麻将","-5"));
//        eventEntities.add(new GradingEventEntity("加班","+5"));
//
//
//        eventDatas.setValue(eventEntities);







    }
}
