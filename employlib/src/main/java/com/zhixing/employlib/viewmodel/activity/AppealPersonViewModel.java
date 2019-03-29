package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.repertory.AppealRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.List;

/**
 * 我要申诉事件
 */
public class AppealPersonViewModel extends AndroidViewModel {
    private AppealRepertory appealRepertory;

    public AppealPersonViewModel(@NonNull Application application) {
        super(application);
        appealRepertory = new AppealRepertory(application);
    }
    //获得个人关键事件
    public LiveData<List<AppealPersonEntity>> getAppealPersonData(String time){
//
        return  Transformations.map(appealRepertory.getAppealEntity(time), BaseResponse::getRows);
//        return  appealRepertory.getAppealEntity(time);
    }
}
