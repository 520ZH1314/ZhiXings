package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.BetterTeamEmployeeEntity;
import com.zhixing.employlib.model.ExcellentEmployeeEntity;
import com.zhixing.employlib.model.NewEmployeeEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zjq
 * create at 2019/3/14 下午4:19
 * 园地的ViewModel
 */
public class TeamViewModel extends AndroidViewModel {


    //优秀员工
    public MutableLiveData<List<ExcellentEmployeeEntity>> excellentEmployeeData = new MutableLiveData<>();
    //新员工

    public MutableLiveData<List<NewEmployeeEntity>> newEmployeeData = new MutableLiveData<>();
    //班组天地

    public MutableLiveData<List<BetterTeamEmployeeEntity>> betterTeamData=new MutableLiveData<>();

    public TeamViewModel(@NonNull Application application) {
        super(application);
    }


    //优秀员工

    public MutableLiveData<List<ExcellentEmployeeEntity>> getExcellentEmployeeData(){

        List<ExcellentEmployeeEntity> data=new ArrayList<>();
        for (int i = 1; i <4; i++) {

            data.add(new ExcellentEmployeeEntity(i+"","","张三",
                    "包装工人","150分","第一名","他是一个好人啊!他是一个好人啊!他是一个好人啊!他是一个好人啊!"));

        }
        excellentEmployeeData.setValue(data);


        return  excellentEmployeeData;
    }




    //新员工
    public MutableLiveData<List<NewEmployeeEntity>> getNewEmployeeData(){

        List<NewEmployeeEntity> datas=new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new NewEmployeeEntity("广东深圳","","李四","包装工","塑胶组","3月6号","天行健,君子已自强不息.天行健,君子已自强不息."));
        }

        newEmployeeData.setValue(datas);
        return  newEmployeeData;

    }
    //班组天地
    public MutableLiveData<List<BetterTeamEmployeeEntity>> getBetterTeamData(){


        List<BetterTeamEmployeeEntity> datas=new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            datas.add(new BetterTeamEmployeeEntity("今晚开会","","注塑车间","2019-03-06","我们今天晚上一起去看科比我们今天晚上一起去看科比我们今天晚上一起去看科比我们今天晚上一起去看科比我们今天晚上一起去看科比"));

        }

        betterTeamData.setValue(datas);
        return  betterTeamData;


    }

}
