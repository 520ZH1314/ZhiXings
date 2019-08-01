package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.AppealListEntity;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AppealListViewModel extends AndroidViewModel {


    //我的申诉记录列表

    public MutableLiveData<List<AppealListEntity>> appealListEntity = new MutableLiveData<>();

    public AppealListViewModel(@NonNull Application application) {
        super(application);
    }


    //获取申诉记录列表


    public MutableLiveData<List<AppealListEntity>> getAppealListEntity() {

        List<AppealListEntity> datas = new ArrayList<>();


        for (int i = 0; i < 2; i++) {

            datas.add(new AppealListEntity("迟到", "2019-03-20", "李四", "你在说你吗呢,你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你" +
                    "在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢", "0"));
        }
        for (int i = 0; i < 2; i++) {

            datas.add(new AppealListEntity("旷工", "2019-03-21", "科比", "你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢你在说你吗呢"
                    , "1"));
        }

        appealListEntity.setValue(datas);
        return appealListEntity;

    }

}
