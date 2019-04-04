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
    //优秀员工
    public MutableLiveData<List<ExcellentEmployeeEntity>> excellentEmployeeData = new MutableLiveData<>();
    //新员工

    public MutableLiveData<List<NewEmployeeEntity>> newEmployeeData = new MutableLiveData<>();
    //班组天地

    public MutableLiveData<List<BetterTeamEmployeeEntity>> betterTeamData=new MutableLiveData<>();


    public TeamViewModel(@NonNull Application application) {
        super(application);
         teamRepertory =new TeamRepertory(application);
    }


    //优秀员工

    public LiveData<List<ExcellentEmployeeBean>> getExcellentEmployeeData(){

        MutableLiveData<BaseResponse<ExcellentEmployeeBean>> excellentEmployee = teamRepertory.getExcellentEmployee();

        LiveData<List<ExcellentEmployeeBean>> ExcellentEmployeeDatas = Transformations.map(excellentEmployee, BaseResponse::getRows);


        return  ExcellentEmployeeDatas;
    }




    //新员工
    public   LiveData<List<NewEmployeeBean>> getNewEmployeeData(){


        MutableLiveData<BaseResponse<NewEmployeeBean>> newEmployee = teamRepertory.getNewEmployee();
                       LiveData<List<NewEmployeeBean>> NewEmployeeDatas=Transformations.map(newEmployee,BaseResponse::getRows);
        return NewEmployeeDatas;




    }
    //班组天地
    public  LiveData<List<TeamDemeanorBean>> getBetterTeamData(){

        MutableLiveData<BaseResponse<TeamDemeanorBean>> teamDemeanor = teamRepertory.getTeamDemeanor();

        LiveData<List<TeamDemeanorBean>> listLiveData =Transformations.map(teamDemeanor,BaseResponse::getRows);
        return  listLiveData;




    }

}
