package com.zhixing.employlib.api;

import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.NoticeBean;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeeBean;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.RankBean;
import com.zhixing.employlib.model.performance.PerformanceRankBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PersonDayEventBean;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.model.resume.GetResumeBean;
import com.zhixing.netlib.base.BaseResponse;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface PerformanceApi<T>  {

    //文件名
    public  static String  FLIESNAME="employlib_shared";

   //保存字段名
    public  static String   ISTEAMLEADER="IsTeamLeader";
    public  static String  TEAMID="TeamId";
    public  static String  TEAMNAME="TeamName";
    public  static String  TEAMLEADERUSERID="TeamLeaderUserId";
 public  static String  ISFITIST="IsFitist";



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


  //等级标准

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<RankBean>> getRankStand(@Body RequestBody body);


 //获取个人日绩效明细

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<DBaseResponse<PersonDayEventBean>> getPersonDayEvent(@Body RequestBody body);



 //获取园地优秀员工列表

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<ExcellentEmployeeBean>> getExcellentEmployee(@Body RequestBody body);

 //获取园地新员工列表

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<NewEmployeeBean>> getNewEmployee(@Body RequestBody body);

 //获取园地优秀班组列表

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<TeamDemeanorBean>> getTeamDemeanor(@Body RequestBody body);


 //获取招聘列表

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<RecruitListBean>> getRecruitList(@Body RequestBody body);


 //投递简历
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> SendJob(@Body RequestBody body);


 //推荐人
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> PutJob(@Body RequestBody body);



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
 //月视图
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<MonthViewBean>> getMonthViews(@Body RequestBody body);
 //颜色值
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<StandScore>> getScoreColor(@Body RequestBody body);

 //优秀员工图片上传

 @Multipart
 @POST("/api/EMS/Files/EditUploadFiles")
 Flowable<BaseResponse<UpLoadBean>>upLoadImage(@QueryMap Map<String,String> map, @Part MultipartBody.Part file);


 //上传优秀员工
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> UpLoadOne(@Body RequestBody body);


 //上传新员工

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> UpLoadTwo(@Body RequestBody body);


 //上传班组风采


 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> UpLoadthree(@Body RequestBody body);


 //上传公告

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> UpLoadFour(@Body RequestBody body);



 //获取个人简历信息

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<GetResumeBean>> GetResumeData(@Body RequestBody body);


 //增加个人简历信息

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> PutResumeData(@Body RequestBody body);



 //修改个人简历信息

 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse> AlterResumeData(@Body RequestBody body);

 //颜色值
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<NoticeBean>> getNotices(@Body RequestBody body);

 //获取绩效排名
 @Headers({"Content-Type: application/json","Accept: application/json"})
 @POST("/api/CMP/ApiRegistrator/PostApiGateWay")
 Flowable<BaseResponse<PerformanceRankBean>> getRank(@Body RequestBody body);
}
