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


    //事件数据

    public MutableLiveData<List<PersonTestEntity>> eventDatas=new MutableLiveData<>();
    public LiveData< List<PersonTestEntity> > testEnt=new MutableLiveData<>();


    public GradingEventViewModel(@NonNull Application application) {
        super(application);
        eventRepertory = new EventRepertory(application);
    }




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
}
