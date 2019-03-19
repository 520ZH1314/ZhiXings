package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.RecruitDeiveredEntity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *@author zjq
 *create at 2019/3/18 下午3:21
 * 招聘记录的ViewModel
 */
public class RecruitRecordViewModel extends AndroidViewModel {

    //已投递的

    public MutableLiveData<List<RecruitDeiveredEntity>> datas=new MutableLiveData<>();

   //已推荐

    public MutableLiveData<List<RecruitDeiveredEntity>> recommendDatas=new MutableLiveData<>();

    public RecruitRecordViewModel(@NonNull Application application) {
        super(application);
    }




    //初始化投递数据


    public MutableLiveData<List<RecruitDeiveredEntity>> getDatas(){

        List<RecruitDeiveredEntity> entities=new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            entities.add(new RecruitDeiveredEntity("塑胶工","2019年03月06日","3-5年|职业技能四级|中专","8-15k","1"));

        }
        for (int i = 0; i < 3; i++) {

            entities.add(new RecruitDeiveredEntity("机械修理工","2019年03月07日","3-5年|职业技能四级|中专","5-7k","2"));

        }


        datas.setValue(entities);
        return datas;

    }


    //初始化推荐数据


    public MutableLiveData<List<RecruitDeiveredEntity>> getRecommendDatas()
    {

        List<RecruitDeiveredEntity> entities=new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            entities.add(new RecruitDeiveredEntity("塑胶工","2019年03月06日","3-5年|职业技能四级|中专","8-15k","1"));

        }
        for (int i = 0; i < 3; i++) {

            entities.add(new RecruitDeiveredEntity("机械修理工","2019年03月07日","3-5年|职业技能四级|中专","5-7k","3"));

        }


        recommendDatas.setValue(entities);
        return recommendDatas;

    }



}
