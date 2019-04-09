package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.NoticeBean;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.repertory.AppealRepertory;
import com.zhixing.employlib.repertory.MonthRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * 月视图
 */
public class MonthViewModel extends AndroidViewModel {
    private MonthRepertory appealRepertory;

    public MonthViewModel(@NonNull Application application) {
        super(application);
        appealRepertory = new MonthRepertory(application);
    }
    //
    public LiveData<List<MonthViewBean>> getMonthViews(Map time){
//
        return  Transformations.map(appealRepertory.getMonthViews(time), BaseResponse::getRows);
//        return  appealRepertory.getAppealEntity(time);
    }
    public LiveData<List<StandScore>> getScoreColor(Map time){
//
        return  Transformations.map(appealRepertory.getScoreColor(time), BaseResponse::getRows);
//        return  appealRepertory.getAppealEntity(time);
    }
    //获取公告
    public LiveData<List<NoticeBean>> getNotices(Map time){
//
        return  Transformations.map(appealRepertory.getNotices(time), BaseResponse::getRows);
//        return  appealRepertory.getAppealEntity(time);
    }


}
