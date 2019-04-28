package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.repertory.team.TeamRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpTeamViewModel extends AndroidViewModel {
    private  Application application;
    private  TeamRepertory teamRepertory;

    public UpTeamViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
         teamRepertory =new TeamRepertory(application);
    }



    //图片上传LinkedTableId 上传图片关联表的ID
    //LinkedTable
    //File

    public  MutableLiveData<BaseResponse<UpLoadBean>> UpLoadImage(String LinkedTabledId, String LinkedTable, String FilePath){
        String tenantId = SharedPreferencesTool.getMStool(application).getTenantId();
        Map<String, String> map=new HashMap<>();
        map.put("LinkedTableId",LinkedTabledId);
        map.put("LinkedTable",LinkedTable);
        map.put("TenantId",tenantId);
        map.put("FileDesc","");

        File file =new File(FilePath);
       return teamRepertory.UpLoadImage(map, file);

    }




    private RequestBody convertToRequestBody(String param){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }



  //上传优秀员工


    public MutableLiveData<BaseResponse> UpLoadOne(
            String ExcellentId,String type,
            String UserName,
            String UserCode,
            String EvaluationDate,
            String ExcellentDeeds,String rank){

        return  teamRepertory.UpLoadOne(ExcellentId,type,UserName,UserCode,EvaluationDate,
                ExcellentDeeds,rank);

    }

    //上传新员工
    public MutableLiveData<BaseResponse> UpLoadTwo( String ExcellentId,String UserName,
                                                    String UserCode,String NewDeeds){

        return  teamRepertory.UpLoadTwo(ExcellentId,UserName,UserCode,NewDeeds);
    }

    //上传班组园地
    public MutableLiveData<BaseResponse> UpLoadThree( String DemeanorId,String DemeanorTitle,
                                                      String DemeanorContent){


        return teamRepertory.UpLoadThree(DemeanorId,DemeanorTitle,DemeanorContent);

    }


    //上传公告


    //上传公告
    public MutableLiveData<BaseResponse> UpLoadFour( String NoticeId,String NoticeTitle,
                                                     String NoticeContent){

        return  teamRepertory.UpLoadFour(NoticeId,NoticeTitle,NoticeContent);

    }


}
