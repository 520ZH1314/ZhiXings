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
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.repertory.EventRepertory;
import com.zhixing.employlib.repertory.PerformanceRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/11 上午11:16
 * 绩效界面的ViewModel
 */

public class PerFormanceViewModel extends AndroidViewModel {
    private  PerformanceRepertory performanceRepertory;
    private EventRepertory eventRepertory;



    //个人绩效评分的数据

    public MutableLiveData<List<PersonTestEntity>> personTestEntitys=new MutableLiveData<>();
    public LiveData< List<PersonTestEntity> > testEnt=new MutableLiveData<>();


    //积分事件的数据
    public MutableLiveData<List<IntegralEventEntity>> IntegralEventEntitys=new MutableLiveData<>();
    public PerFormanceViewModel(@NonNull Application application) {
        super(application);

         performanceRepertory=new PerformanceRepertory(application);
        eventRepertory = new EventRepertory(application);
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


    public LiveData< List<PersonTestEntity> > getPersonTestEntitysData(){

       /* List<PersonTestEntity> lists=new ArrayList<>();
        for (int i = 6; i >0; i--) {
            lists.add(new PersonTestEntity(i+"","员工正常出勤即获得基础得分5分",""+i));

        }*/
            testEnt =  Transformations.map(eventRepertory.getEventInfo(),DBaseResponse::getRows);
//        testEnt.getValue().getRows()
       // personTestEntitys = Transformations.map(testEnt, DBaseResponse::getRows);

//        personTestEntitys.setValue(lists);
        return  testEnt;

    }






}
