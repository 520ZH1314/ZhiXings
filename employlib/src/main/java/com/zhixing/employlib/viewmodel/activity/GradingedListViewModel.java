package com.zhixing.employlib.viewmodel.activity;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.GradingedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author zjq
 *create at 2019/3/13 上午11:30
 * 已评分列表
 */
public class GradingedListViewModel  extends AndroidViewModel {

    public MutableLiveData<List<GradingedEntity>> datas=new MutableLiveData<>();


    public GradingedListViewModel(@NonNull Application application) {
        super(application);
    }



    public MutableLiveData<List<GradingedEntity>> getDatas(){
        List<GradingedEntity> entities=new ArrayList<>();
        for (int i = 30; i < 40; i++) {
            entities.add(new GradingedEntity("","张三","包装工",i+"","优"));

        }
        datas.setValue(entities);
        return  datas;

    }
}
