package com.zhixing.employlib.api;

import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.netlib.base.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PerformanceApi {

    //文件名
    public  static String  FLIESNAME="Performance";

   //保存字段名
    public  static String   ISTEAMLEADER="IsTeamLeader";
    public  static String  TEAMID="TeamId";
    public  static String  TEAMNAME="TeamName";
    public  static String  TEAMLEADERUSERID="TeamLeaderUserId";



    //获取个人班组以及权限
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<DBaseResponse<PersonTeamBean>> getPersonTeamInfo(@Body RequestBody body);
}
