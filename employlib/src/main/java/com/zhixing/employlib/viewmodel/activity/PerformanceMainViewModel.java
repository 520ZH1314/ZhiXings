package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.repertory.performance.PerformanceMainRepertory;

import java.util.List;

public class PerformanceMainViewModel extends AndroidViewModel {
    private PerformanceMainRepertory performanceRepertory;
    //个人班组信息以及权限

    public LiveData< List<PersonTeamBean> > TeamBeans;

    public PerformanceMainViewModel(@NonNull Application application) {
        super(application);
        performanceRepertory=new PerformanceMainRepertory(application);
    }


    //获取个人班组信息以及权限
        public  void getTeamBeans(){
        if (TeamBeans==null){
            TeamBeans=new MutableLiveData<>();
        }
            LiveData<DBaseResponse<PersonTeamBean>> personTeamInfo = performanceRepertory.getPersonTeamInfo();
            TeamBeans = Transformations.map(personTeamInfo, DBaseResponse::getRows);
    }
}
