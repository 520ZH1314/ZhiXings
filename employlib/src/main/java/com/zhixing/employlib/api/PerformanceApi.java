package com.zhixing.employlib.api;

import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.netlib.base.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PerformanceApi<T>  {

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

    //获取考核标准
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
    Flowable<DBaseResponse<PersonTestEntity>> getEventInfo(@Body RequestBody body);

    //获取
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/api/CMP/ApiRegistrator/PostApiGateWay")

    Flowable<BaseResponse<AppealPersonEntity>> net(@Body RequestBody body);

 //获取昨日绩效接口

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<DBaseResponse<TotalMonthPerformanceBean>> getYesterDayInfo(@Body RequestBody body);
 //获取月绩效接口
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<DBaseResponse<MonthPerformanceBean>> getMonthDayInfo(@Body RequestBody body);



 //获取评分人员列表

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<GradListBean>> getGradingListData(@Body RequestBody body);


 //获取评分列表详情

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<GradingListDetailBean>> getGradingListDetailData(@Body RequestBody body);

//评分

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> getGoGrading(@Body RequestBody body);


 //获取所有事件

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<DBaseResponse<EventKeyBean>> getEvent(@Body RequestBody body);





 //获取

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> sendAppealPerson(@Body RequestBody body);

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<AppealList>> getAppealList(@Body RequestBody body);

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> sendAppealRes(@Body RequestBody body);
}
