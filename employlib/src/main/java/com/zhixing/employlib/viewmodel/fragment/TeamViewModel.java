package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.BetterTeamEmployeeEntity;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.model.NewEmployeeEntity;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeeBean;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.repertory.team.TeamRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zjq
 * create at 2019/3/14 下午4:19
 * 园地的ViewModel
 */
public class TeamViewModel extends AndroidViewModel {


    private  TeamRepertory teamRepertory;
       public LiveData<List<TeamDemeanorBean>> listLiveData=new MutableLiveData<>();
    public LiveData<List<ExcellentEmployeeBean>> ExcellentEmployeeDatas =new MutableLiveData<>();
    public LiveData<List<NewEmployeeBean>> NewEmployeeDatas=new MutableLiveData<>();


    //新员工
    public final  MutableLiveData<Boolean>RefrshNewEmployeeData=new MutableLiveData<>();

    public   LiveData<BaseResponse<NewEmployeeBean>> newEmployee=Transformations.switchMap(RefrshNewEmployeeData,entity->(
            teamRepertory.getNewEmployee()
            ));


    //优秀员工
    public final  MutableLiveData<Boolean>RefrshExcellentEmployeeData=new MutableLiveData<>();

    public   LiveData<BaseResponse<ExcellentEmployeeBean>> excellentEmployee=Transformations.switchMap(RefrshExcellentEmployeeData,entity->(
            teamRepertory.getExcellentEmployee()
    ));



    //班组天地
    public final  MutableLiveData<Boolean>RefrshBetterTeamData=new MutableLiveData<>();

    public   LiveData<BaseResponse<TeamDemeanorBean>> teamDemeanor=Transformations.switchMap(RefrshBetterTeamData,entity->(
            teamRepertory.getTeamDemeanor()
    ));

    public TeamViewModel(@NonNull Application application) {
        super(application);
         teamRepertory =new TeamRepertory(application);
    }

    //刷新新员工
    public void getRefrshNewEmployeeData(boolean isTrue){
        RefrshNewEmployeeData.setValue(isTrue);

    }
    //刷新优秀员工
    public void getRefrshExcellentEmployeeData(boolean isTrue){
        RefrshExcellentEmployeeData.setValue(isTrue);

    }
    //刷新班组天地
    public void getRefrshBetterTeamData(boolean isTrue){
        RefrshBetterTeamData.setValue(isTrue);

    }

}
